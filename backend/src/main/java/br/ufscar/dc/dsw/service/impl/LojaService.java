package br.ufscar.dc.dsw.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufscar.dc.dsw.dao.ILojaDAO;
import br.ufscar.dc.dsw.domain.Loja;
import br.ufscar.dc.dsw.domain.Proposta;
import br.ufscar.dc.dsw.service.spec.ILojaService;
import br.ufscar.dc.dsw.domain.Veiculo;
import br.ufscar.dc.dsw.service.spec.IVeiculoService;

@Service
@Transactional(readOnly = false)
public class LojaService implements ILojaService{

	@Autowired
	ILojaDAO dao;

	@Autowired
	private IVeiculoService serviceVeiculo;
	
	public void salvar(Loja loja) {
		dao.save(loja);
	}

	public void excluir(Long id) {
		Loja loja = buscarPorId(id.longValue());
		List<Veiculo> veiculos = serviceVeiculo.buscarTodosPorLoja(loja);
		for (int i = 0; i < veiculos.size(); i++)
			serviceVeiculo.excluir(veiculos.get(i).getId());
		dao.deleteById(id);
	}

	@Transactional(readOnly = true)
	public Loja buscarPorId(Long id) {
		return dao.findById(id.longValue());
	}

	@Transactional(readOnly = true)
	public List<Loja> buscarTodos() {
		return dao.findAll();
	}

	@Transactional(readOnly = true)
	public Loja buscarLojaPorUsername(String username) {
		return dao.getLojaByUsername(username);
	}
	

	@Transactional(readOnly = true)
	public boolean lojaTemPropostasAbertas(Long id) {
		
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
