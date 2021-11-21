package br.com.alura.livraria.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Erro500Dto {

    private LocalDateTime time;
    private String erro;
    private String menssagem;
    private String path;
}
