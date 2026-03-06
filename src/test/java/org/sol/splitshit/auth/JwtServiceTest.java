package org.sol.splitshit.auth;

import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.lifecycle.BeforeProperty;
import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.BeforeEach;

import java.time.Clock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class JwtServiceTest {
    private Clock clock;
    private JwtService jwtService;

    @BeforeEach @BeforeProperty
    public void setup() {
        clock = mock();
        jwtService = new JwtService("fdvvrsggwsshtesbg5hvetaw4bywv4wtve56yb34tv3", 10000, clock);
    }

    @Property
    public void preservesSubject(@ForAll @NonNull String username) {
        assertEquals(username, jwtService.extractSubject(jwtService.generate(username)));
    }
}
