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

import br.upf.userdept.dto.CarDTO;
import br.upf.userdept.service.CarService;
import br.upf.userdept.utils.TokenJWT;

@RestController
@RequestMapping(value = "/userdept/marca")
public class CarController {
	
	@Autowired // mecanismo de injeção de dependência
	private CarService CarService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping(value = "/listarTodos")
	@ResponseStatus(HttpStatus.OK)
	public List<CarDTO> listarTodos(
			@RequestHeader(value = "token") String token) {
		TokenJWT.validarToken(token);
		return CarService.listarTodos();
	}
	
	@GetMapping(value = "/buscarporProdutoNaloja")
	@ResponseStatus(HttpStatus.OK)
	public List<CarDTO> buscarPorLoja(
			@RequestHeader(value = "prodId") Long prodId,
			@RequestHeader(value = "token") String token) {
		TokenJWT.validarToken(token);
		return CarService.buscarPorLoja(prodId);
	}
	
	@PostMapping(value = "/inserir")
	@ResponseStatus(HttpStatus.CREATED)
	public CarDTO inserir(@RequestBody CarDTO dto,
							@RequestHeader(value = "token") String token) {
		TokenJWT.validarToken(token);
		return CarService.salvar(dto);
	}
	
	@GetMapping(value = "/buscarPorId")
	@ResponseStatus(HttpStatus.OK)
	public CarDTO buscarPorId(
			@RequestHeader(value = "id") Long id,
			@RequestHeader(value = "token") String token) {
		TokenJWT.validarToken(token);
		return CarService.buscarPorId(id)
				// caso o cliente não foi encontrado...
				.orElseThrow(() -> new ResponseStatusException(
						HttpStatus.BAD_REQUEST, "Usuario não encontrado."));
	}
	
	@DeleteMapping(value = "/delete")
	@ResponseStatus(HttpStatus.NO_CONTENT) // sem conteúdo
	public void removerUsuario(
			@RequestHeader(value = "id") Long id, 
			@RequestHeader(value = "token") String token) {
		TokenJWT.validarToken(token);
		CarService.buscarPorId(id) // antes de deletar, busca na base o cliente...
				.map(usuario -> { // caso encontre o usuario, remova o mesmo
					CarService.removerPorId(usuario.getId());
					return Void.TYPE;
					// caso não encontre, retorna o status
				}).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuario não encontrado."));
	}
	
	@PutMapping(value = "/atualizar")
	@ResponseStatus(HttpStatus.NO_CONTENT) 
	public void atualizar(@RequestBody CarDTO CarDTO,
			@RequestHeader(value = "token") String token) {
		TokenJWT.validarToken(token);
		CarService.buscarPorId(CarDTO.getId()).map(addressBase -> {
			modelMapper.map(CarDTO, addressBase);
			CarService.salvar(addressBase);
			return Void.TYPE;
		}).orElseThrow(() -> new ResponseStatusException(
				HttpStatus.BAD_REQUEST, "Usuario não encontrado."));
	}

}