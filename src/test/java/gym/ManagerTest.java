package gym;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import gym.model.Membership;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

public class ManagerTest extends BaseTest {
    private Instant getValidUntil(final long id) {
        final Optional<Membership> membershipOptional = dataReader.getMembership(id);
        Assertions.assertTrue(membershipOptional.isPresent());
        final Membership membership = membershipOptional.get();
        return membership.getValidUntil();
    }

    @Test
    public void testValidIssue() {
        Assertions.assertDoesNotThrow(() -> addMembership(1, 1));
        Assertions.assertThrows(IllegalArgumentException.class, () -> addMembership(1, 1));

        final Optional<Membership> mbMembership = dataReader.getMembership(1);
        Assertions.assertTrue(mbMembership.isPresent());
        final Membership membership = mbMembership.get();
        Assertions.assertEquals(1, membership.getId());
    }

    @Test
    public void testExtend() {
        addMembership(1L, 1);
        final Instant validBefore = getValidUntil(1);
        Assertions.assertDoesNotThrow(() -> extend(1, 22));
        Assertions.assertEquals(validBefore.plus(Duration.ofDays(22)), getValidUntil(1));

        Assertions.assertThrows(IllegalArgumentException.class, () -> extend(2, 1));
    }
}
