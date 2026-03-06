package org.sol.splitshit.models;

import jakarta.persistence.Embeddable;

import java.time.LocalDateTime;

@Embeddable
public record Payment(long amount, LocalDateTime date) {
}
