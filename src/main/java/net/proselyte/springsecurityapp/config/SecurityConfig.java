//package net.proselyte.springsecurityapp.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.annotation.Order;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
//@Configuration
//@EnableWebSecurity
//@Order(200)
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
////    @Autowired
////    private UserDetailsService userDetailsService;
////
////    @Autowired
////    private PasswordEncoder passwordEncoder;
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
////        http.authorizeRequests()
////                .antMatchers("/", "/welcome", "/rating", "/profile", "/tasks", "/new_task", "/new_tasker").hasAnyRole("USER", "ADMIN")
////                .antMatchers("/admin").hasRole("ADMIN")
////                .antMatchers("/data/**", "/chat", "/statuser", "/worker").permitAll()
////                .and()
////                .formLogin()
////                .loginPage("/login")
////                .defaultSuccessUrl("/welcome")
////                .failureUrl("/login?error")
////                .usernameParameter("username")
////                .passwordParameter("password")
////                .and()
////                .logout()
////                .logoutSuccessUrl("/login?logout")
////                .and()
////                .csrf().disable();
//    }
//
////        @Autowired
////    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
////        auth.userDetailsService(userDetailsService)
////                .passwordEncoder(passwordEncoder);
////    }
////    @Override
////    public void configure(WebSecurity web) throws Exception {
////        web.ignoring().antMatchers("/statuser");
////    }
////    @Bean
////    public PasswordEncoder passwordEncoder() {
////        return new BCryptPasswordEncoder(11);
////    }
////    @Bean
////    public UserDetailsService userDetailsService() {    return new UserDetailsServiceImpl();}
//
//}