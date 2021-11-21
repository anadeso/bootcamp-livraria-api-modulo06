package br.com.alura.livraria.repositories;

import br.com.alura.livraria.entities.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerfilRepository extends JpaRepository<Perfil, Long> {

}
