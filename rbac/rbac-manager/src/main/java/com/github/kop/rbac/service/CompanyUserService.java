package com.github.kop.rbac.service;


import com.github.kop.rbac.module.req.companyUser.CompanyUserReq;
import com.github.kop.rbac.module.req.user.CompanyCreateUserReq;
import com.github.kop.rbac.module.req.user.CreateUserReq;

public interface CompanyUserService {

    Long  createCompanyUser(CompanyCreateUserReq req);




}
