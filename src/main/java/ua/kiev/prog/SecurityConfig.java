package ua.kiev.prog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

/**
 * Created by Smith on 20.02.2017.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {

        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery(
                        "select username,password,enabled from users where username=?")
                .authoritiesByUsernameQuery(
                        "select username,role from roles where username=?"); //TODO create a Role database!!!
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                    .antMatchers("/static/**").permitAll()
                    .antMatchers("/login/**","/register/**").permitAll()
                    .anyRequest().authenticated()
                .and()
                    .formLogin()
                    .loginPage("/login")
                    .successForwardUrl("/login?success")
                    .permitAll()
                .and()
                    .logout()
                    .logoutSuccessUrl("/login")
                    .permitAll()
                .and()
                    .csrf();
    }

//    private CsrfTokenRepository csrfTokenRepository()
//    {
//        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
//        repository.setSessionAttributeName("_csrf");
//        return repository;
//    }
}
