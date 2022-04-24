package gym.model;

import gym.events.IEventDao;
import gym.events.NewMemberEvent;
import gym.events.ExtendEvent;
import gym.events.VisitEvent;

import java.time.Duration;
import java.time.Instant;

public class DataWriter {
    private final IEventDao eventDao;

    public DataWriter(final IEventDao eventDao) {
        this.eventDao = eventDao;
    }

    public void newMember(final long id, final Duration validity) {
        eventDao.addEvent(new NewMemberEvent(id, Instant.now(), validity));
    }

    public void visit(final long id, final boolean enter) {
        eventDao.addEvent(new VisitEvent(enter, id, Instant.now()));
    }

    public void extend(final long id, final Duration validity) {
        eventDao.addEvent(new ExtendEvent(id, validity));
    }
}
