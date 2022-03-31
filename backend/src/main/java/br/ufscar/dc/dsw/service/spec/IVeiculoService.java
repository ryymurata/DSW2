package br.ufscar.dc.dsw.service.spec;

import java.util.List;

import br.ufscar.dc.dsw.domain.Loja;
import br.ufscar.dc.dsw.domain.Veiculo;

public interface IVeiculoService {
	
	Veiculo buscarPorId(Long id);

	List<Veiculo> buscarTodosPorModelo(String modelo);

	List<Veiculo> buscarTodos();

	List<Veiculo> buscarTodosPorLoja(Loja loja);

	void salvar(Veiculo veiculo);

	void excluir(Long id);
	
	boolean veiculoTemPropostasAbertas(Long id);
}