package br.ufscar.dc.dsw.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@SuppressWarnings("serial")
@Entity
@Table(name = "Cliente")
public class Cliente extends Usuario{
	
	@NotBlank
	@Size(min = 14, max = 14, message = "{Size.cliente.CPF}")
	@Column(nullable = false, unique = true, length = 14)
	private String CPF;
	
	@NotBlank
	@Size(min = 3, max = 50)
	@Column(nullable = false, unique = true, length = 50)
	private String nome;

    @NotBlank
	@Size(min = 3, max = 20)
	@Column(nullable = false, unique = true, length = 20)
	private String telefone;

    @NotBlank
	@Size(min = 1, max = 10)
	@Column(nullable = false, unique = false, length = 10)
	private String sexo;
	
	@NotBlank
	@Size(min = 10, max = 10, message = "{Size.cliente.nascimento}")
	@Column(nullable = false, unique = false, length = 10)
	private String nascimento;

	@JsonIgnore
	@OneToMany(mappedBy = "cliente")
	private List<Proposta> propostas;
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCPF() {
		return CPF;
	}

	public void setCPF(String CPF) {
		this.CPF = CPF;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

    public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

    public String getNascimento() {
		return nascimento;
	}

	public void setNascimento(String nascimento) {
		this.nascimento = nascimento;
	}
	
	public List<Proposta> getPropostas() {
		return propostas;
	}
	
	public void setPropostas(List<Proposta> propostas) {
		this.propostas = propostas;
	}

}