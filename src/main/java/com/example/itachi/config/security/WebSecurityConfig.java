/*
package com.example.itachi.config.security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    */
/**
     * 在这里可以设置登陆接口不设限访问
     * @param http
     * @throws Exception
     *//*

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                // 如果有允许匿名的url，填在下面
//                .antMatchers().permitAll()
                .anyRequest().authenticated()
                .and()
                // 设置登陆页
          .formLogin()
                //.loginPage("/static/userLogin")//登陆的页面

                .defaultSuccessUrl("/api/success").permitAll() //登陆成功后的跳转
                .loginProcessingUrl("/api/userLogin") //登陆的接口
                // 自定义登陆用户名和密码参数，默认为username和password
                .usernameParameter("phone")
                .passwordParameter("password")
                .and()
                .logout().permitAll();

        // 关闭CSRF跨域
        http.csrf().disable();
    }

    */
/**
     * 静态的资源请求不要拦截
     * @param web
     * @throws Exception
     *//*

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/js/**","/static/*");
    }
}
*/
