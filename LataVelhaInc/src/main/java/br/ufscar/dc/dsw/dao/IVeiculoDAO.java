package br.ufscar.dc.dsw.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.ufscar.dc.dsw.domain.Loja;
import br.ufscar.dc.dsw.domain.Veiculo;

@SuppressWarnings("unchecked")
public interface IVeiculoDAO extends CrudRepository<Veiculo, Long>{

	Veiculo findById(long id);
	
	List<Veiculo> findAllByModelo (String modelo);

	List<Veiculo> findAllByLoja (Loja loja);

	List<Veiculo> findAll();
	
	Veiculo save(Veiculo veiculo);

	void deleteById(Long id);
}