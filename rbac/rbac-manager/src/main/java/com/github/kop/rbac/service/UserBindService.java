package com.github.kop.rbac.service;

import com.github.kop.rbac.module.req.user.UserBindRoleReq;

public interface UserBindService {

  /** 根据用户id和企业id获取部门名称 */
  String getBindDeptName(Long userId, Long companyId);

  /** 根据用户id和企业id获取主岗位名称 */
  String getBindMainPostName(Long userId, Long companyId);

  /** 用户绑定企业部门 */
  boolean bindDept(long userId, long companyId, long deptId);

  /** 用户绑定岗位 */
  boolean bindPost(long userID, long companyId, long postId, boolean main);

  /** 用户解绑企业部门 */
  boolean unBindDept(long userId, long companyId, long deptId);

  /** 用户解绑岗位 */
  boolean unBindPost(long userID, long companyId, long postId);

  /**admin绑定用户和角色之间的关系*/
  int    adminBindRole(UserBindRoleReq req);


}
