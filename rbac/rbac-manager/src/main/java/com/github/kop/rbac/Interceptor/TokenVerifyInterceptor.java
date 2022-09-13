package com.github.kop.rbac.Interceptor;

import com.github.kop.rbac.module.ex.NoceException;
import com.github.kop.rbac.utils.JwtTokenUtil;
import com.github.kop.rbac.utils.UserInfoThread;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Acer
 */
@Component
public class TokenVerifyInterceptor  implements HandlerInterceptor {


    private static final String TOKEN_HEADER = "token";
    private static final String ADMIN="admin";

    @Resource
    private JwtTokenUtil jwtTokenUtil;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String token = request.getHeader(TOKEN_HEADER);
        if (ObjectUtils.isEmpty(token)) {
            throw new NoceException("token无效");
        }
        // token 过期
        if (!jwtTokenUtil.validateToken(token)) {
            throw new NoceException("token无效");
        }
        String userId = jwtTokenUtil.getUserId(token);
        String companyId = jwtTokenUtil.getCompanyId(token);
        String roleCode=jwtTokenUtil.getRole(token);
        AtomicReference<Boolean> isAdmin= new AtomicReference<>(false);
        Arrays.stream(roleCode.split(",")).forEach(a->{
            if(a.equals(ADMIN)){
                isAdmin.set(true);
            }
        });


        if (ObjectUtils.isEmpty(userId)||ObjectUtils.isEmpty(companyId)) {
            throw new NoceException("token无效");
        }
        UserInfoThread.setIsAdmin(isAdmin.get());
        UserInfoThread.setUserId(Long.parseLong(userId));
        UserInfoThread.setCompanyId(Long.parseLong(companyId));
        return true;
    }

}
