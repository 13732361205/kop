package com.github.kop.rbac.repo;

import com.github.kop.rbac.module.entity.RbacCompanyUser;

public interface CompanyUserRepository {

  int createCompanyUser(RbacCompanyUser rbacCompanyUser);
}
