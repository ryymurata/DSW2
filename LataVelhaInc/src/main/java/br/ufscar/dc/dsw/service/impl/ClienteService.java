package br.ufscar.dc.dsw.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufscar.dc.dsw.dao.IClienteDAO;
import br.ufscar.dc.dsw.domain.Cliente;
import br.ufscar.dc.dsw.domain.Proposta;
import br.ufscar.dc.dsw.service.spec.IClienteService;


@Service
@Transactional(readOnly = false)
public class ClienteService implements IClienteService{

	@Autowired
	IClienteDAO dao;
	
	public void salvar(Cliente cliente) {
		dao.save(cliente);
	}

	public void excluir(Long id) {
		dao.deleteById(id);
	}

	@Transactional(readOnly = true)
	public Cliente buscarPorId(Long id) {
		return dao.findById(id.longValue());
	}

	@Transactional(readOnly = true)
	public List<Cliente> buscarTodos() {
		return dao.findAll();
	}
	
	@Transactional(readOnly = true)
	public Cliente buscarClientePorUsername(String username) {
		return dao.getClienteByUsername(username);
	}

	@Transactional(readOnly = true)
	public boolean clienteTemPropostasAbertas(Long id) {
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
