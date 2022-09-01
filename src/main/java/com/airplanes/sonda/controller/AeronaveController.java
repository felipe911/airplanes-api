package com.airplanes.sonda.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.airplanes.sonda.model.Aeronave;
import com.airplanes.sonda.service.AeronaveService;


@RestController
@RequestMapping("/aeronaves")
public class AeronaveController {
	
	@Autowired
	private AeronaveService aeronaveService;
	
	@GetMapping(produces = "application/json")
	public List<Aeronave> listar() {
		return aeronaveService.listarAeronaves();
	}
	
	@PostMapping("/find")
	public List<Aeronave> buscaPorParametro(@RequestBody String parametro) throws Exception {
		return aeronaveService.listarAeronavesParametro(parametro);
	}
	
	@GetMapping("/{id}")
	public Aeronave exibir(@PathVariable Long id) throws Exception {
		return aeronaveService.exibirDetalhes(id);
	}
	
	@PostMapping()
	public ResponseEntity<Aeronave> salvar(@RequestBody Aeronave aeronave, HttpServletResponse response) throws Exception {
		return aeronaveService.gravarAeronave(aeronave, response);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Aeronave> atualizar(@PathVariable Long id, @RequestBody Aeronave aluno, HttpServletResponse response) throws Exception {
		return aeronaveService.atualizarAeronave(id, aluno, response);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Long id) throws Exception {
		aeronaveService.deletarAeronave(id);
	}
	

}
