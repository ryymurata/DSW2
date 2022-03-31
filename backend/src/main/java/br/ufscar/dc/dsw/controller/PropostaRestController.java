package br.ufscar.dc.dsw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import br.ufscar.dc.dsw.domain.Cliente;
import br.ufscar.dc.dsw.domain.Proposta;
import br.ufscar.dc.dsw.domain.Veiculo;
import br.ufscar.dc.dsw.service.spec.IClienteService;
import br.ufscar.dc.dsw.service.spec.IPropostaService;
import br.ufscar.dc.dsw.service.spec.IVeiculoService;

@CrossOrigin
@RestController
public class PropostaRestController {

    @Autowired
    private IPropostaService service;

    @Autowired
    private IClienteService serviceCliente;

    @Autowired
    private IVeiculoService serviceVeiculo;

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
}