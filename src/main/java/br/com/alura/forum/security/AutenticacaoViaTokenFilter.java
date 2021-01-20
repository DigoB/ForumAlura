package br.com.alura.forum.security;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.alura.forum.modelo.Usuario;
import br.com.alura.forum.repositories.UsuarioRepository;

//Classe para filtrar a autenticação do cliente ao fazer as requisições
public class AutenticacaoViaTokenFilter extends OncePerRequestFilter {

    // Não é possivel fazer injeção de dependencias em Filtros
    private TokenService tokenService;
    private UsuarioRepository repository;

    // Gerado o construtor no lugar da injeção de dependencias

    public AutenticacaoViaTokenFilter(TokenService tokenService, UsuarioRepository repository) {
        this.tokenService = tokenService;
        this.repository = repository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = recuperarToken(request);
        boolean valido = tokenService.isTokenValido(token);
        if (valido) {
            autenticarCliente(token);
        }
        filterChain.doFilter(request, response);
    }

    // Faz com que a autenticação libere as funções para o Cliente
    private void autenticarCliente(String token) {
        Long IdUsuario = tokenService.getIdUsuario(token);
        Usuario usuario = repository.findById(IdUsuario).get();
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private String recuperarToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if(token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
            return null;
        }
        //Retorna o token sem mencionar o "Bearer"(7)
        return token.substring(7, token.length());
        
    }
    
}
