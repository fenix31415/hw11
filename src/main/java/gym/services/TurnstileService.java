package gym.services;

import gym.model.DataReader;
import gym.model.DataWriter;
import gym.model.Membership;

import java.time.Instant;
import java.util.Optional;

public class TurnstileService {
    private final DataReader dataReader;
    private final DataWriter dataWriter;

    public TurnstileService(final DataReader dataReader, final DataWriter dataWriter) {
        this.dataReader = dataReader;
        this.dataWriter = dataWriter;
    }

    public void pass(final long id, final boolean enter) {
        final Optional<Membership> membership = dataReader.getMembership(id);

        if (membership.isEmpty()) {
            throw new IllegalArgumentException("Member '" + id + "' has no membership");
        }

        if (enter && membership.get().getValidUntil().isBefore(Instant.now())) {
            throw new IllegalArgumentException("Membership '" + membership.get().getId() + "' expired");
        }

        dataWriter.visit(id, enter);
    }
}
