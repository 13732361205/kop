package com.github.kop.rbac.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.kop.rbac.module.entity.RbacUser;
import com.github.kop.rbac.module.ex.ValidateException;
import com.github.kop.rbac.module.req.user.CreateUserReq;
import com.github.kop.rbac.module.req.user.QueryUserReq;
import com.github.kop.rbac.module.req.user.UpdateUserReq;
import com.github.kop.rbac.module.res.company.CompanyQueryRes;
import com.github.kop.rbac.module.res.user.UserLoginRes;
import com.github.kop.rbac.module.res.user.UserQueryRes;
import com.github.kop.rbac.repo.UserRepository;
import com.github.kop.rbac.service.CompanyService;
import com.github.kop.rbac.service.UserBindService;
import com.github.kop.rbac.service.UserService;
import com.github.kop.rbac.utils.CreateValidate;
import com.github.kop.rbac.utils.JwtTokenUtil;
import com.github.kop.rbac.utils.UpdateValidate;
import com.github.kop.rbac.utils.UserInfoThread;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
@Getter
@Setter
public class UserServiceImpl implements UserService {
  protected final UserCreateAndUpdateValidate userCreateAndUpdateValidate =
      new UserCreateAndUpdateValidate();

  public UserServiceImpl() {}

  @Autowired private UserRepository userRepository;
  @Autowired private UserBindService userBindService;
  @Autowired private CompanyService companyService;

  @Override
  public Long create(CreateUserReq req) {
    userCreateAndUpdateValidate.createValidate(req);
    RbacUser rbacUser = new RbacUser();
    rbacUser.setName(req.getName());
    rbacUser.setPhone(req.getPhone());
    rbacUser.setGrade(req.getGrade());
    rbacUser.setPassword(
        DigestUtils.md5DigestAsHex(req.getPassword().getBytes(StandardCharsets.UTF_8)));
    rbacUser.setCompanyId(UserInfoThread.getCompanyId());

    return this.userRepository.create(rbacUser);
  }

  @Override
  public int update(UpdateUserReq req) {
    userCreateAndUpdateValidate.updateValidate(req);
    RbacUser rbacUser = this.userRepository.byId(req.getId());
    if (rbacUser != null) {

      if (!StringUtils.isEmpty(req.getName())) {
        rbacUser.setName(req.getName());
      }
      if (!StringUtils.isEmpty(req.getPhone())) {
        rbacUser.setPhone(req.getPhone());
      }

      if (req.getGrade() != null) {
        rbacUser.setGrade(req.getGrade());
      }

      return this.userRepository.update(rbacUser);
    }
    return -1;
  }

  @Override
  public UserQueryRes byId(Long id) {
    RbacUser rbacUser = this.userRepository.byId(id);
    if (rbacUser != null) {
      return conv(rbacUser);
    }
    return null;
  }

  private UserQueryRes conv(RbacUser rbacUser) {
    UserQueryRes userQueryRes = new UserQueryRes();
    userQueryRes.setId(rbacUser.getId());
    userQueryRes.setUserName(rbacUser.getName());
    userQueryRes.setPhone(rbacUser.getPhone());

    userQueryRes.setDeptName(
        userBindService.getBindDeptName(rbacUser.getId(), UserInfoThread.getCompanyId()));

    CompanyQueryRes companyQueryRes = companyService.byId(UserInfoThread.getCompanyId());
    if (companyQueryRes != null) {
      userQueryRes.setCompanyName(companyQueryRes.getName());
    }
    userQueryRes.setMainPostName(
        userBindService.getBindMainPostName(rbacUser.getId(), UserInfoThread.getCompanyId()));

    return userQueryRes;
  }

  @Override
  public int deleteById(Long id) {
    return this.userRepository.delete(id);
  }

  @Override
  public IPage<UserQueryRes> page(Long page, Long size, QueryUserReq req) {
    IPage<RbacUser> iPage = this.userRepository.page(page, size, req);
    return iPage.convert(this::conv);
  }

  @Override
  public List<UserQueryRes> list(QueryUserReq req) {
    List<RbacUser> list = this.userRepository.list(req);
    List<UserQueryRes> res = new ArrayList<>();
    for (RbacUser rbacUser : list) {
      UserQueryRes e = new UserQueryRes();
      e.setUserName(rbacUser.getName());
      e.setPhone(rbacUser.getPhone());
      e.setId(rbacUser.getId());
      res.add(e);
    }
    return res;
  }

  @Autowired private JwtTokenUtil jwtTokenUtil;

  @Override
  public UserLoginRes login(String username, String password, Long companyId) {
    RbacUser user =
        this.userRepository.findByUsernameAndPassword(
            username, DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8)));
    if (user != null) {
      UserLoginRes userLoginRes = new UserLoginRes();
      userLoginRes.setPhone(user.getPhone());
      userLoginRes.setName(user.getName());
      userLoginRes.setGrade(user.getGrade());
      userLoginRes.setCompanyId(user.getCompanyId());

      userLoginRes.setToken(jwtTokenUtil.generateToken(user.getId()));
      // 初始化用户关联信息
      String bindDeptName = this.userBindService.getBindDeptName(user.getId(), companyId);
      userLoginRes.setDeptName(bindDeptName);
      String bindMainPostName = this.userBindService.getBindMainPostName(user.getId(), companyId);
      userLoginRes.setMainPostName(bindMainPostName);
      // 权限信息初始化

      return userLoginRes;
    }
    return null;
  }

  protected class UserCreateAndUpdateValidate<T extends CreateUserReq>
      implements CreateValidate<T>, UpdateValidate<UpdateUserReq> {

    @Override
    public void createValidate(T createUserReq) throws ValidateException {
      String name = createUserReq.getName();
      if (StringUtils.isEmpty(name)) {
        throw new ValidateException("用户名必填");
      }
      boolean existsName = userRepository.existsName(name);
      if (existsName) {
        throw new ValidateException("用户名已存在");
      }
      String phone = createUserReq.getPhone();
      if (StringUtils.isEmpty(phone)) {
        throw new ValidateException("联系方式必填");
      }

      boolean existsPhone = userRepository.existsPhone(phone);
      if (existsPhone) {
        throw new ValidateException("联系方式已存在");
      }
    }

    @Override
    public void updateValidate(UpdateUserReq updateUserReq) throws ValidateException {
      idValidate(updateUserReq.getId());
      String name = updateUserReq.getName();
      if (!StringUtils.isEmpty(name)) {
        throw new ValidateException("用户名必填");
      }
      String phone = updateUserReq.getPhone();
      if (!StringUtils.isEmpty(phone)) {
        throw new ValidateException("联系方式必填");
      }
    }
  }
}
