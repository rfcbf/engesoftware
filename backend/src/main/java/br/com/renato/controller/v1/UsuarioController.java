package br.com.renato.controller.v1;

import br.com.renato.dto.UsuarioDTO;
import br.com.renato.mapper.UsuarioMapper;
import br.com.renato.service.UsuarioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/usuario")
@Api("Endpoint para gerenciar usuário.")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @GetMapping()
    @ApiOperation("Listar todos os usuários.")
    public ResponseEntity<List<UsuarioDTO>> listarUsuario() {
        List<UsuarioDTO> listaDTO = usuarioMapper.toDto(usuarioService.listarTodos());
        return ResponseEntity.ok(listaDTO);
    }

    @GetMapping("/{idCadastro}")
    @ApiOperation("Listar somente um usuário.")
    public ResponseEntity<UsuarioDTO> buscarUsuario(@PathVariable final Long idCadastro) {
        UsuarioDTO dto = usuarioMapper.toDto(usuarioService.buscarUsuario(idCadastro));
        return ResponseEntity.ok(dto);
    }

    @PostMapping()
    @ApiOperation("Salvar usuário.")
    public ResponseEntity<UsuarioDTO> salvarCadastro(@RequestBody UsuarioDTO usuarioDTO) {
        UsuarioDTO dto = usuarioMapper.toDto(usuarioService.salvarUsuario(usuarioMapper.toEntity(usuarioDTO)));
        return ResponseEntity.ok(dto);
    }

    @PutMapping()
    @ApiOperation("Atualizar usuário.")
    public ResponseEntity<UsuarioDTO> atualizarCadastro(@RequestBody UsuarioDTO usuarioDTO) {
        UsuarioDTO dto = usuarioMapper.toDto(usuarioService.atualizarUsuario(usuarioMapper.toEntity(usuarioDTO)));
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{idCadastro}")
    @ApiOperation("Deletar um usuário.")
    public ResponseEntity<Void> deletarCadastro(@PathVariable final Long idCadastro) {
        usuarioService.deletarUsuario(idCadastro);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/auth/{email}/{pass}")
    @ApiOperation("Verifica se pode autenticar um usuário.")
    public ResponseEntity<UsuarioDTO> autenticarUsuario(@PathVariable final String email, @PathVariable final String pass) {
        UsuarioDTO dto = usuarioMapper.toDto(usuarioService.autenticarUsuario(email, pass));
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/checkemail/{email}")
    @ApiOperation("Verifica se o email já existe cadastrado.")
    public ResponseEntity<UsuarioDTO> verificaEmailExistente(@PathVariable final String email) {
        UsuarioDTO dto = usuarioMapper.toDto(usuarioService.verificaEmailExistente(email));
        return ResponseEntity.ok(dto);
    }


}
