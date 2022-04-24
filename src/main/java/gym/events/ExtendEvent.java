package gym.events;

import java.time.Duration;

public class ExtendEvent {
    private final long id;
    private final Duration duration;

    public ExtendEvent(long membershipId, Duration duration) {
        this.id = membershipId;
        this.duration = duration;
    }

    public long getId() {
        return id;
    }

    public Duration getDuration() {
        return duration;
    }
}
