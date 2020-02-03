package com.example.h5api.config;

import com.example.h5api.service.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());

    }

    @Bean
    public JwtAuthenticationEntryPoint jwtAuthenticationEntryPointBean() throws Exception {
        return new JwtAuthenticationEntryPoint();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();

    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/user").permitAll()
                .antMatchers(HttpMethod.POST ,"/authenticate").permitAll()
               .antMatchers(HttpMethod.GET , "/user/{^[\\d]$").hasRole("ADMIN") // need help
              //  .antMatchers(HttpMethod.GET , "/user/[0-9]").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET , "*/user/list*").hasAnyRole("ADMIN","USER")
                .antMatchers(HttpMethod.GET , "/user/list/api*").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE , "/user*").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET , "/value/list").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET , "/value/list/api*").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET , "/nomination").hasAnyRole("ADMIN","USER")
                .antMatchers(HttpMethod.GET , "/nomination/list/").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET , "/nomination/list/api").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET , "/nomination/summary/*").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET , "/nomination/drawWinners").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET , "/nomination/drawWinners/*").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST , "/campaign/").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST , "/campaign/enable/*").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET , "/campaign/list/api").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET , "/campaign/get/*").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST , "/winner").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET , "/winner/*").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET , "/winner/hasRepeat").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET , "/winner/hasRepeat/*").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET , "/winner/findByCampaignId/*").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET , "/winner/list/").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET , "/winner/list/all").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET , "/winner/list/api").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

    }

}
