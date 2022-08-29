package com.github.kop.rbac.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.kop.rbac.module.req.company.CreateCompanyReq;
import com.github.kop.rbac.module.req.company.QueryCompanyReq;
import com.github.kop.rbac.module.req.company.UpdateCompanyReq;
import com.github.kop.rbac.module.req.user.CreateUserReq;
import com.github.kop.rbac.module.res.RespVO;
import com.github.kop.rbac.module.res.company.CompanyQueryRes;
import com.github.kop.rbac.service.CompanyService;
import com.github.kop.rbac.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "企业接口")
@RestController
@RequestMapping("/company")
public class CompanyController {

    private final CompanyService companyService;
    @Autowired private UserService userService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @ApiOperation(value = "创建企业")
    @PostMapping("/")
    public RespVO<Boolean> create(@RequestBody CreateCompanyReq req) {
        return RespVO.success(companyService.create(req) > 1);
    }

    @ApiOperation(value = "修改企业")
    @PutMapping("/{id}")
    public RespVO<Boolean> update(
            @PathVariable(value = "id") Long id, @RequestBody UpdateCompanyReq req) {

        req.setId(id);
        return RespVO.success(companyService.update(req) > 1);
    }

    @ApiOperation(value = "获取企业信息")
    @GetMapping("/{id}")
    public RespVO<CompanyQueryRes> byId(@PathVariable(value = "id") Long id) {
        return RespVO.success(companyService.byId(id));
    }

    @ApiOperation(value = "删除企业")
    @DeleteMapping("/{id}")
    public RespVO<Boolean> deleteById(@PathVariable(value = "id") Long id) {

        return RespVO.success(companyService.deleteById(id) > 1);
    }

    @ApiOperation(value = "企业查询分页信息")
    @GetMapping("/{page}/{size}")
    public RespVO<IPage<CompanyQueryRes>> page(
            @PathVariable(value = "page") Long page,
            @PathVariable(value = "size") Long size,
            @RequestBody QueryCompanyReq req) {

        return RespVO.success(companyService.page(page, size, req));
    }

    @ApiOperation(value = "企业列表")
    @GetMapping("/list")
    public RespVO<List<CompanyQueryRes>> list(@RequestBody QueryCompanyReq req) {

        return RespVO.success(companyService.list(req));
    }

    @ApiOperation(value = "企业内创建用户")
    @PostMapping("/createUser")
    public RespVO<?> createUser(@RequestBody CreateUserReq req) {

     return RespVO.success(userService.createUser(req) > 1);
    }

}
