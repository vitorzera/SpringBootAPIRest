package br.upf.userdept.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.upf.userdept.dto.CarDTO;

public interface CarRepository extends JpaRepository<CarDTO, Long> {

	// montando querie utilizando JPQL
	@Query("SELECT a, u FROM CarDTO a JOIN a.item u WHERE u.id = :prodId ")
	public List<CarDTO> findByUsuario(@Param("prodId") Long prodId);
}