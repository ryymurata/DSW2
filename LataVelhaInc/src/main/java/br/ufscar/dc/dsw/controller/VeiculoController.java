package br.ufscar.dc.dsw.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ufscar.dc.dsw.domain.Loja;
import br.ufscar.dc.dsw.domain.Usuario;
import br.ufscar.dc.dsw.domain.Veiculo;
import br.ufscar.dc.dsw.security.UsuarioDetails;
import br.ufscar.dc.dsw.service.spec.ILojaService;
import br.ufscar.dc.dsw.service.spec.IVeiculoService;

import javax.servlet.ServletContext;

@Controller
@RequestMapping("/veiculo/*")
public class VeiculoController {

	@Autowired
	private IVeiculoService service;

	@Autowired
	private ILojaService serviceLoja;

	@Autowired
	ServletContext context;

	private Usuario getUsuario() {
		UsuarioDetails usuarioDetails = (UsuarioDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
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

	@GetMapping("/adicionar")
	public String cadastro(Veiculo veiculo, ModelMap model) {
		Loja loja = serviceLoja.buscarPorId(this.getUsuario().getId());
		model.addAttribute("loja", loja);
		veiculo.setLoja(loja);
		return "loja/cadastroVeiculo";
	}

	@GetMapping("/editar/{id}")
	public String preEdicao(@PathVariable("id") Long id, ModelMap model) {

		Loja loja = serviceLoja.buscarPorId(this.getUsuario().getId());
		model.addAttribute("loja", loja);
		model.addAttribute("veiculo", service.buscarPorId(id));
		model.addAttribute("files", fileList(id));

		return "loja/cadastroVeiculo";
	}

	@PostMapping("/editar")
	public String editar(@Valid Veiculo veiculo, BindingResult result, RedirectAttributes attr, ModelMap model) {
		
		if (result.hasErrors()) {
			Loja loja = serviceLoja.buscarPorId(this.getUsuario().getId());
			model.addAttribute("loja", loja);
			return "loja/cadastroVeiculo";
		}
		service.salvar(veiculo);
		attr.addFlashAttribute("success", "vehicle.edit.success");
		return "redirect:/";
	}

	@GetMapping("/remover/{id}")
	public String remover(ModelMap model, @PathVariable("id") Long id, RedirectAttributes attr) {
        
		if (service.veiculoTemPropostasAbertas(id)) {
			attr.addFlashAttribute("fail", "vehicle.delete.fail");
		}
		else {
			service.excluir(id);
	        attr.addFlashAttribute("success", "vehicle.delete.success");
		}
		
        return "redirect:/";
	}

	@PostMapping("/salvar")
	public String salvar(@Valid Veiculo veiculo, BindingResult result, RedirectAttributes attr, ModelMap model) {

		if (result.hasErrors()) {
			Loja loja = serviceLoja.buscarPorId(this.getUsuario().getId());
			model.addAttribute("loja", loja);
			return "loja/cadastroVeiculo";
		}

		service.salvar(veiculo);
		attr.addFlashAttribute("success", "vehicle.create.success");
		return "redirect:/";
	}

	@PostMapping("/uploadFile/{id}")
	public String addPhoto(@RequestParam("file") MultipartFile file, @PathVariable("id") Long id,
			RedirectAttributes attr, ModelMap model) throws IOException {
		
		String fileName = file.getOriginalFilename();

		File uploadDir = new File(context.getRealPath("") + File.separator + "images/");
		if (!uploadDir.exists()) {
			uploadDir.mkdir();
		}
		
		String uploadPathCar = context.getRealPath("") + File.separator + "images/" + id;
		File uploadDirCar = new File(uploadPathCar);
		
		if (!uploadDirCar.exists()) {
			uploadDirCar.mkdir();
			fileName = "1.jpg";
		}
		if (uploadDirCar.listFiles().length < 10){
			file.transferTo(new File(uploadDirCar, fileName));
			attr.addFlashAttribute("sucess", "File " + fileName + " has uploaded successfully!");
		}
		else {
			attr.addFlashAttribute("fail", "Limite de 10 arquivos atingido!");
		}
		return "redirect:/veiculo/editar/" + id;
	}
}