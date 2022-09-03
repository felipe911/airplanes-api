package com.airplanes.sonda.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.airplanes.sonda.exception.BusinessException;
import com.airplanes.sonda.model.Aeronave;
import com.airplanes.sonda.repository.AeronaveRepository;
import com.airplanes.sonda.util.Util;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

@Service
public class AeronaveService {

	private static final String AERONAVE_NAO_ENCONTRADA = "Aeronave não encontrada";

	@Autowired
	private AeronaveRepository aeronaveRepository;

	private Util util = new Util();

	public List<Aeronave> listarAeronaves() {
		return aeronaveRepository.findAll();
	}

	public List<Aeronave> listarAeronavesParametro(String parametro) throws BusinessException {

		JsonObject convertedObject = new Gson().fromJson(parametro, JsonObject.class);

		JsonElement id = convertedObject.get("id");
		JsonElement nome = convertedObject.get("nome");
		List<Aeronave> listagem = new ArrayList<>();

		if ((id == null) && !(nome == null)) {
			listagem.addAll(aeronaveRepository.findByNome(util.removeAspas(nome.toString())));
		}

		if (!(id == null) && (nome == null)) {
			Optional<Aeronave> aero = aeronaveRepository.findById(Long.parseLong(util.removeAspas(id.toString())));
			if (aero.isPresent())
				listagem.add(aero.get());
		}
		
		if(listagem.isEmpty())
			throw new BusinessException("Não foi possível localizar a aeronave.");

		return listagem;

	}

	public Aeronave exibirDetalhes(Long id) {
		return aeronaveRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(AERONAVE_NAO_ENCONTRADA));
	}

	public ResponseEntity<Aeronave> gravarAeronave(Aeronave aeronave, HttpServletResponse response) throws BusinessException {

		try {

			Aeronave aeronaveSalvar = aeronaveRepository.save(aeronave);

			URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(aeronaveSalvar.getId()).toUri();
			response.setHeader("Location", uri.toASCIIString());
			return ResponseEntity.created(uri).body(aeronaveSalvar);

		} catch (Exception e) {
			throw new BusinessException("Não foi possível gravar a aeronave.");
		}

	}

	public ResponseEntity<Aeronave> atualizarAeronave(Long id, Aeronave aeronave, HttpServletResponse response)
			throws BusinessException {

		aeronaveRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(AERONAVE_NAO_ENCONTRADA + " para atualizar"));

		try {

			aeronave.setId(id);
			Aeronave aeronaveAtualizada = aeronaveRepository.save(aeronave);

			URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(aeronaveAtualizada.getId()).toUri();
			response.setHeader("Location", uri.toASCIIString());
			return ResponseEntity.created(uri).body(aeronaveAtualizada);

		} catch (Exception e) {
			throw new BusinessException("Não foi possível atualizar a aeronave.");
		}
	}

	public void deletarAeronave(Long id) throws BusinessException {

		aeronaveRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(AERONAVE_NAO_ENCONTRADA + " para deletar"));

		try {
			aeronaveRepository.deleteById(id);

		} catch (Exception e) {
			throw new BusinessException("Não foi possível deletar a aeronave.");
		}
	}

}
