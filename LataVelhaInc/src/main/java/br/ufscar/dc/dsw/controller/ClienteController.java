package br.ufscar.dc.dsw.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ufscar.dc.dsw.dao.IClienteDAO;
import br.ufscar.dc.dsw.domain.Cliente;
import br.ufscar.dc.dsw.service.spec.IClienteService;


@Controller
@RequestMapping("/cliente/*")
public class ClienteController {

	@Autowired
	private IClienteDAO dao;

	@Autowired
	private IClienteService service;
	
	public boolean isValidCPF(String CPF) {
		if (dao != null) {
			Cliente cliente = dao.findByCPF(CPF);
			return cliente == null;
		} else {
			// Durante a execução da classe LataVelhaIncApplication
			// não há injeção de dependência
			return true;
		}
	}

	public boolean isValidEmail(String email) {
		if (dao != null) {
			Cliente cliente = dao.getClienteByUsername(email);
			return cliente == null;
		} else {
			// Durante a execução da classe LataVelhaIncApplication
			// não há injeção de dependência
			return true;
		}
	}	

	@GetMapping("/listar")
	public String listar(ModelMap model) {	
        model.addAttribute("listaClientes", service.buscarTodos());
		return "admin/listaClientes";
	}

	@GetMapping("/cadastrar")
	public String cadastro(Cliente cliente) {
		cliente.setRole("ROLE_USER");
		return "admin/cadastroCliente";
	}

	@GetMapping("/editar/{id}")
	public String preEdicao(@PathVariable("id") Long id, ModelMap model) {
        model.addAttribute("cliente", service.buscarPorId(id));
		return "admin/cadastroCliente";
	}

	@PostMapping("/editar")
	public String editar(@Valid Cliente cliente, BindingResult result, RedirectAttributes attr) {
		
		if (result.hasErrors()) {
			return "admin/cadastroCliente";
		}
		service.salvar(cliente);
		attr.addFlashAttribute("success", "customer.edit.success");
		return "redirect:/cliente/listar";
	}

	@GetMapping("/remover/{id}")
	public String remover(@PathVariable("id") Long id, ModelMap model, RedirectAttributes attr) {
        
		if (service.clienteTemPropostasAbertas(id)) {
			attr.addFlashAttribute("fail", "customer.delete.fail");
		}
		else {
			service.excluir(id);
	        attr.addFlashAttribute("success", "customer.delete.success");
		}
		
        return "redirect:/cliente/listar";
	}

	@PostMapping("/salvar")
	public String salvar(@Valid Cliente cliente, BindingResult result, RedirectAttributes attr, BCryptPasswordEncoder encoder) {

		if (result.hasErrors()){
			return "admin/cadastroCliente";
		}
		else if (!isValidCPF(cliente.getCPF()) || !isValidEmail(cliente.getUsername())) {
			attr.addFlashAttribute("fail", "customer.create.fail");
			return "redirect:/cliente/listar";
		}

		cliente.setPassword(encoder.encode(cliente.getPassword()));
		service.salvar(cliente);
		attr.addFlashAttribute("success", "customer.create.success");
		return "redirect:/cliente/listar";
	}
}