package br.com.renato.controller.v1;


import br.com.renato.dto.CadastroDTO;
import br.com.renato.mapper.CadastroMapper;
import br.com.renato.service.CadastroService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/v1/cadastro")
@Api("Endpoint para gerenciar cadastro.")

public class CadastroController {

    @Autowired
    private CadastroService cadastroService;

    @Autowired
    private CadastroMapper cadastroMapper;

    @GetMapping()
    @ApiOperation("Listar todos os registros do cadastro.")
    public ResponseEntity<List<CadastroDTO>> listarCadastro() {
        List<CadastroDTO> listaDTO = cadastroMapper.toDto(cadastroService.listarTodos());
        return ResponseEntity.ok(listaDTO);
    }

    @GetMapping("/{idCadastro}")
    @ApiOperation("Listar somente um registros do cadastro.")
    public ResponseEntity<CadastroDTO> buscarCadastro(@PathVariable final Long idCadastro) {
        CadastroDTO dto = cadastroMapper.toDto(cadastroService.buscarCadastro(idCadastro));
        return ResponseEntity.ok(dto);
    }

    @PostMapping()
    @ApiOperation("Salvar um registro no cadastro.")
    public ResponseEntity<CadastroDTO> salvarCadastro(@RequestBody CadastroDTO cadastroDTO) {
        CadastroDTO dto = cadastroMapper.toDto(cadastroService.salvarCadastro(cadastroMapper.toEntity(cadastroDTO)));
        return ResponseEntity.ok(dto);
    }

    @PutMapping()
    @ApiOperation("Atualziar um registro no cadastro.")
    public ResponseEntity<CadastroDTO> atualizarCadastro(@RequestBody CadastroDTO cadastroDTO) {
        CadastroDTO dto = cadastroMapper.toDto(cadastroService.atualizarCadastro(cadastroMapper.toEntity(cadastroDTO)));
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{idCadastro}")
    @ApiOperation("Deletar um registro no cadastro.")
    public ResponseEntity<Void> deletarCadastro(@PathVariable final Long idCadastro) {
        cadastroService.deletarCadastro(idCadastro);
        return ResponseEntity.ok().build();
    }
}
