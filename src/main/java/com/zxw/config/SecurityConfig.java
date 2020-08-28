package com.zxw.config;

import com.zxw.component.JwtAuthenticationTokenFilter;
import com.zxw.component.RestAuthenticationEntryPoint;
import com.zxw.component.RestfulAccessDeniedHandler;
import com.zxw.dto.AdminUserDetails;
import com.zxw.mbg.model.UmsAdmin;
import com.zxw.mbg.model.UmsPermission;
import com.zxw.service.UmsAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UmsAdminService adminService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RestfulAccessDeniedHandler restfulAccessDeniedHandler;
    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
   /* 在客户端需要认证的地方的configure()方法*/
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        System.out.println("SecurityConfig初始化");
        httpSecurity.csrf()// 由于使用的是JWT，我们这里不需要csrf
                .disable()
                .sessionManagement()// 基于token，所以不需要session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, // 允许对于网站静态资源的无授权访问
                        "/",
                        "/*.html",
                        "/favicon.ico",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js",
                        "/swagger-resources/**",
                        "/v2/api-docs/**",
                        "/swagger-ui.html/**",
                        "/swagger-ui.html",
                        "/webjars/**"
                )
                .permitAll()
                .antMatchers("/admin/login", "/admin/register")// 对登录注册要允许匿名访问
                .permitAll()
//                .antMatchers(HttpMethod.OPTIONS)//跨域请求会先进行一次options请求
//                .permitAll()
//                .antMatchers("/**")//测试时全部运行访问
//                .permitAll()
                .anyRequest()// 除上面外的所有请求全部需要鉴权认证
                .authenticated();
        // 禁用缓存
        httpSecurity.headers().cacheControl();
        // 添加JWT filter
        httpSecurity.addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        //添加自定义未授权和未登录结果返回
        httpSecurity.exceptionHandling()
        .accessDeniedHandler(restfulAccessDeniedHandler)
        .authenticationEntryPoint(restAuthenticationEntryPoint);
    }
    /*在授权认证的工程中则需要修改授权配置类WebSecurityConfig的configure方法：
    针对swagger-ui的请求路径进行放行：需要放行静态资源*/
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers(
//            "/",
//            "/*.html",
//            "/favicon.ico",
//            "/**/*.html",
//            "/**/*.css",
//            "/**/*.js",
//            "/swagger-resources/**",
//            "/v2/api-docs/**",
//            "/swagger-ui.html/**",
//            "/swagger-ui.html",
//            "/webjars/**"
//        )
//        .antMatchers(
//"/admin/login",
//             "/admin/register"
//        );
//
//    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder);
    }

    @Override
    @Bean
    public UserDetailsService userDetailsService() {
        //获取登录用户信息
        return username -> {
            System.out.println("SecurityConfig UserDetailsService userDetailsService()获取登录用户信息");
            UmsAdmin admin = adminService.getAdminByUsername(username);
            if (admin != null) {
                List<UmsPermission> permissionList = adminService.getPermissionList(admin.getId());
                return new AdminUserDetails(admin,permissionList);
            }
            throw new UsernameNotFoundException("用户名或密码错误");
        };
    }

    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter(){
        return new JwtAuthenticationTokenFilter();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
