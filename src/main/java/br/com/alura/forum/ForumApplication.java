package br.com.alura.forum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.web.config.EnableSpringDataWebSupport;


@SpringBootApplication
//Pega os campos, paginação e ordenação da requisição na URL e repassa para o Spring Data, porém, os parametros precisam
// estar escritos em ingles e da forma correta
@EnableSpringDataWebSupport
//Habilita o recurso de Cache
@EnableCaching
public class ForumApplication {

	public static void main(String[] args) {
		SpringApplication.run(ForumApplication.class, args);

	}

}
