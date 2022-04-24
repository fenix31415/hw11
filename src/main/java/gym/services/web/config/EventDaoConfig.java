package gym.services.web.config;

import org.springframework.context.annotation.Bean;
import gym.events.IEventDao;
import gym.events.EventDao;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventDaoConfig {
    @Bean
    public IEventDao eventDao() {
        return new EventDao();
    }
}
