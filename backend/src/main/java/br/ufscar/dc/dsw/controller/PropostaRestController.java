package br.ufscar.dc.dsw.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import com.fasterxml.jackson.databind.ObjectMapper;
import br.ufscar.dc.dsw.domain.Cliente;
import br.ufscar.dc.dsw.domain.Proposta;
import br.ufscar.dc.dsw.domain.Veiculo;
import br.ufscar.dc.dsw.domain.Loja;
import br.ufscar.dc.dsw.service.spec.IClienteService;
import br.ufscar.dc.dsw.service.spec.IPropostaService;
import br.ufscar.dc.dsw.service.spec.IVeiculoService;
import br.ufscar.dc.dsw.service.spec.ILojaService;

@CrossOrigin
@RestController
public class PropostaRestController {

    @Autowired
    private IPropostaService service;

    @Autowired
    private IClienteService serviceCliente;

    @Autowired
    private ILojaService serviceLoja;

    @Autowired
    private IVeiculoService serviceVeiculo;

    private boolean isJSONValid(String jsonInString) {
        try {
            return new ObjectMapper().readTree(jsonInString) != null;
        } catch (IOException e) {
            return false;
        }
    }

    private void parse(Proposta proposta, Loja loja, Cliente cliente, Veiculo veiculo, JSONObject json) {
        Double valor = Double.parseDouble((String) json.get("valor"));
        proposta.setValor(BigDecimal.valueOf(valor));
        proposta.setData((String) json.get("data"));
        proposta.setEstado((String) json.get("estado"));
        proposta.setParcelamento((String) json.get("parcelamento"));
        proposta.setCliente(cliente);
        proposta.setLoja(loja);
        proposta.setVeiculo(veiculo);
    }

    @GetMapping(path = "/propostas/veiculos/{id}")
    public ResponseEntity<List<Proposta>> listaVeiculos(@PathVariable("id") long id) {
        Veiculo veiculo = serviceVeiculo.buscarPorId(id);
        List<Proposta> lista = service.buscarPorVeiculo(veiculo);
        if (lista.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping(path = "/propostas/clientes/{id}")
    public ResponseEntity<List<Proposta>> listaClientes(@PathVariable("id") long id) {
        Cliente cliente = serviceCliente.buscarPorId(id);
        List<Proposta> lista = service.buscarPorCliente(cliente);
        if (lista == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(lista);
    }

    @PostMapping(path = "/propostas/{idLoja}/{idCarro}")
    @ResponseBody
    public ResponseEntity<Proposta> cria(@PathVariable("idLoja") long idLoja, @PathVariable("idCarro") long idCarro, @RequestBody JSONObject json) {
		try {
			if (isJSONValid(json.toString())) {
                Cliente cliente = serviceCliente.buscarPorId(Long.valueOf(2));
				Loja loja = serviceLoja.buscarPorId(idLoja);
                Veiculo veiculo = serviceVeiculo.buscarPorId(idCarro);
				if (loja == null || veiculo == null || cliente == null)
					return ResponseEntity.badRequest().body(null);
                Proposta proposta = new Proposta();
                parse(proposta, loja, cliente, veiculo, json);
                proposta.setId(null);
                service.salvar(proposta);
                return ResponseEntity.ok(proposta);
            } else {
                return ResponseEntity.badRequest().body(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
        }
    }
}