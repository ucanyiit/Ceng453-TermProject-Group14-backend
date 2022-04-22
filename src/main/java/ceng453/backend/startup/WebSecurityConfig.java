package ceng453.backend.startup;

import ceng453.backend.services.security.SecurityService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * This method is used to configure the security of the application.
     * @param http the http object that is used to configure the security.
     * @throws Exception if an error occurs.
     * @apiNote This method enables authorization for the endpoints. If an endpoint is wanted as public, then it should be
     * allowed in the method with antMatchers specifying the pattern.
     */
    String[] allowedPaths = new String[]{
            "/",
            "/api/auth/**",
            "/api/leaderboard/**",
            "/swagger-ui/**",
            "/swagger-resources/**",
            "/v2/api-docs"};
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(allowedPaths).permitAll()
                .anyRequest()
                .fullyAuthenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(new SecurityService(), UsernamePasswordAuthenticationFilter.class)
                .formLogin().disable()
                .logout().disable()
                .httpBasic().disable()
                .csrf().disable();
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}