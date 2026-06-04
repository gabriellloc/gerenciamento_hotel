package br.com.backend.gerenciamento_hotel.Config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Sistema de Gerenciamento para Hotel Multi-Torre", version = "v1.0.0", description = """
        A gestao de um hotel de grande porte envolve muito mais do que registrar entradas e saídas de hospedes. E preciso coordenar equipes de diferentes setores, controlar o estado de cada quarto em tempo real, garantir que reservas nao entrem em conflito e ainda oferecer uma boa experiencia ao hospede - tudo isso simultaneamente, todos os dias. Quando o hotel possui multiplas torres, andares e centenas de quartos, essa complexidade se multiplica e torna inviavel qualquer controle feito de forma manual ou descentralizada.
        Contatos:
        João Gustavo Carvalho Mendonça,
        Gabriel Oliveira Cardoso,
        Pedro Henrique Gomes Araujo Correia,
        Gileno Dos Santos Neto,
        José Santos do Nascimento Neto,
        José Gabriel Fontes dos Reis
        """, contact = @Contact(url = "https://github.com/gabriellloc/gerenciamento_hotel")), servers = {
        @Server(url = "http://localhost:8080", description = "Servidor Local") })
public class OpenApiConfig {

}
