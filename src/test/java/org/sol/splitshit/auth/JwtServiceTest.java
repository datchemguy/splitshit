package org.sol.splitshit.auth;

import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.AlphaChars;
import net.jqwik.api.constraints.NumericChars;
import net.jqwik.api.lifecycle.BeforeProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Clock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JwtServiceTest {
    private Clock clock;
    private JwtService jwtService;

    @BeforeEach @BeforeProperty
    public void setup() {
        clock = mock();
        when(clock.millis()).thenReturn(10000L);
        jwtService = new JwtService("fdvvrsggwsshtesbg5hvetaw4bywv4wtve56yb34tv3", Long.MAX_VALUE - 20000, clock);
    }

    @Property
    public void preservesSubject(@ForAll @AlphaChars @NumericChars char init,
                                 @ForAll String username) {
        username = init + username;
        assertEquals(username, jwtService.extractSubject(jwtService.generate(username)));
    }

    @Test
    public void edgeCases() {
        preservesSubject(' ', "   aaa");
    }
}
