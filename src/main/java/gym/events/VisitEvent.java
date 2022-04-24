package gym.events;

import java.time.Instant;

public class VisitEvent {
    private final boolean isEnter;
    private final long id;
    private final Instant timestamp;

    public VisitEvent(final boolean enter, long id, Instant timestamp) {
        this.isEnter = enter;
        this.id = id;
        this.timestamp = timestamp;
    }

    public boolean getIsEnter() {
        return isEnter;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public long getId() {
        return id;
    }
}
