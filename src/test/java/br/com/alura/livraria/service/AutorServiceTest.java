package br.com.alura.livraria.service;

import br.com.alura.livraria.dto.AutorDto;
import br.com.alura.livraria.dto.AutorFormDto;

import br.com.alura.livraria.repositories.AutorRepository;
import br.com.alura.livraria.repositories.LivroRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
public class AutorServiceTest {

    @Mock
    private LivroRepository livroRepository;

    @Mock
    private AutorRepository autorRepository;

    @InjectMocks
    private AutorService autorService;

    @Test
    void deveriaCadastrarUmAutor() {
        AutorFormDto autorFormDto = new AutorFormDto(
                "Rodrigo de Souza",
                "rodrigo@gmail.com",
                LocalDate.now(),
                "Rodrigo desenvolvedor Java e escritor de livros técnicos");


        AutorDto dto = autorService.cadastrar(autorFormDto);

        Assertions.assertEquals(autorFormDto.getNome(), dto.getNome());
        Assertions.assertEquals(autorFormDto.getEmail(), dto.getEmail());
        Assertions.assertEquals(autorFormDto.getDataNascimento(), dto.getDataNascimento());

    }

}
