package br.upf.userdept.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.upf.userdept.dto.CarDTO;
import br.upf.userdept.repository.CarRepository;

@Service
public class CarService {
	
	@Autowired
	private CarRepository CarRepository;
	
	public List<CarDTO> listarTodos(){
		return CarRepository.findAll();
	}
	
	public List<CarDTO> buscarPorLoja(Long prodId){
		return CarRepository.findByUsuario(prodId);
	}
	
	public CarDTO salvar(CarDTO dto) {
		return CarRepository.save(dto);
	}
	
	public Optional<CarDTO> buscarPorId(Long id) {
		return CarRepository.findById(id);
	}
	
	public void removerPorId(Long id) {
		CarRepository.deleteById(id);
	}

}