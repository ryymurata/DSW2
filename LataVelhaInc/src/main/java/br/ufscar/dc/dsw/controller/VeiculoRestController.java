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
import com.fasterxml.jackson.databind.ObjectMapper;
import br.ufscar.dc.dsw.domain.Veiculo;
import br.ufscar.dc.dsw.service.spec.ILojaService;
import br.ufscar.dc.dsw.service.spec.IVeiculoService;

import br.ufscar.dc.dsw.domain.Loja;

@RestController
public class VeiculoRestController {

	@Autowired
	private IVeiculoService serviceVeiculo;

	@Autowired
	private ILojaService serviceLoja;

	private boolean isJSONValid(String jsonInString) {
		try {
			return new ObjectMapper().readTree(jsonInString) != null;
		} catch (IOException e) {
			return false;
		}
	}

	private void parse(Veiculo veiculo, Loja loja, JSONObject json) {

		Object id = json.get("id");
		if (id != null) {
			if (id instanceof Integer) {
				veiculo.setId(((Integer) id).longValue());
			} else {
				veiculo.setId((Long) id);
			}
		}

		veiculo.setDescricao((String) json.get("descricao"));
		Double preco = (Double) json.get("preco");
		veiculo.setPreco(BigDecimal.valueOf(preco));
		veiculo.setModelo((String) json.get("modelo"));
		veiculo.setChassi((String) json.get("chassi"));
		veiculo.setAno((Integer) json.get("ano"));
		veiculo.setPlaca((String) json.get("placa"));
		veiculo.setQuilometragem((Integer) json.get("quilometragem"));

		veiculo.setLoja(loja);
	}

	@GetMapping(path = "/veiculos/lojas/{id}")
	public ResponseEntity<List<Veiculo>> lista(@PathVariable("id") Long id) {
		Loja loja = serviceLoja.buscarPorId(id);
		List<Veiculo> lista = serviceVeiculo.buscarTodosPorLoja(loja);
		if (lista.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(lista);
	}

	@GetMapping(path = "/veiculos/modelos/{nome}")
	public ResponseEntity<List<Veiculo>> lista(@PathVariable("nome") String modelo) {
		List<Veiculo> lista = serviceVeiculo.buscarTodosPorModelo(modelo);
		if (lista == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(lista);
	}

	@PostMapping(path = "/veiculos/lojas/{id}")
	@ResponseBody
	public ResponseEntity<Veiculo> cria(@PathVariable("id") long id, @RequestBody JSONObject json) {
		try {
			if (isJSONValid(json.toString())) {
				Loja loja = serviceLoja.buscarPorId(id);
				if (loja == null)
					return ResponseEntity.badRequest().body(null);
				Veiculo veiculo = new Veiculo();
				parse(veiculo, loja, json);
				serviceVeiculo.salvar(veiculo);
				return ResponseEntity.ok(veiculo);
			} else {
				return ResponseEntity.badRequest().body(null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
		}
	}
}
