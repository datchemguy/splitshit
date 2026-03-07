package org.sol.splitshit.models;

import org.jspecify.annotations.NonNull;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Group {
    @Id @GeneratedValue
    private Long id;
    private String name;
    @OneToMany private Set<SUser> members = new HashSet<>();

    protected Group() {

    }

    public void addMember(SUser user) {
        members.add(user);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final Group group;

        private Builder() {
            group = new Group();
        }

        public Builder id(Long id) {
            group.id = id;
            return this;
        }

        public Builder name(String name) {
            group.name = name;
            return this;
        }

        public Builder member(@NonNull SUser member) {
            group.members.add(member);
            return this;
        }

        public Group build() {
            return group;
        }
    }
}
