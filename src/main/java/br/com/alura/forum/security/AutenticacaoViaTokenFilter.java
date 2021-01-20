package br.com.alura.forum.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

//Classe para filtrar a autenticação do cliente ao fazer as requisições
public class AutenticacaoViaTokenFilter extends OncePerRequestFilter {

    //Não é possivel fazer injeção de dependencias em Filtros
	private TokenService tokenService;

    //Gerado o construtor no lugar da injeção de dependencias

    public AutenticacaoViaTokenFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

                String token = recuperarToken(request);
                boolean valido = tokenService.isTokenValido(token);
                System.out.println(valido);
                filterChain.doFilter(request, response);
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
