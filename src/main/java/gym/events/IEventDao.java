package gym.events;

import java.time.Instant;
import java.util.List;

public interface IEventDao {
    void addEvent(final NewMemberEvent event);

    void addEvent(final ExtendEvent event);

    void addEvent(final VisitEvent event);

    List<NewMemberEvent> getNewMemberEvents();

    List<ExtendEvent> getExtendEvents();

    List<NewMemberEvent> getNewMemberEvents(final long id);

    List<ExtendEvent> getExtendEvents(final long id);

    List<VisitEvent> getVisitEventsSince(final Instant timestamp);
}
