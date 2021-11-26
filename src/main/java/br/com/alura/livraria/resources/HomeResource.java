package br.com.alura.livraria.resources;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
public class HomeResource {

    @GetMapping
    public String home() {
        return "Teste Pipoca";
    }
}
