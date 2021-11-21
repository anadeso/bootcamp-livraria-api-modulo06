package br.com.alura.livraria.resources;

import br.com.alura.livraria.dto.LoginFormDto;
import br.com.alura.livraria.dto.TokenDto;
import br.com.alura.livraria.infra.security.AutenticacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AutenticacaoResource {

    @Autowired
    private AutenticacaoService autenticacaoService;

    @PostMapping
    public TokenDto autenticar(@RequestBody @Valid LoginFormDto dto) {
        return new TokenDto(autenticacaoService.autenticar(dto));
    }
}
