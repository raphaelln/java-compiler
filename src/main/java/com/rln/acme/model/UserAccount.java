package com.rln.acme.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class UserAccount {

    @Id
    private String id;

    @Indexed(unique = true, direction = IndexDirection.DESCENDING, dropDups = true)
    private String username;

    private String password;

    private String firstname;

    private String lastname;

    private String status;

    private Boolean enabled;

    @DBRef
    private final List<Role> roles = new ArrayList<Role>();

    public void addRole(Role role) {
        this.roles.add(role);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof UserAccount)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        final UserAccount rhs = (UserAccount) obj;
        return new EqualsBuilder().append(id, rhs.id).isEquals();
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getId() {
        return id;
    }

    public String getLastname() {
        return lastname;
    }

    public String getPassword() {
        return password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public String getRolesCSV() {
        final StringBuilder sb = new StringBuilder();
        for (final Iterator<Role> iter = this.roles.iterator(); iter.hasNext(); )
        {
            sb.append(iter.next().getId());
            if (iter.hasNext()) {
                sb.append(',');
            }
        }
        return sb.toString();
    }

    public String getStatus() {
        return status;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).append(username).toHashCode();
    }

    public void removeRole(Role role) {
        //use iterator to avoid java.util.ConcurrentModificationException with foreach
        for (final Iterator<Role> iter = this.roles.iterator(); iter.hasNext(); )
        {
            if (iter.next().equals(role)) {
                iter.remove();
            }
        }
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}