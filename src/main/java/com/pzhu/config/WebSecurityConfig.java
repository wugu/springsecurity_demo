package com.pzhu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity//开启SpringSecurity的自定义配置（在SpringBoot项目中可以省略此注解
@EnableMethodSecurity//开启基于方法的授权
public class WebSecurityConfig {

    /*@Bean
    public UserDetailsService userDetailsService(){
        //创建基于内存的用户信息管理器
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        //创建UserDetails对象，用于管理用户名、用户密码、用户角色、用户权限等内容，用manager管理
        manager.createUser(
                User.withDefaultPasswordEncoder().username("user").password("123").roles("USER").build()
        );
        return manager;
    }*/
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //authorizeRequests()：开启授权保护
        //anyRequest()：对所有请求开启授权保护
        //authenticated()：已认证请求会自动被授权
        http.authorizeHttpRequests(authorize -> authorize
                        /*.requestMatchers("/user/list").hasAnyAuthority("USER_LIST")
                        .requestMatchers("/user/add").hasAnyAuthority("USER_ADD")*/
                        .requestMatchers("/user/**").hasRole("ADMIN")
                        //对所有请求开启授权保护
                        .anyRequest()
                        //已认证请求会自动被授权
                        .authenticated()
                );
        //登录配置
        http.formLogin(
                        form ->{//自定义登录页面
                            form.loginPage("/login").permitAll()//.permitAll()作用无需授权即可访问当前页面
                                    .usernameParameter("myusername")//配置自定义的表单用户参数，默认值是username
                                    .passwordParameter("mypassword")//配置自定义的表单密码参数，默认值是password
                                    .failureUrl("/login?failure")//校验失败时跳转的地址，默认值是error
                                    .successHandler(new MyAuthenticationSuccessHandler())//认证成功时的处理
                                    .failureHandler(new MyAuthenticationFailureHandler())//认证失败时的处理
                            ;

                        }
                );//表单授权方式
                //.httpBasic(withDefaults());//基本授权方式

        //登出配置
        http.logout(logout ->{
            logout.logoutSuccessHandler(new MyLogoutSuccessHandler());//注销成功时的处理
        });
        //实现跨域
        http.cors(withDefaults());
        //会话并发处理（即只允许登录一台设备
        http.sessionManagement(session->{
           session.maximumSessions(1).expiredSessionStrategy(new MySessionInformationExpiredStrategy());
        });
        //错误处理
        http.exceptionHandling(exception  -> {
            exception.authenticationEntryPoint(new MyAuthenticationEntryPoint());//请求未认证的接口
            exception.accessDeniedHandler(new MyAccessDeniedHandler());
        });

        //关闭csrf攻击防御
        http.csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable());
        return http.build();
    }
    /*@Bean
    public UserDetailsService userDetailsService(){
        //创建基于数据库的用户信息管理器
        DBUserDetailsManager manager = new DBUserDetailsManager();
        return manager;
    }*/
}
