package br.ufscar.dc.dsw.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.ufscar.dc.dsw.domain.Cliente;
import br.ufscar.dc.dsw.domain.Loja;
import br.ufscar.dc.dsw.domain.Proposta;
import br.ufscar.dc.dsw.domain.Veiculo;

@SuppressWarnings("unchecked")
public interface IPropostaDAO extends CrudRepository<Proposta, Long>{

	Proposta findById(long id);
	
	List<Proposta> findAll();
	
	List<Proposta> findAllByLoja(Loja loja);
	
	List<Proposta> findAllByCliente(Cliente cliente);
	
	List<Proposta> findAllByVeiculo(Veiculo veiculo);
	
	Proposta save(Proposta proposta);
	
	void deleteById(Long id);
}