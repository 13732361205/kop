package com.github.kop.rbac.repo;

import com.github.kop.rbac.module.entity.RbacUserBindRole;

import java.util.List;

public interface UserBindRoleRepository {

    /**根据用户id拿到关联的角色信息*/
    List<RbacUserBindRole> getRoleListByUserId(Long userId);

    /**将用户和角色绑定关系
     * @param rbacUserBindRole*/
    int userBindRole(RbacUserBindRole rbacUserBindRole);
}
