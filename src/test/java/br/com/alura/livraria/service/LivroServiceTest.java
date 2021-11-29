package br.com.alura.livraria.service;

import br.com.alura.livraria.dto.LivroDto;
import br.com.alura.livraria.dto.LivroFormDto;
import br.com.alura.livraria.entities.Usuario;
import br.com.alura.livraria.repositories.AutorRepository;
import br.com.alura.livraria.repositories.LivroRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
public class LivroServiceTest {

    @Mock
    private LivroRepository livroRepository;

    @Mock
    private AutorRepository autorRepository;

    @InjectMocks
    private LivroService livroService;

    private Usuario logado;

    @BeforeEach
    public void before() {
        this.logado = new Usuario("Rodrigo", "rodrigo", "123455", "andre@gmail.com");
    }

    @Test
    void deveriaCadastrarUmLivro() {
        LivroFormDto livroFormDto = new LivroFormDto(
                "Testando nome livro",
                LocalDate.now(),
                100,
                1l);

        LivroDto dto = livroService.cadastrar(livroFormDto);

        //Mockito.verify(livroRepository.save(Mockito.any()));

        Assertions.assertEquals(livroFormDto.getTitulo(), dto.getTitulo());
        Assertions.assertEquals(livroFormDto.getDataLancamento(), dto.getDataLancamento());
        Assertions.assertEquals(livroFormDto.getNumeroPagina(), dto.getNumeroPagina());
    }

    @Test
    void naoDeveriaCadastrarLivroComUserInexistente() {
        LivroFormDto livroFormDto = new LivroFormDto(
                "Testando nome livro",
                LocalDate.now(),
                100,
                99999999l);

        Mockito.when(autorRepository.getById(livroFormDto.getAutorId()))
                .thenThrow(EntityNotFoundException.class);

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> livroService.cadastrar(livroFormDto));
    }
}
