package com.github.kop.rbac.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.kop.rbac.module.entity.RbacPost;
import com.github.kop.rbac.module.entity.RbacPostBindRoleGroup;
import com.github.kop.rbac.module.ex.ValidateException;
import com.github.kop.rbac.module.req.post.CreatePostReq;
import com.github.kop.rbac.module.req.post.QueryPostReq;
import com.github.kop.rbac.module.req.post.UpdatePostReq;
import com.github.kop.rbac.module.res.company.CompanyQueryRes;
import com.github.kop.rbac.module.res.dept.DeptQueryRes;
import com.github.kop.rbac.module.res.post.PostQueryRes;
import com.github.kop.rbac.repo.PostBindRoleGroupRepository;
import com.github.kop.rbac.repo.PostRepository;
import com.github.kop.rbac.service.CompanyService;
import com.github.kop.rbac.service.DeptService;
import com.github.kop.rbac.service.PostService;
import com.github.kop.rbac.utils.CreateValidate;
import com.github.kop.rbac.utils.UpdateValidate;
import com.github.kop.rbac.utils.UserInfoThread;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {

  protected static final PostCreateAndUpdateValidate VALIDATE = new PostCreateAndUpdateValidate();

  @Autowired private PostRepository postRepository;
  @Autowired private DeptService deptService;
  private final CompanyService companyService;

  public PostServiceImpl(CompanyService companyService) {
    this.companyService = companyService;
  }

  @Override
  public int create(CreatePostReq req) {
    VALIDATE.createValidate(req);

    RbacPost rbacPost = new RbacPost();
    rbacPost.setName(req.getName());
    rbacPost.setDeptId(req.getDeptId());
    rbacPost.setCompanyId(req.getCompanyId());

    return postRepository.create(rbacPost);
  }

  @Override
  public int update(UpdatePostReq req) {
    VALIDATE.updateValidate(req);

    RbacPost rbacPost = this.postRepository.byId(req.getId());
    if (rbacPost != null) {
      if (org.apache.commons.lang3.StringUtils.isNotBlank(req.getName())) {
        rbacPost.setName(req.getName());
      }

      if (req.getDeptId() != null) {
        rbacPost.setDeptId(req.getDeptId());
      }
      if (req.getCompanyId() != null) {
        rbacPost.setCompanyId(req.getCompanyId());
      }

      return postRepository.update(rbacPost);
    }
    return -1;
  }

  @Override
  public PostQueryRes byId(Long id) {
    RbacPost rbacPost = this.postRepository.byId(id);
    if (rbacPost != null) {
      return conv(rbacPost);
    }

    return null;
  }

  @NotNull
  private PostQueryRes conv(RbacPost rbacPost) {
    PostQueryRes postQueryRes = new PostQueryRes();
    postQueryRes.setName(rbacPost.getName());
    postQueryRes.setDeptId(rbacPost.getDeptId());
    postQueryRes.setCompanyId(rbacPost.getCompanyId());
    DeptQueryRes deptQueryRes = deptService.byId(rbacPost.getDeptId());
    if (deptQueryRes != null) {
      postQueryRes.setDeptName(deptQueryRes.getName());
    }
    CompanyQueryRes companyQueryRes = companyService.byId(rbacPost.getCompanyId());
    if (companyQueryRes != null) {
      postQueryRes.setCompanyName(companyQueryRes.getName());
    }
    return postQueryRes;
  }

  @Override
  public int deleteById(Long id) {
    return this.postRepository.delete(id);
  }

  @Override
  public IPage<PostQueryRes> page(Long page, Long size, QueryPostReq req) {
    IPage<RbacPost> iPage = this.postRepository.page(page, size, req);
    return iPage.convert(this::conv);
  }

  @Override
  public List<PostQueryRes> list(QueryPostReq req) {
    List<RbacPost> list = this.postRepository.list(req);
    List<PostQueryRes> res = new ArrayList<>();
    for (RbacPost rbacPost : list) {
      res.add(conv(rbacPost));
    }
    return res;
  }

  @Autowired private PostBindRoleGroupRepository postBindRoleGroupRepository;

  @Override
  public boolean bindRoleGroup(Long postId, Long roleGroupId) {
    RbacPostBindRoleGroup rbacPostBindRoleGroup = new RbacPostBindRoleGroup();
    rbacPostBindRoleGroup.setPostId(postId);
    rbacPostBindRoleGroup.setRoleGroupId(roleGroupId);

    return postBindRoleGroupRepository.create(rbacPostBindRoleGroup) > 0;
  }

  @Override
  public boolean bindRoleGroups(Long postId, List<Long> roleGroupIds) {
    List<RbacPostBindRoleGroup> ins = new ArrayList<>(roleGroupIds.size());
    for (Long aLong : roleGroupIds) {
      RbacPostBindRoleGroup rbacPostBindRoleGroup = new RbacPostBindRoleGroup();
      rbacPostBindRoleGroup.setPostId(postId);
      rbacPostBindRoleGroup.setRoleGroupId(aLong);
      ins.add(rbacPostBindRoleGroup);
    }
    return postBindRoleGroupRepository.create(ins);
  }

  @Override
  public boolean unBindRoleGroup(Long postId, Long roleGroupId) {
    RbacPostBindRoleGroup rbacPostBindRoleGroup = new RbacPostBindRoleGroup();
    rbacPostBindRoleGroup.setPostId(postId);
    rbacPostBindRoleGroup.setRoleGroupId(roleGroupId);
    return postBindRoleGroupRepository.delete(rbacPostBindRoleGroup) > 0;
  }

  @Override
  public List<Long> findRoleGroupIds(Long postId) {
    return this.postBindRoleGroupRepository.findRoleGroupIds(postId);
  }

  protected static final class PostCreateAndUpdateValidate
      implements CreateValidate<CreatePostReq>, UpdateValidate<UpdatePostReq> {

    private static void commonValidate(String name, Long companyId, Long deptId) {
      if (StringUtils.isEmpty(name)) {
        throw new ValidateException("岗位名称必填");
      }
      if (companyId == null) {
        throw new ValidateException("企业id必填");
      }
      // 当登陆作的企业id
      Long loginCompanyId = UserInfoThread.getCompanyId();
      if (!loginCompanyId.equals(companyId)) {
        throw new ValidateException("当前登录企业id和操作企业id不匹配");
      }

      if (deptId == null) {
        throw new ValidateException("部门id必填");
      }
    }

    @Override
    public void createValidate(CreatePostReq createPostReq) throws ValidateException {

      commonValidate(
          createPostReq.getName(), createPostReq.getCompanyId(), createPostReq.getDeptId());
      // TODO: 2022/7/30 是否拥有当前部门操作权限
    }

    @Override
    public void updateValidate(UpdatePostReq updatePostReq) throws ValidateException {
      idValidate(updatePostReq.getId());
      commonValidate(
          updatePostReq.getName(), updatePostReq.getCompanyId(), updatePostReq.getDeptId());
      // TODO: 2022/7/30 是否拥有当前部门操作权限

    }
  }
}
