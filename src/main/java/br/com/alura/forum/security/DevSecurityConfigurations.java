package br.com.alura.forum.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
@Profile("dev")
public class DevSecurityConfigurations extends WebSecurityConfigurerAdapter {

    //Configurações de Autorização(quem pode acessar URL's, perfis de acesso, etc)
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
        .antMatchers("/**").permitAll()
        //Csrf = Cross-Site Request Forgery - É um tipo de ataque hacker, está desabilitado pois o token
        // já faz o papel de defesa contra esse tipo de ataque.
        .and().csrf().disable();
    }
}
