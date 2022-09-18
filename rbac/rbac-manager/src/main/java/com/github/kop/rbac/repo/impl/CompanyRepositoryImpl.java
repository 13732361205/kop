package com.github.kop.rbac.repo.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.kop.rbac.module.entity.RbacCompany;
import com.github.kop.rbac.module.req.company.QueryCompanyReq;
import com.github.kop.rbac.repo.CompanyRepository;
import com.github.kop.rbac.repo.mapper.RbacCompanyMapper;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(rollbackFor = Exception.class)
public class CompanyRepositoryImpl implements CompanyRepository {
  private final RbacCompanyMapper rbacCompanyMapper;

  public CompanyRepositoryImpl(RbacCompanyMapper rbacCompanyMapper) {
    this.rbacCompanyMapper = rbacCompanyMapper;
  }

  @Override
  public Long create(RbacCompany rbacCompany) {
   this.rbacCompanyMapper.insert(rbacCompany);
   if(rbacCompany.getId()!=null){
     return rbacCompany.getId();
   }
    return null;
  }

  @Override
  public int update(RbacCompany rbacCompany) {
    return this.rbacCompanyMapper.updateById(rbacCompany);
  }

  @Override
  public RbacCompany byId(Long id) {
    return this.rbacCompanyMapper.selectById(id);
  }

  @Override
  public int deleteById(Long id) {
    return this.rbacCompanyMapper.deleteById(id);
  }

  @Override
  public IPage<RbacCompany> page(Long page, Long size, QueryCompanyReq req) {
    QueryWrapper<RbacCompany> queryWrapper = new QueryWrapper<>();
    if (req != null) {

      queryWrapper
          .lambda()
          .like(StringUtils.isNoneEmpty(req.getName()), RbacCompany::getName, req.getName())
          .like(
              StringUtils.isNoneEmpty(req.getAddress()), RbacCompany::getAddress, req.getAddress())
          .like(
              StringUtils.isNoneEmpty(req.getSocialCreditCode()),
              RbacCompany::getSocialCreditCode,
              req.getSocialCreditCode());
    }
    return this.rbacCompanyMapper.selectPage(new Page<>(page, size), queryWrapper);
  }

  @Override
  public List<RbacCompany> list(QueryCompanyReq req) {
    QueryWrapper<RbacCompany> queryWrapper = new QueryWrapper<>();
    if (req != null) {
      queryWrapper
          .lambda()
          .like(StringUtils.isNoneEmpty(req.getName()), RbacCompany::getName, req.getName())
          .like(
              StringUtils.isNoneEmpty(req.getAddress()), RbacCompany::getAddress, req.getAddress())
          .like(
              StringUtils.isNoneEmpty(req.getSocialCreditCode()),
              RbacCompany::getSocialCreditCode,
              req.getSocialCreditCode());
    }
    return this.rbacCompanyMapper.selectList(queryWrapper);
  }
}
