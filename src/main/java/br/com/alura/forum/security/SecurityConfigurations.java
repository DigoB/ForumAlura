package br.com.alura.forum.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
public class SecurityConfigurations extends WebSecurityConfigurerAdapter {

    //Configurações de Autenticação(controle de acesso, login, etc)
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

    }

    //Configurações de Autorização(quem pode acessar URL's, perfis de acesso, etc)
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
        .antMatchers(HttpMethod.GET, "/topicos").permitAll()
        .antMatchers(HttpMethod.GET, "/topicos/*").permitAll()
        .anyRequest().authenticated()
        //Gera um formulário de autenticação automáticamente pelo Spring
        .and().formLogin();
    }

    //Configurações de recursos estáticos(js, css, imagens, etc)
    @Override
    public void configure(WebSecurity web) throws Exception {
        
    }
    
}
