package org.sol.splitshit.repos;

import org.jspecify.annotations.NonNull;
import org.sol.splitshit.models.SUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<@NonNull SUser, @NonNull String> {
}
