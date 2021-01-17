package com.example.demo.config;

import com.example.demo.handel.MyAuthenticationFailureHandler;
import com.example.demo.handel.MyAuthenticationSuccessHandler;
import com.example.demo.service.MyUserDetailsService;
import com.example.demo.strategy.MyExpiredSessionStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity // 默认开启security  可以不要
public class WebSecurity extends WebSecurityConfigurerAdapter {
    @Autowired
private MyUserDetailsService myUserDetailsService;
    @Bean
    public PasswordEncoder PasswordEncoder(){
    return new BCryptPasswordEncoder();
}
    @Bean
    public UserDetailsService userDetailsServiceBean(){
        return new MyUserDetailsService();
}

    protected  void configure(AuthenticationManagerBuilder auth)throws Exception{
//        auth.inMemoryAuthentication()
//                .withUser("cctang").password(PasswordEncoder().encode("123456"))
//                .authorities("admin")
//        .and()
//        .withUser("ZKHTYJ")
//        .password(PasswordEncoder().encode("123456"))
//        .authorities("admin");
auth.userDetailsService(myUserDetailsService);
}

    protected void configure(HttpSecurity http) throws  Exception {
            http.formLogin()//支持表单登录
                .loginPage("/login.html")//指定登录路径
                .loginProcessingUrl("/user/login"); //对应表单的action
                //.defaultSuccessUrl("/main.html")//登录成功 跳转main.html
                //.successHandler(new MyAuthenticationSuccessHandler("/main.html"))
                //.failureForwardUrl("/error.html");
                //.failureHandler(new MyAuthenticationFailureHandler("/error.html"));
                //sessiom 会话管理
            http.sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                    .invalidSessionUrl("/session/invalid")
                    //最大会话数
                    .maximumSessions(1)
                    //配置会话过期策略
                    .expiredSessionStrategy(new MyExpiredSessionStrategy())
                    //阻止用户第二次登录  挤兑登录
                    .maxSessionsPreventsLogin(true);
// 授权
                http.authorizeRequests()
                        .antMatchers("/login.html","/user/login","/error.html","/session/invalid").permitAll()
                        .anyRequest().authenticated(); //认证

                //关闭csrf服务保护
        http.csrf().disable();

}
}


