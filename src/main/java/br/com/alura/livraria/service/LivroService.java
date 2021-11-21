package br.com.alura.livraria.service;

import br.com.alura.livraria.dto.AtualizacaoLivroFormDto;
import br.com.alura.livraria.dto.LivroDto;
import br.com.alura.livraria.dto.LivroFormDto;

import br.com.alura.livraria.entities.Autor;
import br.com.alura.livraria.entities.Livro;

import br.com.alura.livraria.repositories.AutorRepository;
import br.com.alura.livraria.repositories.LivroRepository;

import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private AutorRepository autorRepository;

    private ModelMapper modelMapper = new ModelMapper();

    @Transactional(readOnly = true)
    public Page<LivroDto> listar(Pageable paginacao) {
        Page<Livro> livros = livroRepository.findAll(paginacao);
        return livros.map(x -> modelMapper.map(x, LivroDto.class));
    }

    @Transactional
    public LivroDto cadastrar(LivroFormDto livroFormDto) {
        try {
            Livro livro = modelMapper.map(livroFormDto, Livro.class);
            Long autorId = livroFormDto.getAutorId();
            Autor autor = autorRepository.getById(autorId);
            livro.setId(null);
            livro.setAutor(autor);

            livroRepository.save(livro);

            return modelMapper.map(livro, LivroDto.class);
        } catch (EntityNotFoundException e) {
            throw new IllegalArgumentException("Autor inexistente!");
        }
    }

    @Transactional
    public LivroDto atualizar(AtualizacaoLivroFormDto dto) {
        Livro livro = livroRepository.getById(dto.getId());

        livro.atualizarInformacoes(dto.getTitulo(), dto.getDataLancamento(), dto.getNumeroPagina());
        return modelMapper.map(livro, LivroDto.class);
    }

    @Transactional
    public void remover(Long id) {
        livroRepository.deleteById(id);
    }

    public LivroDto listarPorId(Long id) {
        Livro livro = livroRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException());

        return modelMapper.map(livro, LivroDto.class);
    }
}
