package br.com.alura.livraria.resources;

import br.com.alura.livraria.entities.Autor;
import br.com.alura.livraria.entities.Perfil;
import br.com.alura.livraria.entities.Usuario;
import br.com.alura.livraria.repositories.AutorRepository;
import br.com.alura.livraria.repositories.LivroRepository;
import br.com.alura.livraria.repositories.PerfilRepository;
import br.com.alura.livraria.repositories.UsuarioRepository;
import br.com.alura.livraria.service.LivroService;
import br.com.alura.livraria.service.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class LivroResourceTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private LivroService livroService;

    private String token;

    @BeforeEach
    public void gerarToken() {
        // Criando user com perfil admin e salvando no banco
        Usuario logado = new Usuario("Rodrigo", "rodrigo", "501065", "andre@gmail.com");
        Perfil admin = perfilRepository.findById(1l).get();
        logado.adicionarPerfil(admin);
        usuarioRepository.save(logado);

        Authentication authentication = new UsernamePasswordAuthenticationToken(logado, logado.getLogin());
        this.token = tokenService.gerarToken(authentication);
    }

    @Test
    public void naoDeveriaCadastrarUsuarioComDadosIncompletos() throws Exception {
        String json = "{}";

        mvc
          .perform(MockMvcRequestBuilders
                  .post("/livros")
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(json)
                  .header("Authorization", "Bearer " +token))
                  .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void deveriaCadastrarUsuarioComDadosIncompletos() throws Exception {

        Autor autor =  new Autor("André da Silva", "andre@gmail.com", LocalDate.now(), "Vamos criar um minicurriculo");
        autorRepository.save(autor);

        String json = "{\"titulo\":\"Aprenda Python em 12 dias\",\"dataLancamento\" : \"01/01/2012\",\"numeroPagina\" : \"110\",\"autor_id\" :" + autor.getId() + " ,\"autor\" : {\"nome\": \"André da Silva\"}}";

        String jsonRetorno = "{\"titulo\": \"Aprenda Python em 12 dias\",\"dataLancamento\": \"01/01/2012\",\"numeroPagina\": 110,\"autor\": {\"nome\": \"André da Silva\",\"email\": \"andre@gmail.com\",\"dataNascimento\": \"29/11/2021\"}}";

        mvc
                .perform(MockMvcRequestBuilders
                        .post("/livros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .header("Authorization", "Bearer " +token))
                        .andExpect(MockMvcResultMatchers.status().isCreated())
                        .andExpect(MockMvcResultMatchers.header().exists("Location"))
                        .andExpect(MockMvcResultMatchers.content().json(jsonRetorno));
    }

}
