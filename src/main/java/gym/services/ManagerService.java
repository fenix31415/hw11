package gym.services;

import gym.model.DataReader;
import gym.model.DataWriter;
import gym.model.Membership;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

public class ManagerService {
    private final DataReader dataReader;
    private final DataWriter dataWriter;

    public ManagerService(final DataReader dataReader, final DataWriter dataWriter) {
        this.dataReader = dataReader;
        this.dataWriter = dataWriter;
    }

    public void newMembership(final long id, final Duration validity) {
        final Optional<Membership> membershipOptional = dataReader.getMembership(id);
        if (membershipOptional.isPresent() &&
                membershipOptional.get().getValidUntil().isAfter(Instant.now())) {
            final Membership membership = membershipOptional.get();
            throw new IllegalArgumentException(
                    "Membership for id " + id + " is already registered and valid until "
                            + membership.getValidUntil()
            );
        } else {
            dataWriter.newMember(id, validity);
        }
    }

    public void extendMembership(final long id, final Duration validity) {
        if (dataReader.getMembership(id).isEmpty()) {
            throw new IllegalArgumentException("Member with id " + id + " is not registered.");
        }
        dataWriter.extend(id, validity);
    }
}
