package com.curso.spring.data.jpa;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	/**
	 * Bean para encriptar las contraseñas de los usuarios
	 * 
	 * @return
	 */
	@Bean
	public static BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * Autenticacion
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		PasswordEncoder encoder = passwordEncoder();

		/**
		 * Creamos los usuarios y especificamos la encriptacion de la constraseña
		 * utilizando el objeto encoder
		 */
		UserBuilder users = User.builder().passwordEncoder(password -> {
			return encoder.encode(password);
		});

		/**
		 * Configurar/crear usuarios en memoria. Cuando se cre los usuarios,
		 * automaticamente el obheto UserBuilder('users') codificara la contraseña
		 */

		auth.inMemoryAuthentication().withUser(users.username("admin").password("123").roles("ADMIN", "USER"))
				.withUser(users.username("ronny").password("123").roles("USER"));
	}

	
	/**
	 * Autorizacion para las rutas  , restringuir acceso a rutas, recursos.
	 * Spring carga un filtro, intercetpa la peticion y realize la verificacion de acuerdo a la configuracion
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	
		http.authorizeRequests()
									// acceso a rutas publicas
									.antMatchers("/", "/listar", "/css/**", "/js/**", "/img/**").permitAll()
									// rutas privadas
									.antMatchers("/ver/**").hasAnyRole("USER")
									.antMatchers("/uploads/**").hasAnyAuthority("USER")
									.antMatchers("/form/**").hasAnyRole("ADMIN")
									.antMatchers("/delete/**").hasAnyRole("ADMIN")
									.antMatchers("/factura/**").hasAnyRole("ADMIN")
									.anyRequest().authenticated()
									.and()
									// formulario de login por defecto
									//.formLogin().permitAll()
									// Pagina personaliza para el login
									.formLogin().loginPage("/login").permitAll()
									.and()
									
									.logout().permitAll();
									
									
									
	}
	

}
