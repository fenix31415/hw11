package gym;

import org.junit.jupiter.api.BeforeEach;
import gym.events.IEventDao;
import gym.events.EventDao;
import gym.events.VisitEvent;
import gym.model.DataReader;
import gym.model.DataWriter;
import gym.services.ManagerService;

import java.time.Duration;
import java.time.Instant;

public class BaseTest {
    protected IEventDao eventDao = null;
    protected DataReader dataReader = null;
    protected DataWriter dataWriter = null;
    protected ManagerService managerService = null;

    @BeforeEach
    public void setupServices() {
        this.eventDao = new EventDao();
        this.dataReader = new DataReader(eventDao);
        this.dataWriter = new DataWriter(eventDao);
        this.managerService = new ManagerService(dataReader, dataWriter);
    }

    protected void addMembership(final long id, final long validity) {
        managerService.newMembership(id, Duration.ofDays(validity));
    }

    protected void addExpiredMembership(final long id) {
        managerService.newMembership(id, Duration.ofDays(-1));
    }

    protected void extend(final long id, final long validity) {
        managerService.extendMembership(id, Duration.ofDays(validity));
    }

    protected void enter(final long id, final long timestamp) {
        eventDao.addEvent(new VisitEvent(true, id, Instant.ofEpochMilli(timestamp)));
    }

    protected void exit(final long id, final long timestamp) {
        eventDao.addEvent(new VisitEvent(false, id, Instant.ofEpochMilli(timestamp)));
    }
}
