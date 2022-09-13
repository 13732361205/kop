package com.github.kop.rbac.service;

import com.github.kop.rbac.module.req.user.UserBindCompanyReq;
import com.github.kop.rbac.module.req.user.CompanyCreateUserReq;

import java.util.List;

public interface CompanyUserService {

  Long createCompanyUser(CompanyCreateUserReq req);

  /**admin绑定用户和企业之间关系
   * @param req*/
  int bindCompany(UserBindCompanyReq req);
  /**校验用户和企业的绑定是否重复插入*/
  Boolean chenckRepetitionInsert(Long userId,Long companyId);
  /**根据用户id 拿到绑定的企业ld*/
  List<Long> getByUserId(Long userId);
}
