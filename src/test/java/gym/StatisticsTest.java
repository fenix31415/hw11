package gym;

import gym.services.StatisticsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Map;
import java.util.TimeZone;

public class StatisticsTest extends BaseTest {
    private StatisticsService statisticsService = null;

    private void setupStatisticsService() {
        this.statisticsService = new StatisticsService(dataReader);
    }

    private LocalDate visit(final long id, final long day, final long duration) {
        final long timestamp = day * 86400100;
        enter(id, timestamp);
        exit(id, timestamp + duration);
        return LocalDate.ofInstant(Instant.ofEpochMilli(timestamp), TimeZone.getDefault().toZoneId());
    }

    private LocalDate visit(final long id, final long day) {
        return visit(id, day, 0);
    }

    @Test
    public void testEmpty() {
        setupStatisticsService();
        Assertions.assertEquals(Map.of(), statisticsService.dailyStatistics());
        Assertions.assertEquals(0, statisticsService.getAverageCount());
        Assertions.assertEquals(0, statisticsService.getAverageDuration());
    }

    @Test
    public void testDailyStats() {
        setupStatisticsService();

        final LocalDate day1 = visit(1, 0);
        final LocalDate day2 = visit(2, 1);
        final LocalDate day3 = visit(3, 1);
        Assertions.assertEquals(Map.of(day1, 1, day2, 2), statisticsService.dailyStatistics());
    }

    @Test
    public void testDailyStats2() {
        final LocalDate day1 = visit(1, 0);
        final LocalDate day2 = visit(2, 1);
        setupStatisticsService();
        final LocalDate day3 = visit(3, 2);
        Assertions.assertEquals(Map.of(day1, 1, day2, 1, day3, 1), statisticsService.dailyStatistics());
    }

    @Test
    public void testAverageCount() {
        visit(1, 0);
        visit(1, 1);
        visit(2, 2);
        setupStatisticsService();
        Assertions.assertEquals(1.5, statisticsService.getAverageCount());

        visit(1, 0);
        setupStatisticsService();
        visit(1, 1);
        visit(2, 2);
        Assertions.assertEquals(2.0, statisticsService.getAverageCount());
    }

    @Test
    public void testAverageDuration() {
        visit(1, 0, 100);
        visit(1, 0, 200);
        setupStatisticsService();
        Assertions.assertEquals(150.0, statisticsService.getAverageDuration());

        visit(1, 0, 100);
        setupStatisticsService();
        visit(1, 0, 200);
        visit(2, 1, 300);
        Assertions.assertEquals(175.0, statisticsService.getAverageDuration());
    }
}
