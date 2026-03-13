package org.sol.splitshit.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import org.hibernate.annotations.EmbeddedTable;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "s_user")
@JsonIncludeProperties({"username", "payments"})
public class SUser implements UserDetails {
    @Id private String username;
    @JsonIgnore private String password;
    @ManyToOne @JoinColumn(name = "group_id") @JsonIgnore
    private Group group;
    @ElementCollection @JoinTable(joinColumns = @JoinColumn(name = "username")) @EmbeddedTable("payments")
    private List<Payment> payments;

    protected SUser() {
        this(null, null, null, new ArrayList<>());
    }

    SUser(String username, String password, Group group, List<Payment> payments) {
        this.username = username;
        this.password = password;
        this.group = group;
        this.payments = payments;
    }

    @Override
    public @Nullable String getUsername() {
        return username;
    }

    @Override
    public @Nullable String getPassword() {
        return password;
    }

    @Override
    public @NonNull Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    public @Nullable Group getGroup() {
        return group;
    }

    public void setGroup(@Nullable Group group) {
        this.group = group;
    }

    public @NonNull List<Payment> getPayments() {
        return payments;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        SUser that = (SUser) obj;
        return Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final SUser user;

        private Builder() {
            user = new SUser();
        }

        public Builder username(String username) {
            user.username = username;
            return this;
        }

        public Builder password(String password) {
            user.password = password;
            return this;
        }

        public Builder group(Group group) {
            user.group = group;
            return this;
        }

        public SUser build() {
            return user;
        }
    }
}
