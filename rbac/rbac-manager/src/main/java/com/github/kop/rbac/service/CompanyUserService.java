package com.github.kop.rbac.service;

import com.github.kop.rbac.module.req.user.CompanyCreateUserReq;

import java.util.List;

public interface CompanyUserService {

  /**
   * 企业内部创建用户，并将用户与企业做绑定
   * @param req
   * @return
   */
  Long createCompanyUser(CompanyCreateUserReq req);

  /**
   * 直接绑定用户与企业的关联关系
   * @param req
   * @return
   */
  Long companyBindUser(CompanyCreateUserReq req);



  /**校验用户和企业的绑定是否重复插入*/
  Boolean chenckRepetitionInsert(Long userId,Long companyId);
  /**根据用户id 拿到绑定的企业ld*/
  List<Long> getByUserId(Long userId);

}
