package br.com.alura.livraria.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class LoginFormDto {

    @NotBlank
    private String login;

    @NotBlank
    private String senha;
}
