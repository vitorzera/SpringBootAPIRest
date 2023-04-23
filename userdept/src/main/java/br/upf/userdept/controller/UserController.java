package br.upf.userdept.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import br.upf.userdept.dto.UserDTO;
import br.upf.userdept.service.UserService;
import br.upf.userdept.utils.TokenJWT;

@RestController
@RequestMapping(value = "/userdept/user")
public class UserController {

	@Autowired // mecanismo de injeção de dependência
	private UserService userService;

	@Autowired
	private ModelMapper modelMapper;

	@PostMapping(value = "/inserir")
	@ResponseStatus(HttpStatus.CREATED)
	public UserDTO inserir(@RequestBody UserDTO user,
			@RequestHeader(value = "token") String token) {
		TokenJWT.validarToken(token);
		return userService.salvar(user);
	}

	@GetMapping(value = "/listarTodos")
	@ResponseStatus(HttpStatus.OK)
	public List<UserDTO> listarTodos(@RequestHeader(value = "token") String token) {
		TokenJWT.validarToken(token);
		return userService.listarTodos();
	}

	@GetMapping(value = "/buscarPorId")
	@ResponseStatus(HttpStatus.OK)
	public UserDTO buscarPorId(@RequestHeader(value = "id") Long id, 
			@RequestHeader(value = "token") String token) {
		TokenJWT.validarToken(token);
		return userService.buscarPorId(id)
				// caso o cliente não foi encontrado...
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuario não encontrado."));
	}

	@DeleteMapping(value = "/delete")
	@ResponseStatus(HttpStatus.NO_CONTENT) // sem conteúdo
	public void removerUsuario(@RequestHeader(value = "id") Long id, 
			@RequestHeader(value = "token") String token) {
		TokenJWT.validarToken(token);
		userService.buscarPorId(id) // antes de deletar, busca na base o cliente...
				.map(usuario -> { // caso encontre o usuario, remova o mesmo
					userService.removerPorId(usuario.getId());
					return Void.TYPE;
					// caso não encontre, retorna o status
				}).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuario não encontrado."));
	}

	/**
	 * Para implementar o atualizar, é necessário incluir o método bean
	 * modelMapper() na classe UserdeptApplication.java
	 * 
	 * @param user
	 * @param id
	 */
	@PutMapping(value = "/atualizar")
	@ResponseStatus(HttpStatus.NO_CONTENT) // sem conteúdo
	public void atualizar(@RequestBody UserDTO user, 
			@RequestHeader(value = "token") String token) {
		TokenJWT.validarToken(token);
		userService.buscarPorId(user.getId()).map(usuarioBase -> {
			// recurso do modelMap que ferifica o que esta no parâmetro para
			// atualizar na base.
			// esse recurso necessita incluir a dependência modelMap no pom.xml
			modelMapper.map(user, usuarioBase);
			userService.salvar(usuarioBase);
			return Void.TYPE;
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuario não encontrado."));
	}

	@GetMapping(value = "/buscarPorEmail")
	@ResponseStatus(HttpStatus.OK)
	public UserDTO findByEmail(@RequestHeader(value = "email") String email, 
			@RequestHeader(value = "token") String token) {
		TokenJWT.validarToken(token);
		return userService.buscarPorEmail(email);
	}

	@GetMapping(value = "/buscarPorParteNome")
	@ResponseStatus(HttpStatus.OK)
	public List<UserDTO> buscarPorParteNome(@RequestHeader(value = "nome") String nome, 
			@RequestHeader(value = "token") String token) {
		TokenJWT.validarToken(token);
		return userService.buscarPorParteNome(nome);
	}

	@GetMapping(value = "/buscarPorSenha")
	@ResponseStatus(HttpStatus.OK)
	public List<UserDTO> buscarPorSenha(@RequestHeader(value = "senha") String senha, 
			@RequestHeader(value = "token") String token) {
		TokenJWT.validarToken(token);
		return userService.buscarPorSenha(senha);
	}

	@GetMapping(value = "/authorize")
	@ResponseStatus(HttpStatus.OK)
	public UserDTO authorize(@RequestHeader(value = "email") String email,
			@RequestHeader(value = "password") String password) {
		UserDTO userDTO;
		// validando se usuario e senha foram enviados corretamente...
		if (email != null && !email.isEmpty() && password != null && !password.isEmpty()) {
			userDTO = userService.buscarPorEmail(email);
			// se encontrar o usuario no banco...
			if (userDTO.getId() != null) {
				// se todas as credenciais foram válidas...
				if (userDTO.getSenha().equals(password)) {
					// adicionar o token no retorno da entidade...
					userDTO.setToken(TokenJWT.processarTokenJWT(email));
					return userDTO;
				} else {// se a senha não estiver correta...
					return null;
				}
			} else {// se NÃO encontrar o usuario no banco...
				return null;
			}
		} else { // se não enviou usuario e senha corretamente...
			return null;
		}
	}

}