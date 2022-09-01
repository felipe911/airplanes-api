package com.airplanes.sonda.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "Aeronave")
public class Aeronave {
	
	@Id
	@JsonProperty
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@SequenceGenerator(name = "service_sequence", sequenceName = "service_sequence", allocationSize=1)
	private Long id;

	@NotNull
	@JsonProperty
	@Column(name = "nome")
	private String nome;

	@NotNull
	@JsonProperty
	@Column(name = "marca")
	private String marca;

	@NotNull
	@JsonProperty
	@Column(name = "ano")
	private Integer ano;

	@JsonProperty
	@Column(name = "descricao")
	private String descricao;

	@NotNull
	@JsonProperty
	@Column(name = "vendido")
	private Boolean vendido;

	@NotNull
	@JsonProperty
	@Column(name = "created")
	private Date created;

	@JsonProperty
	@Column(name = "updated")
	private Date updated;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
