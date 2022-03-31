package br.ufscar.dc.dsw.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufscar.dc.dsw.dao.IVeiculoDAO;
import br.ufscar.dc.dsw.domain.Loja;
import br.ufscar.dc.dsw.domain.Proposta;
import br.ufscar.dc.dsw.domain.Veiculo;
import br.ufscar.dc.dsw.service.spec.IVeiculoService;


@Service
@Transactional(readOnly = false)
public class VeiculoService implements IVeiculoService{

	@Autowired
	IVeiculoDAO dao;
	
	public void salvar(Veiculo veiculo) {
		dao.save(veiculo);
	}

	public void excluir(Long id) {
		dao.deleteById(id);
	}

	@Transactional(readOnly = true)
	public Veiculo buscarPorId(Long id) {
		return dao.findById(id.longValue());
	}

	@Transactional(readOnly = true)
	public List<Veiculo> buscarTodosPorModelo(String modelo) {
		return dao.findAllByModelo(modelo);
	}

	@Transactional(readOnly = true)
	public List<Veiculo> buscarTodos() {
		return dao.findAll();
	}

	@Transactional(readOnly = true)
	public List<Veiculo> buscarTodosPorLoja(Loja loja) {
		return dao.findAllByLoja(loja);
	}
	
	@Transactional(readOnly = true)
	public boolean veiculoTemPropostasAbertas(Long id) {
		List<Proposta> listaPropostas = dao.findById(id.longValue()).getPropostas();
		
		int i = 0;
		boolean temPropostasAbertas = false;
		
		while (i < listaPropostas.size() && !temPropostasAbertas) {
			if (listaPropostas.get(i).getEstado().equals("ABERTO")) {
				temPropostasAbertas = true;
			}

			i++;
		}
		return temPropostasAbertas; 
	}
}