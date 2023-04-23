package br.upf.userdept.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.upf.userdept.dto.ValueDTO;

public interface ValueRepository extends JpaRepository<ValueDTO, Long>{

}

