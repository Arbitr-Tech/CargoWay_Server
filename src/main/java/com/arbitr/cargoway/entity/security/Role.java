package com.arbitr.cargoway.entity.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.arbitr.cargoway.entity.security.Permission.CARGO_CRUD;
import static com.arbitr.cargoway.entity.security.Permission.TRANSPORT_CRUD;

@Getter
@RequiredArgsConstructor
public enum Role {
    ADMIN(
            Set.of(
                    CARGO_CRUD,
                    TRANSPORT_CRUD
            )
    ),
    CARRIER(
            Set.of(
                    TRANSPORT_CRUD
            )
    )
    ;

    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.name()))
                .collect(Collectors.toList());

        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

        return authorities;
    }
}
