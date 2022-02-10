package br.ufscar.dc.dsw.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ufscar.dc.dsw.domain.Cliente;
import br.ufscar.dc.dsw.domain.Usuario;
import br.ufscar.dc.dsw.domain.Veiculo;
import br.ufscar.dc.dsw.domain.Loja;
import br.ufscar.dc.dsw.domain.Proposta;
import br.ufscar.dc.dsw.security.UsuarioDetails;
import br.ufscar.dc.dsw.service.impl.EmailService;
import br.ufscar.dc.dsw.service.spec.IClienteService;
import br.ufscar.dc.dsw.service.spec.ILojaService;
import br.ufscar.dc.dsw.service.spec.IVeiculoService;
import br.ufscar.dc.dsw.service.spec.IPropostaService;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.mail.internet.InternetAddress;
import javax.validation.Valid;

import javax.servlet.ServletContext;

@Controller
@RequestMapping("/proposta/*")
public class PropostaController {

	@Autowired
	private ILojaService serviceLoja;

	@Autowired
	private IClienteService serviceCliente;

	@Autowired
	private IPropostaService serviceProposta;

	@Autowired
	private IVeiculoService serviceVeiculo;

	@Autowired
	private EmailService service;

	@Autowired
	ServletContext context;

	private Usuario getUsuario() {
		UsuarioDetails usuarioDetails = (UsuarioDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		return usuarioDetails.getUsuario();
	}

	public List<String> fileList(Long id) {
		List<String> fileList = new ArrayList<String>();

		String uploadPath = context.getRealPath("") + File.separator + "images/" + id;
		File uploadDir = new File(uploadPath);

		File[] files = uploadDir.listFiles();

		if (files != null) {
			for (final File file : files) {
				fileList.add(file.getName());
			}
		}
		System.out.println(fileList);
		return fileList;
	}

	@GetMapping("/listarPropostasLoja")
	public String listarPropostasLoja(ModelMap model) {
		Loja loja = serviceLoja.buscarPorId(this.getUsuario().getId());
		model.addAttribute("propostas", serviceProposta.buscarPorLoja(loja));
		model.addAttribute("loja", loja);
		return "listaPropostas";
	}

	@GetMapping("/listarPropostasCliente")
	public String listarPropostasCliente(ModelMap model) {
		Cliente cliente = serviceCliente.buscarPorId(this.getUsuario().getId());

		model.addAttribute("propostas", serviceProposta.buscarPorCliente(cliente));
		model.addAttribute("cliente", cliente);
		return "listaPropostas";
	}

	@GetMapping("/comprar/{id}")
	public String comprar(@PathVariable("id") Long id, ModelMap model, Proposta proposta) {
		Veiculo veiculo = serviceVeiculo.buscarPorId(id);
		Loja loja = veiculo.getLoja();
		Cliente cliente = serviceCliente.buscarPorId(this.getUsuario().getId());
		String data = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());

		proposta.setCliente(cliente);
		proposta.setLoja(loja);
		proposta.setVeiculo(veiculo);
		proposta.setData(data);
		proposta.setEstado("ABERTO");

		List<Proposta> lista_propostas = serviceProposta.buscarPorCliente(cliente);
		List<Proposta> lista_todas_propostas = serviceProposta.buscarPorVeiculo(veiculo);
		for (int i = 0; i < lista_propostas.size(); i++) {
			if (lista_propostas.get(i).getVeiculo().getId() == id
					&& lista_propostas.get(i).getEstado().equals("ABERTO")) {
				model.addAttribute("proposta_aberta", lista_propostas.get(i));
			}
			if (lista_propostas.get(i).getVeiculo().getId() == id
					&& lista_propostas.get(i).getEstado().equals("ACEITO")) {
				model.addAttribute("proposta_aceita", lista_propostas.get(i));
			}
		}
		for (int i = 0; i < lista_todas_propostas.size(); i++) {
			if (lista_todas_propostas.get(i).getEstado().equals("ACEITO") 
					&& lista_todas_propostas.get(i).getCliente().getId() != cliente.getId()){
				model.addAttribute("ja_vendido", lista_todas_propostas.get(i));
			}
		}

		model.addAttribute("cliente", cliente);
		model.addAttribute("veiculo", veiculo);
		model.addAttribute("files", fileList(id));
		return "cliente/comprar";
	}

	@PostMapping("/salvar")
	public String salvar(@Valid Proposta proposta, BindingResult result, RedirectAttributes attr,
			ModelMap model) {
		if (result.hasErrors()) {
			Long id = proposta.getVeiculo().getId();
			Veiculo veiculo = serviceVeiculo.buscarPorId(id);
			Cliente cliente = serviceCliente.buscarPorId(this.getUsuario().getId());
			List<Proposta> lista_propostas = serviceProposta.buscarPorCliente(cliente);
			for (int i = 0; i < lista_propostas.size(); i++) {
				if (lista_propostas.get(i).getVeiculo().getId() == id
						&& lista_propostas.get(i).getEstado().equals("ABERTO")) {
					model.addAttribute("proposta_aberta", lista_propostas.get(i));
				}
			}

			model.addAttribute("cliente", cliente);
			model.addAttribute("veiculo", veiculo);
			model.addAttribute("files", fileList(id));
			return "cliente/comprar";
		}

		serviceProposta.salvar(proposta);
		attr.addFlashAttribute("sucess", "Proposta enviada com sucesso.");
		return "redirect:/proposta/listarPropostasCliente";

	}

	@GetMapping("/cancelar/{id}")
	public String cancelar(@PathVariable("id") Long id, RedirectAttributes attr, ModelMap model) {
		Proposta proposta = serviceProposta.buscarPorId(id);
		if (proposta.getCliente().equals(this.getUsuario()) && proposta.getEstado().equals("ABERTO")) {
			serviceProposta.excluir(id);
			attr.addFlashAttribute("sucess", "proposta.delete.sucess");
			return "redirect:/proposta/listarPropostasCliente";
		}

		Cliente cliente = serviceCliente.buscarPorId(this.getUsuario().getId());
		model.addAttribute("cliente", cliente);
		return "cliente/comprar";
	}

	@GetMapping("/aceitar/{id_proposta}")
	public String aceitar(@PathVariable("id_proposta") Long id_proposta,
			@RequestParam(value = "mensagem", required = false) String mensagem, RedirectAttributes attr,
			ModelMap model) {
		Proposta proposta = serviceProposta.buscarPorId(id_proposta);
		if (mensagem == null) {
			mensagem = "";
		}
		if (proposta.getLoja().equals(this.getUsuario()) && proposta.getEstado().equals("ABERTO")) {

			InternetAddress from, to;
			try {
				from = new InternetAddress(proposta.getLoja().getUsername(), proposta.getLoja().getNome());
				to = new InternetAddress(proposta.getCliente().getUsername(), proposta.getCliente().getNome());

				String subject = "Sua proposta para compra de " + proposta.getVeiculo().getModelo() + " foi ACEITA!";
				String body = mensagem + "\n\nLink para a videochamada: https://meet.google.com/ooe-xsvv-orm\nAtenciosamente\n" + proposta.getLoja().getNome();
				System.out.println(from);
				System.out.println(to);
				System.out.println(subject);
				System.out.println(body);

				service.send(from, to, subject, body);

			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			proposta.setEstado("ACEITO");
			List<Proposta> propostas_veiculo = serviceProposta.buscarPorVeiculo(proposta.getVeiculo());
			for (int i = 0; i < propostas_veiculo.size(); i++) {
				if (propostas_veiculo.get(i).getEstado().equals("ABERTO")) {
					propostas_veiculo.get(i).setEstado("RECUSADO");
				}
			}
			attr.addFlashAttribute("success", "proposta.acceptance.success");
			serviceProposta.salvar(proposta);
		}
		return "redirect:/proposta/listarPropostasLoja";
	}

	@GetMapping("/negar/{id_proposta}")
	public String negar(@PathVariable("id_proposta") Long id_proposta,
			@RequestParam(value = "mensagem", required = false) String mensagem, RedirectAttributes attr,
			ModelMap model) {
		Proposta proposta = serviceProposta.buscarPorId(id_proposta);
		if (mensagem == null) {
			mensagem = "";
		}
		if (proposta.getLoja().getId().equals(this.getUsuario().getId())) {

			InternetAddress from, to;
			try {
				from = new InternetAddress(proposta.getLoja().getUsername(), proposta.getLoja().getNome());
				to = new InternetAddress(proposta.getCliente().getUsername(), proposta.getCliente().getNome());

				String subject = "Sua proposta para compra de " + proposta.getVeiculo().getModelo() + " foi NEGADA!";
				String body = mensagem + "\n\nAtenciosamente\n" + proposta.getLoja().getNome();

				service.send(from, to, subject, body);

			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			proposta.setEstado("RECUSADO");
			attr.addFlashAttribute("success", "proposta.acceptance.success");
			serviceProposta.salvar(proposta);
		}
		return "redirect:/proposta/listarPropostasLoja";
	}
}
