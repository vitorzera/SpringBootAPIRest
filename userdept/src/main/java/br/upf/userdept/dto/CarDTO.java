package br.upf.userdept.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data 
@AllArgsConstructor 
@NoArgsConstructor 
@EqualsAndHashCode 
@Entity
@Table(name = "tb_car")
public class CarDTO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "marc_name", nullable = false)
	private String name;

    @Column(name="marc_model" , nullable = false)
	private  String modelo;

	@Column(name = "marc_loja", nullable = false)
	private  Long  Local;
	
	@ManyToOne //definindo a relação muitos-para-um
	@JoinColumn(name = "prods_id")
	private ValueDTO item;

}