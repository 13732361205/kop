package com.github.kop.rbac.repo;

import com.github.kop.rbac.module.entity.RbacCompanyUser;

import java.util.List;

public interface CompanyBindUserRepository {

  Long createCompanyUser(RbacCompanyUser rbacCompanyUser);

  List<RbacCompanyUser> getByUserId(Long  uesrId);
}
