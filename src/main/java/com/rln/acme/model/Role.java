package com.rln.acme.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Role {

    @Id
    private String id;

    public Role() {
        super();
    }

    public Role(String id) {
        super();
        this.setId(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Role)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        final Role rhs = (Role) obj;
        return new EqualsBuilder().append(id, rhs.id).isEquals();
    }
    public String getId() {
        return id;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).toHashCode();
    }

    public void setId(String id) {
        this.id = id;
    }

}