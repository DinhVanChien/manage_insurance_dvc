package com.insurance.application.config;

import com.insurance.application.service.impl.UserDetailsServiceImpl;
import com.insurance.application.utils.Constant;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    // Password encoder, để Spring Security sử dụng mã hóa mật khẩu người dùng
    @Autowired
    PasswordEncoder passwordEncoder;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
        System.out.println(passwordEncoder.encode("A123"));
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        // Các trang không yêu cầu login
        http.authorizeRequests().antMatchers(
                Constant.START_REQUEST,
                Constant.LOGIN_ANNOTATION,
                Constant.LOGOUT_ANNOTATION).permitAll();
        // Trang /userInfo yêu cầu phải login với vai trò ROLE_USER hoặc ROLE_ADMIN.
        // Nếu chưa login, nó sẽ redirect tới trang /login.
        http.authorizeRequests().antMatchers(Constant.INSURANCE_ANNOTATION)
                .access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')");
        // Trang chỉ dành cho ADMIN
        http.authorizeRequests().antMatchers("/admin")
                .access("hasRole('ROLE_ADMIN')");
        http.authorizeRequests().anyRequest().authenticated();
        http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");
        // Cấu hình cho Login Form.
        http.authorizeRequests().and().formLogin()//
                // Submit URL của trang login
                .loginPage(Constant.LOGIN_ANNOTATION)
                .loginProcessingUrl(Constant.LOGIN_ANNOTATION) // Submit URL
                .defaultSuccessUrl(Constant.INSURANCE_ANNOTATION, true)
                .failureUrl("/login?error=true")//
                .usernameParameter(Constant.USERNAME)//
                .passwordParameter(Constant.PASSWORD)
                // Cấu hình cho Logout Page.
                .and().logout().logoutUrl(Constant.LOGOUT_ANNOTATION).
                logoutSuccessUrl("/logoutSuccessful");
        System.out.println("nahyr vào khi login");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**", "/static/**", "/css/**", "/js/**");
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}