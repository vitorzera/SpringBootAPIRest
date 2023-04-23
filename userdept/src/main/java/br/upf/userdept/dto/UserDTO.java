package br.upf.userdept.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data //Lombok - geters, seters, contrutores obrigatórios, toString, equals. 
@AllArgsConstructor //Lombok - monta o contrutor com todos os atributos
@NoArgsConstructor //Lombok - monta o contrutor sem argumentos
@EqualsAndHashCode //Lombok - monta os métodos Equals e HashCode
@Entity
@Table(name = "tb_user")
public class UserDTO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "usr_nome", nullable = false)
	private String nome;
	
	//esse campo é único no banco...
	@Column(name = "usr_email", nullable = false, unique=true)
	private String email;
	
	@Column(name = "usr_senha", nullable = false)
	private String senha;
	
	@Column(name = "usr_nascimento")
	private Date dataNascimento;
	
	@Transient //atributo não será persistido
	private String token;
	
	@ManyToOne //definindo a relação muitos-para-um
	@JoinColumn(name = "prods_id")
	private ValueDTO carValue;
}
