package br.com.alura.livraria.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AtualizacaoLivroFormDto extends LivroFormDto {

    @NotNull
    private Long id;
}
