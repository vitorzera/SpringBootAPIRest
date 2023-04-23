package br.upf.userdept.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;	

//notações referente ao lombok
@Data //geters, seters, contrutores obrigatórios, toString, equals. 
@AllArgsConstructor //monta o contrutor com todos os atributos
@NoArgsConstructor //monta o contrutor sem argumentos
@EqualsAndHashCode

@Entity //definindo que é uma classe tipo entidade
@Table(name = "tb_prods") //mapeando a tabela
public class ValueDTO {

	@Id //definindo a chave primária
	//definindo a geração automática do valor da chave primária
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "prods_name", nullable = false)
	private String name;

	@Column(name = "prods_value", nullable = false)
	private String value;
}
