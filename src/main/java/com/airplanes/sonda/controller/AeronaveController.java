package com.airplanes.sonda.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.airplanes.sonda.exception.BusinessException;
import com.airplanes.sonda.model.Aeronave;
import com.airplanes.sonda.service.AeronaveService;

@RestController
@RequestMapping("/aeronaves")
@CrossOrigin(exposedHeaders="Access-Control-Allow-Origin")
public class AeronaveController {
	
	@Autowired
	private AeronaveService aeronaveService;
	
	@GetMapping(produces = "application/json")
	public List<Aeronave> listar() {
		return aeronaveService.listarAeronaves();
	}
	

	@PostMapping("/find")
	public List<Aeronave> buscaPorParametro(@RequestBody String parametro) throws BusinessException {
		return aeronaveService.listarAeronavesParametro(parametro);
	}
	

	@GetMapping("/{id}")
	public Aeronave exibir(@PathVariable Long id) throws BusinessException {
		return aeronaveService.exibirDetalhes(id);
	}

	@PostMapping()
	public ResponseEntity<Aeronave> salvar(@RequestBody Aeronave aeronave, HttpServletResponse response) throws BusinessException {
		return aeronaveService.gravarAeronave(aeronave, response);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Aeronave> atualizar(@PathVariable Long id, @RequestBody Aeronave aluno, HttpServletResponse response) throws BusinessException {
		return aeronaveService.atualizarAeronave(id, aluno, response);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Long id) throws BusinessException {
		aeronaveService.deletarAeronave(id);
	}
	

}
