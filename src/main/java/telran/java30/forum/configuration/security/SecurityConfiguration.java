package telran.java30.forum.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import telran.java30.forum.service.security.filter.ExpDateFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) /* доёт возможнать ставить секурити над каждым отдельным методом */
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Override
	public void configure(WebSecurity web)
			throws Exception {/* этот метог идет перед configure(HttpSecurity http) и вожнее */
		web.ignoring().antMatchers(HttpMethod.POST, "/account/user");
		super.configure(web);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.httpBasic();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.csrf()/* кроссайтовая подделка запроса, закрывает все запросы кроме GET */.disable()/* отключение */;
		http.authorizeRequests()
				.antMatchers("/forum/posts/**").permitAll()
				.antMatchers(HttpMethod.PUT,"/account/user/password")
				.authenticated().and()
				.addFilterAfter(new ExpDateFilter(),UsernamePasswordAuthenticationFilter.class).authorizeRequests()
				.antMatchers(HttpMethod.DELETE, "/account/user").authenticated()
				.antMatchers(HttpMethod.GET, "/forum/post/{id}/**").authenticated()
				.antMatchers(HttpMethod.PUT, "/forum/post/{id}/like", "/account/user").authenticated()
				.antMatchers(HttpMethod.POST, "/account/login").authenticated()
				.antMatchers("/account/user/{login}/role/{role}").hasRole("ADMINISTRATOR")
				.antMatchers(HttpMethod.DELETE, "/forum/post/{id}")
				.access("@customSecurity.checkAuthorityForDeletePost(#id,authentication) or hasRole('MODERATOR')")
				.antMatchers(HttpMethod.PUT, "/forum/post/{id}")
				.access("@customSecurity.checkAuthorityForDeletePost(#id,authentication)")
				.antMatchers(HttpMethod.POST, "/forum/post/{author}", "/forum/post/{id}/comment/{author}")
				.access("#author==authentication.name")
				.antMatchers("/actuator/**").hasRole("ADMINISTRATOR");
				
		/* все запросы *//* permitAll()разрешено для всех *//* .authenticated()для зарегестрированных */

	}

}
