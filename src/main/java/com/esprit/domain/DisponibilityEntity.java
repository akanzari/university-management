package com.esprit.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class DisponibilityEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "disponibility-uuid")
	@GenericGenerator(name = "disponibility-uuid", strategy = "uuid2")
	private String disponibilityId;

}