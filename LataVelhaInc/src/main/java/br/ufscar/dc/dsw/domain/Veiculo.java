package br.ufscar.dc.dsw.domain;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@SuppressWarnings("serial")
@Entity
@Table(name = "Veiculo")
public class Veiculo extends AbstractEntity<Long>{

	@NotBlank
	@Size(max = 20)
	@Column(nullable = false, unique = true, length = 20)
	private String placa;
	
	@NotBlank
	@Size(max = 20)
	@Column(nullable = false, length = 20)
    private String modelo;
	
	@NotBlank
	@Size(max = 22)
	@Column(nullable = false, unique = true, length = 22)
    private String chassi;
	
	@NotNull(message = "{javax.validation.constraints.NotNull.message}")
	@Column(nullable = false, length = 5)
    private Integer ano;
	
	@NotNull(message = "{javax.validation.constraints.NotNull.message}")
	@Column(nullable = false, length = 10)
    private Integer quilometragem;
	
	@Size(max = 300)
	@Column(nullable = false, length = 300)
    private String descricao;
	
	@NotNull(message = "{javax.validation.constraints.NotNull.message}")
	@Column(nullable = false, columnDefinition = "DECIMAL(10,2) DEFAULT 0.0")
    private BigDecimal preco;
    
    @JsonBackReference
    @NotNull(message = "{javax.validation.constraints.NotNull.message}")
	@ManyToOne
	@JoinColumn(name = "id_loja")
    private Loja loja;
    
    @JsonIgnore
    @OneToMany(mappedBy = "veiculo")
	private List<Proposta> propostas;
    
    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getChassi() {
        return chassi;
    }

    public void setChassi(String chassi) {
        this.chassi = chassi;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public Integer getQuilometragem() {
        return quilometragem;
    }

    public void setQuilometragem(Integer quilometragem) {
        this.quilometragem = quilometragem;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Loja getLoja() {
        return loja;
    }

    public void setLoja(Loja loja) {
        this.loja = loja;
    }

    public List<Proposta> getPropostas() {
		return propostas;
	}
    
    public void setPropostas(List<Proposta> propostas) {
		this.propostas = propostas;
	}
    
    @Override
    public String toString() {
    	return modelo + ", " + chassi + "(" + quilometragem + ")"; 
    }
}
