package com.github.kop.bbs.api.manager;

import com.github.kop.bbs.module.req.user.UpdateUserReq;
import com.github.kop.bbs.module.res.RespVO;
import com.github.kop.bbs.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auth ahxiaoqi
 * @desc 用户控制器
 * @time 2022/8/9 22:17
 */
@Api(tags = "用户接口")
@RestController
@RequestMapping("/user")
public class UserController {

  @Autowired
  private UserService userService;


  @ApiOperation(value = "修改用户")
  @PutMapping("/{id}")
  public RespVO<Boolean> updateUser(
      @PathVariable(value = "id") Long id, @RequestBody UpdateUserReq req) {
    req.setId(id);
    return RespVO.success(userService.updateUser(req));
  }


}
