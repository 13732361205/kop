package com.github.kop.rbac.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.kop.rbac.module.req.user.CompanyCreateUserReq;
import com.github.kop.rbac.module.req.user.CreateUserReq;
import com.github.kop.rbac.module.req.user.QueryUserReq;
import com.github.kop.rbac.module.req.user.UpdateUserReq;
import com.github.kop.rbac.module.res.user.UserLoginRes;
import com.github.kop.rbac.module.res.user.UserQueryRes;
import java.util.List;

public interface UserService {
  Long create(CreateUserReq req);

  int update(UpdateUserReq req);

  UserQueryRes byId(Long id);

  int deleteById(Long id);

  IPage<UserQueryRes> page(Long page, Long size, QueryUserReq req);

  List<UserQueryRes> list(QueryUserReq req);

  UserLoginRes login(String username, String password, Long companyId);

}
