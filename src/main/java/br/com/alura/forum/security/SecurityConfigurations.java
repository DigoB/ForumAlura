package br.com.alura.forum.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.com.alura.forum.repositories.UsuarioRepository;

@EnableWebSecurity
@Configuration
public class SecurityConfigurations extends WebSecurityConfigurerAdapter {

    @Autowired
    private AutenticacaoService autenticacaoService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
    	return super.authenticationManager();
    }

    //Configurações de Autenticação(controle de acesso, login, etc)
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(autenticacaoService).passwordEncoder(new BCryptPasswordEncoder());
    }

    //Configurações de Autorização(quem pode acessar URL's, perfis de acesso, etc)
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
        .antMatchers(HttpMethod.GET, "/topicos").permitAll()
        .antMatchers(HttpMethod.GET, "/topicos/*").permitAll()
        .antMatchers(HttpMethod.POST, "/auth").permitAll()
        .antMatchers(HttpMethod.GET, "/actuator/**").permitAll()
        .antMatchers(HttpMethod.GET, "/actuator/**").permitAll()
        .antMatchers(HttpMethod.GET, "/swagger-ui/**").permitAll()
        .anyRequest().authenticated()
        //Csrf = Cross-Site Request Forgery - É um tipo de ataque hacker, está desabilitado pois o token
        // já faz o papel de defesa contra esse tipo de ataque.
        .and().csrf().disable()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        //Roda o filtro para pegar o token antes de fazer a autenticação
        .and().addFilterBefore(new AutenticacaoViaTokenFilter(tokenService, usuarioRepository), UsernamePasswordAuthenticationFilter.class);
    }

    //Configurações de recursos estáticos(js, css, imagens, etc)
    @Override
public void configure(WebSecurity web) throws Exception {
    web.ignoring()
        .antMatchers("/**.html", "/v2/api-docs", "/webjars/**", "/configuration/**", "/swagger-resources/**");
}
}
