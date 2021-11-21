package br.com.alura.livraria.resources;

import br.com.alura.livraria.dto.AtualizacaoAutorDto;
import br.com.alura.livraria.dto.AutorDto;
import br.com.alura.livraria.dto.AutorFormDto;

import br.com.alura.livraria.service.AutorService;

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
@RequestMapping("/autores")
public class AutorResource {

    @Autowired
    private AutorService autorService;

    @GetMapping
    public ResponseEntity<Page<AutorDto>> listar(Pageable pageable) {
        Page<AutorDto> list = autorService.listar(pageable);
        return ResponseEntity.ok().body(list);
    }

    @PostMapping
    public ResponseEntity<AutorDto> cadastrar(@RequestBody @Valid AutorFormDto autorFormDto) {
        AutorDto autorDto = autorService.cadastrar(autorFormDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(autorDto.getId()).toUri();
        return ResponseEntity.created(uri).body(autorDto);
    }

    @PutMapping
    public ResponseEntity<AutorDto> atualizar(@RequestBody @Valid AtualizacaoAutorDto dto) {
        AutorDto atualizada = autorService.atualizar(dto);
        return ResponseEntity.ok(atualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AutorDto> delete(@PathVariable @Valid Long id) {
        autorService.remover(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AutorDto> listaPorId(@PathVariable @NotNull Long id) {
        AutorDto dto = autorService.listarPorId(id);
        return ResponseEntity.ok(dto);
    }
}
