package br.com.renato.service;

import br.com.renato.domain.Usuario;
import br.com.renato.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario salvarUsuario(Usuario usuario) {
        usuario.setId(null);
        return usuarioRepository.save(usuario);
    }

    public Usuario autenticarUsuario(String email, String senha ){
        return usuarioRepository.findByEmailSenha(email, senha);
    }

    public Usuario verificaEmailExistente(String email){
        return usuarioRepository.findByEmail(email);
    }


    public Usuario atualizarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Usuario buscarUsuario(Long idCadastro) {
        return usuarioRepository.findById(idCadastro).orElse(null);
    }

    public void deletarUsuario(Long idCadastro) {
        usuarioRepository.deleteById(idCadastro);
    }
}