/*
 * Copyright 2002-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package sample.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import sample.custom.CustomEntityConverter;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * @author Joe Grandja
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	//@Autowired
	//private OAuth2UserService onekeyOAuth2UserService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests((authorizeRequests) ->
				authorizeRequests
					.mvcMatchers("/", "/public/**").permitAll()
					.anyRequest().authenticated()
			)
			.formLogin(withDefaults())
			.oauth2Client(withDefaults())
			.oauth2Login(oauth2->oauth2.userInfoEndpoint(userInfo->userInfo.userService(this.oauth2UserService()))
			);
	}

	private OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2UserService() {
		DefaultOAuth2UserService userService = new DefaultOAuth2UserService();
		userService.setRequestEntityConverter(new CustomEntityConverter());
		//userService.setRestOperations(new CustomRestOperations());
		return userService;
	}


	@Bean
	public UserDetailsService userDetailsService() {
		UserDetails userDetails = User.withDefaultPasswordEncoder()
			.username("user")
			.password("password")
			.roles("USER")
			.build();
		return new InMemoryUserDetailsManager(userDetails);
	}

	//@Bean(value = "onekeyOAuth2UserService")
	//public OnekeyOAuth2UserService onekeyOAuth2UserService(){
	//	OnekeyOAuth2UserService userService = new OnekeyOAuth2UserService();
	//	userService.setRequestEntityConverter(new CustomEntityConverter());
	//	userService.setRestOperations(new CustomRestOperations());
	//	return userService;
	//}
}
