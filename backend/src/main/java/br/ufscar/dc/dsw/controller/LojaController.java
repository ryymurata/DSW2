package br.ufscar.dc.dsw.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.ufscar.dc.dsw.dao.ILojaDAO;
import br.ufscar.dc.dsw.domain.Loja;
import br.ufscar.dc.dsw.service.spec.ILojaService;


@Controller
@RequestMapping("/loja/*")
public class LojaController {

	@Autowired
	private ILojaDAO dao;

    @Autowired
	private ILojaService service;

	public boolean isValidCNPJ(String CNPJ) {
		if (dao != null) {
			Loja cliente = dao.findByCNPJ(CNPJ);
			return cliente == null;
		} else {
			// Durante a execução da classe LataVelhaIncApplication
			// não há injeção de dependência
			return true;
		}
	}

	public boolean isValidEmail(String email) {
		if (dao != null) {
			Loja cliente = dao.getLojaByUsername(email);
			return cliente == null;
		} else {
			// Durante a execução da classe LataVelhaIncApplication
			// não há injeção de dependência
			return true;
		}
	}	

	@GetMapping("/listar")
	public String listar(ModelMap model) {
		model.addAttribute("listaLojas", service.buscarTodos());
		return "admin/listaLojas";
	}

	@GetMapping("/cadastrar")
	public String cadastro(Loja loja) {
		loja.setRole("ROLE_LOJA");
		return "admin/cadastroLoja";
	}

	@GetMapping("/editar/{id}")
	public String preEdicao(@PathVariable("id") Long id, ModelMap model) {
        model.addAttribute("loja", service.buscarPorId(id));
		return "admin/cadastroLoja";
	}

	@PostMapping("/editar")
	public String editar(@Valid Loja loja, BindingResult result, RedirectAttributes attr, BCryptPasswordEncoder encoder) {
		
		if (result.hasErrors()) {
			return "admin/cadastroLoja";
		}
		service.salvar(loja);
		attr.addFlashAttribute("success", "store.edit.success");
		return "redirect:/loja/listar";
	}

	@GetMapping("/remover/{id}")
	public String remover(@PathVariable("id") Long id, ModelMap model, RedirectAttributes attr) {
		
		if (service.lojaTemPropostasAbertas(id)) {
			attr.addFlashAttribute("fail", "store.delete.fail");
		}
		else {
			service.excluir(id);
	        attr.addFlashAttribute("success", "store.delete.success");
		}
		return "redirect:/loja/listar";
	}

	@PostMapping("/salvar")
	public String salvar(@Valid Loja loja, BindingResult result, RedirectAttributes attr, BCryptPasswordEncoder encoder) {

		if (result.hasErrors()) {
			return "admin/cadastroLoja";
		}
		else if (!isValidCNPJ(loja.getCNPJ()) || !isValidEmail(loja.getUsername())) {
			attr.addFlashAttribute("fail", "store.create.fail");
			return "redirect:/loja/listar";
		}
		
		loja.setPassword(encoder.encode(loja.getPassword()));
		service.salvar(loja);
		attr.addFlashAttribute("success", "store.create.success");
		return "redirect:/loja/listar";
	}
}