package com.github.kop.rbac.service.impl;

import com.github.kop.rbac.module.entity.RbacCompanyUser;
import com.github.kop.rbac.module.ex.NoceException;
import com.github.kop.rbac.module.ex.ValidateException;
import com.github.kop.rbac.module.req.user.AdminBindReq;
import com.github.kop.rbac.module.req.user.CompanyCreateUserReq;
import com.github.kop.rbac.module.req.user.CreateUserReq;
import com.github.kop.rbac.module.req.user.UpdateUserReq;
import com.github.kop.rbac.repo.CompanyUserRepository;
import com.github.kop.rbac.service.CompanyUserService;
import com.github.kop.rbac.utils.JwtTokenUtil;
import com.github.kop.rbac.utils.UserInfoThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CompanyUserServiceImpl extends UserServiceImpl implements CompanyUserService {

  private final CompanyUserCreateAndUpdateValidate companyUserCreateAndUpdateValidate =
      new CompanyUserCreateAndUpdateValidate();
  @Autowired
  private CompanyUserRepository companyUserRepository;
  @Autowired private JwtTokenUtil jwtTokenUtil;

  @Override
  @Transactional(rollbackFor = {Exception.class})
  public Long createCompanyUser(CompanyCreateUserReq req) {
    Long companyId = UserInfoThread.getCompanyId();
    req.setCompanyId(companyId);
    companyUserCreateAndUpdateValidate.createValidate(req);
    Long userId = create(req);
    RbacCompanyUser rbacCompanyUser = new RbacCompanyUser();
    rbacCompanyUser.setUserId(userId);
    rbacCompanyUser.setCompanyId(req.getCompanyId());
    companyUserRepository.createCompanyUser(rbacCompanyUser);

    return userId;
  }

  @Override
  @Transactional(rollbackFor = {Exception.class})
  public int bindCompany(AdminBindReq req) {
    if(UserInfoThread.getIsAdmin()){
      throw new NoceException("不是admin用户无法操作");
    }
    if(!chenckRepetitionInsert(req.getUserId(),req.getCompanyId())){
      throw  new NoceException("该用户与企业已建立绑定关系,不要重复绑定");
    }
    RbacCompanyUser rbacCompanyUser=new RbacCompanyUser();
    rbacCompanyUser.setCompanyId(req.getCompanyId());
    rbacCompanyUser.setUserId(req.getUserId());
    return companyUserRepository.createCompanyUser(rbacCompanyUser);
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


  @Override
  public Boolean chenckRepetitionInsert(Long userId, Long companyId) {
    List<Long> conpanyIdList = getByUserId(userId);
    boolean flag=true;
    for(Long a:conpanyIdList){
      if(a.equals(companyId)){
        return flag=false;
      }
    }
    return flag;
  }

  @Override
  public List<Long> getByUserId(Long userId) {
    List<RbacCompanyUser> rbacCompanyUserList = companyUserRepository.getByUserId(userId);
    List<Long> conpanyIdList=new ArrayList<>();
    rbacCompanyUserList.forEach(a->{
      conpanyIdList.add(a.getCompanyId());
    });
    return conpanyIdList;
  }
}
