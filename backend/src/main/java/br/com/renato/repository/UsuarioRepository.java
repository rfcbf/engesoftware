package br.com.renato.repository;

import br.com.renato.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>, QueryByExampleExecutor<Usuario> {

    @Query("SELECT t FROM Usuario t WHERE t.email = ?1 AND t.senha = ?2")
    Usuario findByEmailSenha(String email, String senha);

    @Query("SELECT t FROM Usuario t WHERE t.email = ?1")
    Usuario findByEmail(String email);

}
