package com.github.kop.rbac.service;


import com.github.kop.rbac.module.req.user.CompanyCreateUserReq;

public interface CompanyUserService {

    Long createCompanyUser(CompanyCreateUserReq req);
}
