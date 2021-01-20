package br.com.alura.forum.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.alura.forum.modelo.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {

    @Value("${forum.jwt.expiration}")
    private String expiration;

    @Value("${forum.jwt.secret}")
    private String secret;

	public String gerarToken(Authentication authentication) {
        Usuario logado = (Usuario) authentication.getPrincipal();
        Date hoje = new Date();
        Date dataExpiracao = new Date(hoje.getTime() + Long.parseLong(expiration));

        return Jwts.builder()
                //Informa quem é a aplicação que está gerando o token
                .setIssuer("API do forum da Alura")
                //Quem é o usuário autenticado a quem o token pertence
                .setSubject(logado.getId().toString())
                //Data de geração do Token
                .setIssuedAt(hoje)
                .setExpiration(dataExpiracao)
                //Insere a Criptografia do token
                .signWith(SignatureAlgorithm.HS256, secret)
                //Compacta e tranforma tudo em String
                .compact();
	}

}
