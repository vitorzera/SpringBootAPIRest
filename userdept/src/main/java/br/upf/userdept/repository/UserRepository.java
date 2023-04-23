package br.upf.userdept.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import br.upf.userdept.dto.UserDTO;

public interface UserRepository extends JpaRepository<UserDTO, Long> {

	public UserDTO findByEmail(String email);

	// montando querie utilizando implementações prontas do Spring DATA
	// pesquisa no campo name o que contem
	public List<UserDTO> findByNomeContaining(String name);

	// montando querie utilizando JPQL
	@Query("SELECT u FROM UserDTO u WHERE u.senha =:senha ORDER BY u.id DESC")
	public List<UserDTO> findByPorSenha(@Param("senha") String senha);

}