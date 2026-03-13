package org.sol.splitshit.models;

import jakarta.persistence.*;
import org.hibernate.annotations.EmbeddedTable;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.*;

@Entity
@Table(name = "s_group")
public class Group {
    @Id @GeneratedValue @Column(name = "group_id")
    private Long id;
    private String name;
    @OneToMany @JoinColumn(name = "group_id")
    private Set<SUser> members;
    @ElementCollection @JoinTable(joinColumns = @JoinColumn(name = "group_id")) @EmbeddedTable("payments")
    private List<Payment> payments;

    protected Group() {
        this(null, null, new HashSet<>(), new ArrayList<>());
    }

    Group(Long id, String name, Set<SUser> members, List<Payment> payments) {
        this.id = id;
        this.name = name;
        this.members = members;
        this.payments = payments;
    }

    public @Nullable Long getId() {
        return id;
    }

    public @Nullable String getName() {
        return name;
    }

    public @NonNull Set<SUser> getMembers() {
        return members;
    }

    public @NonNull List<Payment> getPayments() {
        return payments;
    }

    public void addMember(@NonNull SUser user) {
        members.add(user);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return Objects.equals(id, group.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
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

        public Builder payment(@NonNull Payment payment) {
            group.payments.add(payment);
            return this;
        }

        public Group build() {
            return group;
        }
    }
}
