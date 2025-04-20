package com.arbitr.cargoway.mapper;

import com.arbitr.cargoway.dto.general.profile.RoleDto;
import com.arbitr.cargoway.dto.rq.SignUpRequest;
import com.arbitr.cargoway.dto.rs.profile.UserRs;
import com.arbitr.cargoway.entity.security.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper
public interface UserMapper {
    @Mapping(source = "role", target = "role", qualifiedByName = "mapCargoWayRoleToUserRole")
    User buildUserFrom(SignUpRequest signUpRequest);

    UserRs buildUserRsFrom(User user);

    @Named("mapCargoWayRoleToUserRole")
    default String mapCargoWayRoleToUserRole(RoleDto role) {
        return role.name();
    }
}
