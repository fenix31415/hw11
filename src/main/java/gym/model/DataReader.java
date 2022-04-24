package gym.model;

import gym.events.IEventDao;
import gym.events.NewMemberEvent;
import gym.events.ExtendEvent;
import gym.events.VisitEvent;

import java.time.Instant;
import java.util.*;

public class DataReader {
    private final IEventDao eventDao;

    public DataReader(final IEventDao eventDao) {
        this.eventDao = eventDao;
    }

    private List<Membership> getMembershipsByData(final List<NewMemberEvent> newMemberEvents, final List<ExtendEvent> extendEvents) {
        final Map<Long, Membership> result = new HashMap<>();

        for (final NewMemberEvent newMemberEvent : newMemberEvents) {
            Membership membership = new Membership(newMemberEvent.getId(), newMemberEvent.getTimestamp().plus(newMemberEvent.getDuration()));
            result.put(membership.getId(), membership);
        }

        for (final ExtendEvent extendEvent : extendEvents) {
            result.get(extendEvent.getId()).extend(extendEvent.getDuration());
        }

        return new ArrayList<>(result.values());
    }

    public List<Membership> getMemberships() {
        return getMembershipsByData(eventDao.getNewMemberEvents(), eventDao.getExtendEvents());
    }

    public Optional<Membership> getMembership(final long id) {
        return getMembershipsByData(eventDao.getNewMemberEvents(id), eventDao.getExtendEvents(id)).stream().findAny();
    }

    public List<VisitEvent> getAllVisitsSince(final Instant timestamp) {
        return eventDao.getVisitEventsSince(timestamp);
    }
}
