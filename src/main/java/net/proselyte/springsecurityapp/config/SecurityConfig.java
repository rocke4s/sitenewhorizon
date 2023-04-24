package net.proselyte.springsecurityapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;



@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyAuthenticationSuccessHandler successHandler;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // Определение URL-адресов, которые требуют аутентификации и/или авторизации
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/welcome/**").hasAnyRole("ADMIN", "USER")
                .anyRequest().permitAll()
                .and()
                // Настройка формы входа на сайт
                .formLogin()
                .loginPage("/login") // URL-адрес страницы входа
                .successHandler(successHandler)
                .defaultSuccessUrl("/welcome") // URL-адрес страницы после успешной аутентификации
                .failureUrl("/login?error=true") // URL-адрес страницы при ошибочной аутентификации
                .and()
                // Настройка выхода из сайта
                .logout()
                .logoutUrl("/logout") // URL-адрес выхода из системы
                .logoutSuccessUrl("/login?logout=true") // URL-адрес страницы после успешного выхода из системы
                .invalidateHttpSession(true) // Удаление HttpSession при выходе из системы
                .deleteCookies("JSESSIONID") // Удаление куки JSESSIONID при выходе из системы
                .and()
                // Настройка работы сессий
                .sessionManagement()
                .invalidSessionUrl("/login?invalid=true") // URL-адрес при неверной сессии
                .maximumSessions(1) // Допуск только одной сессии на пользователя (по умолчанию не ограничено)
                .expiredUrl("/login?expired=true") // URL-адрес при истечении срока действия сессии
                .and();
                // Добавление защиты против CSRF-атак
                //.and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
