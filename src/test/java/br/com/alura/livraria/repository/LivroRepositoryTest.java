package br.com.alura.livraria.repository;

import br.com.alura.livraria.dto.ItemAutorDto;
import br.com.alura.livraria.entities.Autor;
import br.com.alura.livraria.entities.Livro;
import br.com.alura.livraria.repositories.LivroRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class LivroRepositoryTest {

    @Autowired
    private LivroRepository repository;

    @Autowired
    private TestEntityManager em;

    @Test
    void deveriaRetornarRelatorioAutorLivro() {
        Autor autor1 = new Autor(
                "André da Silva",
                "andre@gmail.com",
                LocalDate.now(),
                "André desenvolvedor Java e escritor de livros técnicos"
        );
        em.persist(autor1);

        Autor autor2 = new Autor(
                "Fernanda Nogueira",
                "fernanda@gmail.com",
                LocalDate.now(),
                "Fernanda desenvolvedora Java e escritora de livros técnicos"
        );
        em.persist(autor2);

        Autor autor3 = new Autor(
                "Juliana Carvalho",
                "juliana@gmail.com",
                LocalDate.now(),
                "Juliana desenvolvedora Java e escritora de livros técnicos"
        );
        em.persist(autor3);

        Autor autor4 = new Autor(
                "Rita",
                "rita@gmail.com",
                LocalDate.now(),
                "Rita desenvolvedora Java e escritora de livros técnicos"
        );
        em.persist(autor4);

        Autor autor5 = new Autor(
                "Rodrigo de Souza",
                "rodrigo@gmail.com",
                LocalDate.now(),
                "Rodrigo desenvolvedor Java e escritor de livros técnicos"
        );
        em.persist(autor5);

        Livro l1 = new Livro(
            "Aprenda Java em 21 dias",
                LocalDate.now(),
                100,
                autor1);
        em.persist(l1);

        Livro l2 = new Livro(
                "Como ser mais produtivo ",
                LocalDate.now(),
                100,
                autor2);
        em.persist(l2);

        Livro l3 = new Livro(
                "Aprenda a falar em público",
                LocalDate.now(),
                202,
                autor3);
        em.persist(l3);

        Livro l4 = new Livro(
                "Otimizando seu tempo",
                LocalDate.now(),
                150,
                autor2);
        em.persist(l4);

        Livro l5 = new Livro(
                "Como fazer bolos incrívei",
                LocalDate.now(),
                130,
                autor4);
        em.persist(l5);

        Livro l6 = new Livro(
                "Investindo em ações na bolda de valores",
                LocalDate.now(),
                120,
                autor5);
        em.persist(l6);

        Livro l7 = new Livro(
                "Aprenda Python em 12 dias",
                LocalDate.now(),
                110,
                autor1);
        em.persist(l7);

        List<ItemAutorDto> itemAutorDtos = repository.relatorioAutorLivros();
        Assertions
                .assertThat(itemAutorDtos)
                .hasSize(5)
                .extracting(ItemAutorDto::getAutor, ItemAutorDto::getQuantidadeLivros, ItemAutorDto::getPercentual)
                .containsExactlyInAnyOrder(
                        Assertions.tuple("André da Silva", 2l, 0.285714),
                        Assertions.tuple("Fernanda Nogueira", 2l, 0.285714),
                        Assertions.tuple("Juliana Carvalho", 1l, 0.142857),
                        Assertions.tuple("Rita", 1l, 0.142857),
                        Assertions.tuple("Rodrigo de Souza", 1l, 0.142857)
                );
    }

}
