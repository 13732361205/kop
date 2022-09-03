package com.github.kop.rbac.service.impl;

import com.github.kop.rbac.module.entity.RbacUserBindDept;
import com.github.kop.rbac.module.entity.RbacUserBindPost;
import com.github.kop.rbac.module.ex.ValidateException;
import com.github.kop.rbac.module.res.dept.DeptQueryRes;
import com.github.kop.rbac.module.res.post.PostQueryRes;
import com.github.kop.rbac.repo.UserBindDeptRepository;
import com.github.kop.rbac.repo.UserBindPostRepository;
import com.github.kop.rbac.service.DeptService;
import com.github.kop.rbac.service.PostService;
import com.github.kop.rbac.service.UserBindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = {Exception.class})
public class UserBindServiceImpl implements UserBindService {
  @Autowired private UserBindDeptRepository userBindDeptRepository;

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
}
