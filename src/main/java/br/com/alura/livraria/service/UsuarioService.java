package br.com.alura.livraria.service;

import br.com.alura.livraria.dto.UsuarioDto;
import br.com.alura.livraria.dto.UsuarioFormDto;
import br.com.alura.livraria.entities.Perfil;
import br.com.alura.livraria.entities.Usuario;
import br.com.alura.livraria.repositories.PerfilRepository;
import br.com.alura.livraria.repositories.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional(readOnly = true)
    public Page<UsuarioDto> listar(Pageable paginacao) {
        Page<Usuario> usuarios = usuarioRepository.findAll(paginacao);
        return usuarios.map(x -> modelMapper.map(x, UsuarioDto.class));
    }

    @Transactional
    public UsuarioDto cadastrar(UsuarioFormDto usuarioFormDto) {
        Usuario usuario = modelMapper.map(usuarioFormDto, Usuario.class);
        usuario.setId(null);

        Perfil perfil = perfilRepository.getById(usuarioFormDto.getPerfilId());
        usuario.adicionarPerfil(perfil);

        String senha = new Random().nextInt(999999) + "";
        usuario.setSenha(bCryptPasswordEncoder.encode(senha));

        usuarioRepository.save(usuario);
        return modelMapper.map(usuario, UsuarioDto.class);
    }

    public void remover(Long id) {
        usuarioRepository.deleteById(id);
    }
}
