package com.github.kop.rbac.service.impl;

import com.github.kop.rbac.module.entity.RbacUserBindDept;
import com.github.kop.rbac.module.entity.RbacUserBindPost;
import com.github.kop.rbac.module.entity.RbacUserBindRole;
import com.github.kop.rbac.module.ex.NoceException;
import com.github.kop.rbac.module.ex.ValidateException;
import com.github.kop.rbac.module.req.user.AdminBindRole;
import com.github.kop.rbac.module.res.dept.DeptQueryRes;
import com.github.kop.rbac.module.res.post.PostQueryRes;
import com.github.kop.rbac.repo.UserBindDeptRepository;
import com.github.kop.rbac.repo.UserBindPostRepository;
import com.github.kop.rbac.repo.UserBindRoleRepository;
import com.github.kop.rbac.service.DeptService;
import com.github.kop.rbac.service.PostService;
import com.github.kop.rbac.service.UserBindService;
import com.github.kop.rbac.utils.UserInfoThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = {Exception.class})
public class UserBindServiceImpl implements UserBindService {
  @Autowired private UserBindDeptRepository userBindDeptRepository;
  @Autowired private UserBindRoleRepository  userBindRoleRepository;
  @Autowired private UserBindPostRepository userBindPostRepository;
  @Autowired private DeptService deptService;
  private final PostService postService;

  public UserBindServiceImpl(PostService postService) {
    this.postService = postService;
  }

  @Override
  public String getBindDeptName(Long userId, Long companyId) {
    RbacUserBindDept userBindDept = this.userBindDeptRepository.getBind(userId, companyId);
    if (userBindDept != null) {
      Long deptId = userBindDept.getDeptId();
      DeptQueryRes deptQueryRes = deptService.byId(deptId);
      if (deptQueryRes != null) {
        return deptQueryRes.getName();
      }
    }
    return null;
  }

  @Transactional(rollbackFor = {Exception.class})
  @Override
  public boolean bindDept(long userId, long companyId, long deptId) {
    RbacUserBindDept rbacUserBindDept = new RbacUserBindDept();
    rbacUserBindDept.setUserId(userId);
    rbacUserBindDept.setDeptId(deptId);
    rbacUserBindDept.setCompanyId(companyId);
    // 判断是否存在
    boolean b = this.userBindDeptRepository.hash(userId, deptId, companyId);
    if (b) {
      return true;
    }
    return userBindDeptRepository.create(rbacUserBindDept) > 0;
  }

  @Override
  public String getBindMainPostName(Long userId, Long companyId) {
    RbacUserBindPost userBindPost = this.userBindPostRepository.getBind(userId, companyId);
    if (userBindPost != null) {
      Long postId = userBindPost.getPostId();
      PostQueryRes postQueryRes = postService.byId(postId);
      if (postQueryRes != null) {
        return postQueryRes.getName();
      }
    }
    return null;
  }

  @Transactional(rollbackFor = {Exception.class})
  @Override
  public boolean bindPost(long userID, long companyId, long postId, boolean main) {
    boolean ex = userBindPostRepository.hashMainPost(userID, companyId);
    if (ex) {
      throw new ValidateException("当前用户已存在主职");
    }
    return this.userBindPostRepository.create(userID, companyId, postId, main) > 0;
  }

  @Transactional(rollbackFor = {Exception.class})
  @Override
  public boolean unBindDept(long userId, long companyId, long deptId) {
    return this.userBindDeptRepository.delete(userId, companyId, deptId);
  }

  @Transactional(rollbackFor = {Exception.class})
  @Override
  public boolean unBindPost(long userID, long companyId, long postId) {
    return this.userBindPostRepository.delete(userID, companyId, postId);
  }


  @Override
  @Transactional(rollbackFor = {Exception.class})
  public int adminBindRole(AdminBindRole req) {
    if(UserInfoThread.getIsAdmin()) {
      throw new NoceException("非admin不允许操作");
    }
    List<RbacUserBindRole> rbacUserBindRoleList = userBindRoleRepository.getRoleListByUserId(req.getUserId());
    rbacUserBindRoleList.forEach(a -> {
      if(a.getRoleId().equals(req.getRoleId())){
        throw  new NoceException("该用户已经和这个角色绑定");
      }
    });
    RbacUserBindRole rbacUserBindRole=new RbacUserBindRole();
    rbacUserBindRole.setRoleId(req.getRoleId());
    rbacUserBindRole.setUserId(req.getUserId());
    return userBindRoleRepository.userBindRole(rbacUserBindRole);
  }


}
