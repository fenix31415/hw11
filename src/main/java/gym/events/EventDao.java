package gym.events;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EventDao implements IEventDao {
    private final Set<NewMemberEvent> newMemberEventSet = new HashSet<>();
    private final Set<ExtendEvent> extendEventSet = new HashSet<>();
    private final Set<VisitEvent> visitEventSet = new HashSet<>();

    private Stream<ExtendEvent> getProlongationEventImpl() {
        return extendEventSet.stream();
    }

    private Stream<VisitEvent> getVisitEventsImpl() {
        return visitEventSet.stream()
                .sorted(Comparator.comparing(VisitEvent::getTimestamp));
    }

    private Stream<NewMemberEvent> getNewMemberEventsImpl() {
        return newMemberEventSet.stream().sorted(Comparator.comparing(NewMemberEvent::getTimestamp));
    }

    @Override
    public void addEvent(final NewMemberEvent event) {
        newMemberEventSet.add(event);
    }

    @Override
    public void addEvent(final ExtendEvent event) {
        extendEventSet.add(event);
    }

    @Override
    public void addEvent(final VisitEvent event) {
        visitEventSet.add(event);
    }

    @Override
    public List<NewMemberEvent> getNewMemberEvents() {
        return getNewMemberEventsImpl().collect(Collectors.toList());
    }

    @Override
    public List<NewMemberEvent> getNewMemberEvents(long id) {
        return getNewMemberEventsImpl().filter(event -> event.getId() == id).collect(Collectors.toList());
    }

    @Override
    public List<ExtendEvent> getExtendEvents() {
        return new ArrayList<>(extendEventSet);
    }

    @Override
    public List<ExtendEvent> getExtendEvents(long id) {
        return getProlongationEventImpl()
                .filter(event -> event.getId() == id)
                .collect(Collectors.toList());
    }

    @Override
    public List<VisitEvent> getVisitEventsSince(Instant timestamp) {
        return getVisitEventsImpl().filter(event -> event.getTimestamp().isAfter(timestamp)).collect(Collectors.toList());
    }
}
