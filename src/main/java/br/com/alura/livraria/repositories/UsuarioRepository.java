package br.com.alura.livraria.repositories;

import br.com.alura.livraria.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UsuarioRepository  extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByLogin(String login);

    @Query("SELECT u FROM Usuario u JOIN FETCH u.perfis WHERE u.id = :idUsuario")
    Optional<Usuario> carregarPorIdComPerfis(Long idUsuario);
}
