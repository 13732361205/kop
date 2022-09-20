package com.github.kop.rbac.service.impl;

import com.github.kop.rbac.module.ex.NoceException;
import com.github.kop.rbac.module.ex.ValidateException;
import com.github.kop.rbac.module.req.user.CompanyCreateUserReq;
import com.github.kop.rbac.module.req.user.CreateUserReq;
import com.github.kop.rbac.module.req.user.UpdateUserReq;
import com.github.kop.rbac.service.CompanyBindUserService;
import com.github.kop.rbac.service.CompanyUserService;
import com.github.kop.rbac.utils.UserInfoThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
public class CompanyUserServiceImpl extends UserServiceImpl  implements CompanyUserService {

  private final CompanyUserCreateAndUpdateValidate companyUserCreateAndUpdateValidate =
      new CompanyUserCreateAndUpdateValidate();

  @Autowired
  private CompanyBindUserService companyBindUserService;


  @Override
  @Transactional(rollbackFor = {Exception.class})
  public Long createCompanyUser(CompanyCreateUserReq req) {
    Long companyId = UserInfoThread.getCompanyId();
    req.setCompanyId(companyId);
    companyUserCreateAndUpdateValidate.createValidate(req);
    Long userId = create(req);
    companyBindUserService.bindUser(userId,companyId);

    return userId;
  }



  protected final class CompanyUserCreateAndUpdateValidate
      extends UserCreateAndUpdateValidate<CompanyCreateUserReq> {
    @Override
    public void createValidate(CompanyCreateUserReq createUserReq) throws ValidateException {
      super.createValidate(createUserReq);
      if(createUserReq.getCompanyId() == null) {
        throw new ValidateException("缺少企业id");
      }
    }

    @Override
    public void updateValidate(UpdateUserReq updateUserReq) throws ValidateException {
     throw new NoceException("系统异常");
    }

    @Override
    public void idValidate(Long id) throws ValidateException {}


  }

  @Override
  public Long create(CreateUserReq req) {
    return super.create(req);
  }



}
