package com.github.kop.rbac.repo.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.kop.rbac.module.entity.RbacCompanyUser;
import com.github.kop.rbac.repo.CompanyBindUserRepository;
import com.github.kop.rbac.repo.mapper.RbacCompanyUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(rollbackFor = Exception.class)
public class CompanyBindUserRepositoryImpl implements CompanyBindUserRepository {

  @Autowired private RbacCompanyUserMapper rbacCompanyUserMapper;

  @Override
  public Long createCompanyUser(RbacCompanyUser rbacCompanyUser) {
    this.rbacCompanyUserMapper.insert(rbacCompanyUser);
    Long id = rbacCompanyUser.getId();
    if(id == null){
      return null;
    }
    return id ;
  }

  @Override
  public List<RbacCompanyUser> getByUserId(Long uesrId) {
    QueryWrapper<RbacCompanyUser> queryWrapper = new QueryWrapper<>();
    queryWrapper.lambda().eq(RbacCompanyUser::getUserId, uesrId);
    return rbacCompanyUserMapper.selectList(queryWrapper);
  }
}
