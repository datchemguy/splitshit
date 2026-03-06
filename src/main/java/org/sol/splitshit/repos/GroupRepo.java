package org.sol.splitshit.repos;

import org.jspecify.annotations.NonNull;
import org.sol.splitshit.models.Group;
import org.springframework.data.jpa.repository.JpaRepository;

interface GroupRepo extends JpaRepository<@NonNull Group, @NonNull Long> {
}
