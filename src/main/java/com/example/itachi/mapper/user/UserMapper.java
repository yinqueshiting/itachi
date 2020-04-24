package com.example.itachi.mapper.user;

import com.example.itachi.entity.Permissions;
import com.example.itachi.entity.Role;
import com.example.itachi.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {


    User queryAtLogin(@Param("phone") String phone);

    List<Role> selectRoleListByUserId(@Param("user_id") String userId);

    List<Permissions> selectPermissionsListByRoleId(@Param("id") Integer roleId);

    void updateUserPermissions(@Param("role_id") Integer roleId, @Param("permissions_id")Integer permissionsId);
}
