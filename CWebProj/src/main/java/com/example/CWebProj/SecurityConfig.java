package com.example.CWebProj;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.CWebProj.User.PrincipalOauth2UserService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {
	@Autowired
	private PrincipalOauth2UserService principalOauth2UserService;

	  @Bean
	    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	        http //.csrf(AbstractHttpConfigurer::disable)
	            .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
	            		
	            	.requestMatchers(new AntPathRequestMatcher("/**")).permitAll()            
	            	.requestMatchers(new AntPathRequestMatcher("/profile/update")).authenticated()
	            	.requestMatchers(new AntPathRequestMatcher("/autho/manager")).hasAuthority("ROLE_MANAGER")
	              .requestMatchers(new AntPathRequestMatcher("/autho/admin")).hasAuthority("ROLE_ADMIN"))
	            
	            .oauth2Login(oauth2Login -> oauth2Login
	                      .loginPage("/oauth2/authorization/google")
	                      .defaultSuccessUrl("/")
	                      .userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint
	                          .userService(principalOauth2UserService)
	                      )
	                  )
	              .formLogin((formLogin) -> formLogin
                    .loginPage("/signin")
                    .defaultSuccessUrl("/"))	
								
	              .logout((logout) -> logout
                    .logoutRequestMatcher(new AntPathRequestMatcher("/signout"))
                    .logoutSuccessUrl("/")
                    .invalidateHttpSession(true).deleteCookies("JSESSIONID")
                    )
	              ;
	        
	        
	        return http.build();
	    }


	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();

	}
//	 @Bean
//	    AuthenticationManager authenticationManager() {
//	        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//	        provider.setUserDetailsService(cuserService);
//	        provider.setPasswordEncoder(passwordEncoder());
//	        return new ProviderManager(provider);
//	    }
	
}