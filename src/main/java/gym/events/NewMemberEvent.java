package gym.events;

import java.time.Duration;
import java.time.Instant;

public class NewMemberEvent {
    private final long id;
    private final Instant timestamp;
    private final Duration duration;

    public NewMemberEvent(long membershipId, Instant timestamp, Duration duration) {
        this.id = membershipId;
        this.timestamp = timestamp;
        this.duration = duration;
    }

    public long getId() {
        return id;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public Duration getDuration() {
        return duration;
    }
}
