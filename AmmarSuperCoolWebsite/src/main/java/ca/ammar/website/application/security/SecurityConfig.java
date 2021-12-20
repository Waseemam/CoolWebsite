package ca.ammar.website.application.security;

import ca.ammar.website.application.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;


@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private LogAccessDeniedHandler accessDeniedHandler;
	@Autowired
	private void logAccessDeniedHandler (LogAccessDeniedHandler handler){
		this.accessDeniedHandler = handler;
	}

	private UserDetailsServiceImpl userDetailsService;
	@Autowired
	private void userDetailsServiceImpl (UserDetailsServiceImpl service){
		this.userDetailsService = service;
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Override
	public void configure(HttpSecurity http) throws Exception{

		// disables Cross-site request forgery
		http.csrf().disable();
		// disables headers and frame options
		http.headers().frameOptions().disable();

		http.authorizeRequests()
				//Need the USER role for /user/** requests
				.antMatchers("/**").permitAll()
				.antMatchers("/secure/**").hasAnyAuthority("ADMIN")
				.antMatchers("/", "/js/**", "/css/**", "/images/**", "/register").permitAll() //this allows access to /index.html
				.and()//allows us to chain configuration calls
				.formLogin().loginPage("/login") //we use this to map to login.html
				.defaultSuccessUrl("/") //if not deep-linking (later)
				.and()
				.logout()
				.invalidateHttpSession(true)
				.clearAuthentication(true)
				.and()
				.exceptionHandling() //use our handler
				.accessDeniedHandler(accessDeniedHandler);
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth)throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(NoOpPasswordEncoder.getInstance());
	}
}