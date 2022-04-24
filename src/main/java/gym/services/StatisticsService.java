package gym.services;

import gym.events.VisitEvent;
import gym.model.DataReader;

import java.time.Instant;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class StatisticsService {
    private final List<VisitEvent> visitEventList = new ArrayList<>();
    private final DataReader dataReader;
    private Instant maxTimestamp = Instant.MIN;

    public StatisticsService(final DataReader dataReader) {
        this.dataReader = dataReader;
        updateVisitEventList();
    }

    private void updateVisitEventList() {
        visitEventList.addAll(dataReader.getAllVisitsSince(maxTimestamp));
        if (!visitEventList.isEmpty()) {
            maxTimestamp = visitEventList.get(visitEventList.size() - 1).getTimestamp();
        }
    }

    private Map<LocalDate, List<VisitEvent>> getDailyEnterEvents() {
        return visitEventList.stream().filter(VisitEvent::getIsEnter).collect(Collectors.groupingBy(event ->
                LocalDate.ofInstant(event.getTimestamp(), TimeZone.getDefault().toZoneId())));
    }

    private Map<Long, List<VisitEvent>> getMemberEnterEvents() {
        return visitEventList.stream()
                .filter(VisitEvent::getIsEnter)
                .collect(Collectors.groupingBy(VisitEvent::getId));
    }

    public Map<LocalDate, Integer> dailyStatistics() {
        updateVisitEventList();

        return getDailyEnterEvents().entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().size()));
    }

    public double getAverageCount() {
        updateVisitEventList();
        return getMemberEnterEvents().values().stream()
                .map(List::size).mapToDouble(Double::valueOf).average().orElse(0.0);
    }

    public double getAverageDuration() {
        updateVisitEventList();
        final Map<Long, Instant> memberToEnter = new HashMap<>();
        final List<Double> durations = new ArrayList<>();

        for (final VisitEvent event : visitEventList) {
            long id = event.getId();
            Instant timestamp = event.getTimestamp();

            if (event.getIsEnter()) {
                memberToEnter.put(id, timestamp);
            } else {
                assert(memberToEnter.containsKey(id));
                durations.add((double) timestamp.toEpochMilli() - memberToEnter.get(id).toEpochMilli());
            }
        }

        return durations.stream().mapToDouble(Double::valueOf).average().orElse(0.0);
    }
}
