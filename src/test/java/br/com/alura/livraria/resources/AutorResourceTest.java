package br.com.alura.livraria.resources;

import br.com.alura.livraria.entities.Perfil;
import br.com.alura.livraria.entities.Usuario;
import br.com.alura.livraria.repositories.PerfilRepository;
import br.com.alura.livraria.repositories.UsuarioRepository;
import br.com.alura.livraria.service.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class AutorResourceTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

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
                  .post("/autores")
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(json)
                  .header("Authorization", "Bearer " +token))
                  .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void deveriaCadastrarUsuarioComDadosIncompletos() throws Exception {
        String json = "{\"nome\":\"Rodrigo de Souza\",\"email\":\"rodrigo@gmail.com\",\"dataNascimento\":\"02/10/1990\",\"miniCurriculo\":\"Rodrigo desenvolvedor Java e escritor de livros técnicos\"}";
        String jsonRetorno = "{\"nome\":\"Rodrigo de Souza\",\"email\":\"rodrigo@gmail.com\",\"dataNascimento\":\"02/10/1990\"}";

        mvc
                .perform(MockMvcRequestBuilders
                        .post("/autores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .header("Authorization", "Bearer " +token))
                        .andExpect(MockMvcResultMatchers.status().isCreated())
                        .andExpect(MockMvcResultMatchers.header().exists("Location"))
                        .andExpect(MockMvcResultMatchers.content().json(jsonRetorno));
    }

}
