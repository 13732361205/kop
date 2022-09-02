package com.github.kop.rbac.repo.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.kop.rbac.module.entity.RbacUser;
import com.github.kop.rbac.module.req.user.QueryUserReq;
import com.github.kop.rbac.repo.UserRepository;
import com.github.kop.rbac.repo.mapper.RbacUserMapper;
import com.github.kop.rbac.utils.UserInfoThread;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository {
  @Autowired private RbacUserMapper userMapper;

  @Override
  public RbacUser findByUsernameAndPassword(String username, String password) {
    return null;
  }

  @Override
  public Long create(RbacUser rbacUser) {
     int i = userMapper.insert(rbacUser);
     if(i ==1){
       return rbacUser.getId();
     }
    return null;
  }

  @Override
  public RbacUser byId(Long id) {
    return userMapper.selectById(id);
  }

  @Override
  public int update(RbacUser rbacUser) {
    return userMapper.updateById(rbacUser);
  }

  @Override
  public List<RbacUser> list(QueryUserReq req) {
    QueryWrapper<RbacUser> queryWrapper = new QueryWrapper<>();
    queryWrapper
        .lambda()
        .eq(RbacUser::getCompanyId, UserInfoThread.getCompanyId())
        .like(StringUtils.isNotBlank(req.getName()), RbacUser::getName, req.getName())
        .like(StringUtils.isNotBlank(req.getPhone()), RbacUser::getPhone, req.getPhone());
    return this.userMapper.selectList(queryWrapper);
  }

  @Override
  public int delete(Long id) {
    return this.userMapper.deleteById(id);
  }

  @Override
  public IPage<RbacUser> page(Long page, Long size, QueryUserReq req) {
    QueryWrapper<RbacUser> queryWrapper = new QueryWrapper<>();
    queryWrapper
        .lambda()
        .eq(RbacUser::getCompanyId, UserInfoThread.getCompanyId())
        .like(StringUtils.isNotBlank(req.getName()), RbacUser::getName, req.getName())
        .like(StringUtils.isNotBlank(req.getPhone()), RbacUser::getPhone, req.getPhone());
    return this.userMapper.selectPage(new Page<>(page, size), queryWrapper);
  }

  @Override
  public boolean existsName(String name) {
    QueryWrapper<RbacUser> queryWrapper = new QueryWrapper<>();
    queryWrapper.lambda().eq(RbacUser::getName, name);
    return this.userMapper.exists(queryWrapper);
  }

  @Override
  public boolean existsPhone(String phone) {
    QueryWrapper<RbacUser> queryWrapper = new QueryWrapper<>();
    queryWrapper.lambda().eq(RbacUser::getPhone, phone);
    return this.userMapper.exists(queryWrapper);
  }
}
