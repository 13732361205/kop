package com.github.kop.rbac.repo.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.kop.rbac.module.entity.RbacUserBindRole;
import com.github.kop.rbac.repo.UserBindRoleRepository;
import com.github.kop.rbac.repo.mapper.RbacUserBindRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserBindRoleRepositoryImpl implements UserBindRoleRepository {
    @Autowired
    private RbacUserBindRoleMapper rbacUserBindRoleMapper;

    @Override
    public List<RbacUserBindRole> getRoleListByUserId(Long userId) {
        QueryWrapper<RbacUserBindRole> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda().eq(RbacUserBindRole::getUserId,userId);

        return rbacUserBindRoleMapper.selectList(queryWrapper);
    }

    @Override
    public int userBindRole(RbacUserBindRole rbacUserBindRole) {
        return rbacUserBindRoleMapper.insert(rbacUserBindRole);
    }
}
