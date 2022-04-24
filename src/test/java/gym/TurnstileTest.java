package gym;

import gym.services.TurnstileService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TurnstileTest extends BaseTest {
    private TurnstileService turnstileService = null;

    @BeforeEach
    public void setupTurnstileService() {
        this.turnstileService = new TurnstileService(dataReader, dataWriter);
    }

    private void tryPass() {
        this.turnstileService.pass(1L, true);
    }

    @Test
    public void testPass() {
        addMembership(1L, 1L);
        Assertions.assertDoesNotThrow(this::tryPass);
    }

    @Test
    public void testNoMembership() {
        Assertions.assertThrows(IllegalArgumentException.class, this::tryPass);
    }

    @Test
    public void testMembershipExpiredAndThenProlonged() {
        addExpiredMembership(1L);
        Assertions.assertThrows(IllegalArgumentException.class, this::tryPass);
        extend(1L, 2L);
        Assertions.assertDoesNotThrow(this::tryPass);
    }
}
