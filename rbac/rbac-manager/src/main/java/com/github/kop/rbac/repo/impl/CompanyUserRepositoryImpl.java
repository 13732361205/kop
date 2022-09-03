package com.github.kop.rbac.repo.impl;

import com.github.kop.rbac.module.entity.RbacCompanyUser;
import com.github.kop.rbac.repo.CompanyUserRepository;
import com.github.kop.rbac.repo.mapper.RbacCompanyUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(rollbackFor = Exception.class)
public class CompanyUserRepositoryImpl implements CompanyUserRepository {

  @Autowired private RbacCompanyUserMapper rbacCompanyUserMapper;

  @Override
  public int createCompanyUser(RbacCompanyUser rbacCompanyUser) {
    return this.rbacCompanyUserMapper.insert(rbacCompanyUser);
  }
}
