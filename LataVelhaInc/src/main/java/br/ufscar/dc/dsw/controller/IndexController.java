package br.ufscar.dc.dsw.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import br.ufscar.dc.dsw.domain.Cliente;
import br.ufscar.dc.dsw.domain.Loja;
import br.ufscar.dc.dsw.service.spec.IClienteService;
import br.ufscar.dc.dsw.service.spec.ILojaService;
import br.ufscar.dc.dsw.service.spec.IVeiculoService;

@Controller
public class IndexController {

    @Autowired
	private IVeiculoService serviceVeiculo;

    @Autowired
	private IClienteService serviceCliente;

    @Autowired
	private ILojaService serviceLoja;

    @GetMapping("/")
    public String index(ModelMap model, HttpServletRequest request) {
        String username = request.getRemoteUser();
        Cliente cliente = serviceCliente.buscarClientePorUsername(username);
        Loja loja = serviceLoja.buscarLojaPorUsername(username);

        model.addAttribute("cliente", cliente);
        model.addAttribute("loja", loja);

        if(loja != null){
            model.addAttribute("catalogo", serviceVeiculo.buscarTodosPorLoja(loja));
        }
        else {
            model.addAttribute("catalogo", serviceVeiculo.buscarTodos());
        }
        return "index";
    }
    
    @GetMapping("/login")
    public String login() {
    	return "login";
    }
}