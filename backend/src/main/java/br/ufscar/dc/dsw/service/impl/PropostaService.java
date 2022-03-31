package br.ufscar.dc.dsw.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufscar.dc.dsw.dao.IPropostaDAO;
import br.ufscar.dc.dsw.domain.Cliente;
import br.ufscar.dc.dsw.domain.Loja;
import br.ufscar.dc.dsw.domain.Proposta;
import br.ufscar.dc.dsw.domain.Veiculo;
import br.ufscar.dc.dsw.service.spec.IPropostaService;


@Service
@Transactional(readOnly = false)
public class PropostaService implements IPropostaService{

	@Autowired
	IPropostaDAO dao;
	
	public void salvar(Proposta proposta) {
		dao.save(proposta);
	}

	public void excluir(Long id) {
		dao.deleteById(id);
	}

	@Transactional(readOnly = true)
	public Proposta buscarPorId(Long id) {
		return dao.findById(id.longValue());
	}

	@Transactional(readOnly = true)
	public List<Proposta> buscarTodos() {
		return dao.findAll();
	}
	
	@Transactional(readOnly = true)
	public List<Proposta> buscarPorLoja(Loja loja){
		return dao.findAllByLoja(loja);
	}
	
	@Transactional(readOnly = true)
	public List<Proposta> buscarPorCliente(Cliente cliente){
		return dao.findAllByCliente(cliente);
	}
	
	@Transactional(readOnly = true)
	public List<Proposta> buscarPorVeiculo(Veiculo veiculo){
		return dao.findAllByVeiculo(veiculo);
	}

	

}
