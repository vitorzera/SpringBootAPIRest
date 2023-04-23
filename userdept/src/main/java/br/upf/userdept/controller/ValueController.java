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

import br.upf.userdept.dto.ValueDTO;
import br.upf.userdept.service.ValueService;
import br.upf.userdept.utils.TokenJWT;

@RestController
@RequestMapping(value = "/userdept/produts")
public class ValueController {
	@Autowired
	private ValueService ValueService;

	@Autowired
	private ModelMapper modelMapper;

	@PostMapping(value = "/inserir")
	@ResponseStatus(HttpStatus.CREATED)
	public ValueDTO inserir(@RequestBody ValueDTO ValueDTO,
							@RequestHeader(value = "token") String token) {
		TokenJWT.validarToken(token);
		return ValueService.salvar(ValueDTO);
	}

	@GetMapping(value = "/listarTodos")
	@ResponseStatus(HttpStatus.OK)
	public List<ValueDTO> listarTodos(@RequestHeader(value = "token") String token) {
		TokenJWT.validarToken(token);
		return ValueService.listarTodos();
	}

	@GetMapping(value = "/buscarPorId")
	@ResponseStatus(HttpStatus.OK)
	public ValueDTO buscarPorId(@RequestHeader(value = "id") Long id,
								@RequestHeader(value = "token") String token) {
		TokenJWT.validarToken(token);
		return ValueService.buscarPorId(id)
				// caso o cliente não foi encontrado...
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, 
						"produto não encontrado."));
	}

	@DeleteMapping(value = "/delete")
	@ResponseStatus(HttpStatus.NO_CONTENT) // sem conteúdo
	public void removerUsuario(@RequestHeader(value = "id") Long id, 
			@RequestHeader(value = "token") String token) {
		TokenJWT.validarToken(token);
		ValueService.buscarPorId(id) // antes de deletar, busca na base o cliente...
				.map(department -> { // definindo a variável no map
					ValueService.removerPorId(department.getId()); // caso encontre o usuario, remova o mesmo
					return Void.TYPE;
					// caso não encontre, retorna o status
				}).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, 
						"Usuario não encontrado."));
	}

	/**
	 * Para implementar o atualizar, é necessário incluir o 
	 * método bean modelMapper() na classe UserdeptApplication.java 
	 * @param ValueDTO
	 * @param id
	 */
	@PutMapping(value = "/atualizar")
	@ResponseStatus(HttpStatus.NO_CONTENT) // sem conteúdo
	public void atualizar(@RequestBody ValueDTO ValueDTO,
			@RequestHeader(value = "token") String token) {
		TokenJWT.validarToken(token);
		ValueService.buscarPorId(ValueDTO.getId()).map(departmentBase -> {// definindo a variável no map
			//recurso do modelMap que ferifica o que esta no parâmetro para
			//atualizar na base.
			//esse recurso necessita de incluir a dependência modelMap no pom.xml
			modelMapper.map(ValueDTO, departmentBase);
			ValueService.salvar(departmentBase); // salvar os itens alterados
			return Void.TYPE;
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, 
				"Usuario não encontrado."));
	}
}