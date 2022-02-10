package br.ufscar.dc.dsw.service.spec;

import java.util.List;

import br.ufscar.dc.dsw.domain.Cliente;
import br.ufscar.dc.dsw.domain.Loja;
import br.ufscar.dc.dsw.domain.Proposta;
import br.ufscar.dc.dsw.domain.Veiculo;

public interface IPropostaService {
	
	Proposta buscarPorId(Long id);

	List<Proposta> buscarTodos();
	
	List<Proposta> buscarPorLoja(Loja loja);
	
	List<Proposta> buscarPorCliente(Cliente cliente);
	
	List<Proposta> buscarPorVeiculo(Veiculo veiculo);

	void salvar(Proposta proposta);

	void excluir(Long id);
	
}
