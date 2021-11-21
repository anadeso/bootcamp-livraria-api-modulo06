package br.com.alura.livraria.resources;

import br.com.alura.livraria.dto.AtualizacaoLivroFormDto;
import br.com.alura.livraria.dto.LivroDto;
import br.com.alura.livraria.dto.LivroFormDto;
import br.com.alura.livraria.service.LivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;

@RestController
@RequestMapping("/livros")
public class LivroResource {

    @Autowired
    private LivroService livroService;

    @GetMapping
    public ResponseEntity<Page<LivroDto>> listar(Pageable pageable) {
        Page<LivroDto> list = livroService.listar(pageable);
        return ResponseEntity.ok().body(list);
    }

    @PostMapping
    public ResponseEntity<LivroDto> cadastrar(@RequestBody @Valid LivroFormDto livroFormDto) {
        LivroDto livroDto = livroService.cadastrar(livroFormDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(livroDto.getId()).toUri();
        return ResponseEntity.created(uri).body(livroDto);
    }

    @PutMapping
    public ResponseEntity<LivroDto> atualizar(@RequestBody @Valid AtualizacaoLivroFormDto dto) {
        LivroDto atualizada = livroService.atualizar(dto);
        return ResponseEntity.ok(atualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<LivroDto> delete(@PathVariable @NotNull Long id) {
        livroService.remover(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LivroDto> listaPorId(@PathVariable @NotNull Long id) {
        LivroDto dto = livroService.listarPorId(id);
        return ResponseEntity.ok(dto);
    }
}
