package org.isiktir.isupport.domain.entities;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;

@Entity(name = "roles")
public class Role extends BaseEntity implements GrantedAuthority {

    private String authority;

    public Role() {
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
