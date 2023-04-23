package br.upf.userdept.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.upf.userdept.dto.ValueDTO;
import br.upf.userdept.repository.ValueRepository;

@Service
public class ValueService {

	@Autowired
	private ValueRepository ValueRepository;
	
	public ValueDTO salvar(ValueDTO dto) {
		return ValueRepository.save(dto);
	}
	
	public List<ValueDTO> listarTodos(){
		return ValueRepository.findAll();
	}
	
	public Optional<ValueDTO> buscarPorId(Long id) {
		return ValueRepository.findById(id);
	}
	
	public void removerPorId(Long id) {
		ValueRepository.deleteById(id);
	}
	
}