package org.sol.splitshit.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
public class SUser implements UserDetails {
    @Id private String username;
    private String password;
    @ManyToOne @JsonIgnore
    private Group group;
    @ElementCollection private List<Payment> payments = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public @Nullable String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public @Nullable Group getGroup() {
        return group;
    }

    public void setGroup(@Nullable Group group) {
        this.group = group;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
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
