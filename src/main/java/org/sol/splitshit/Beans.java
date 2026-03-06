package org.sol.splitshit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
class Beans {
    @Bean
    public Clock realClock() {
        return Clock.systemUTC();
    }
}
