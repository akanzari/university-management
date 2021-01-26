package com.esprit.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.collections4.CollectionUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "ESP_ENSEIGNANT")
public class TeacherEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String teacherId;

	private String fullName;

	private String email;

	private String phone;

	private String up;

	@ManyToOne(cascade = CascadeType.ALL, optional = true)
	@JoinColumn(name = "fk_departmenent")
	private DepartmentEntity departement;

	@OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<DisponibilityEntity> disponibilities;

	@JsonIgnore
	@ManyToMany(mappedBy = "teachers")
	private List<AssignClassModuleEntity> assignClasses;

	public void addDisponibilities(List<DisponibilityEntity> disponibilityEntities) {
		if (CollectionUtils.isEmpty(disponibilities)) {
			disponibilities = new ArrayList<>();
			disponibilities.addAll(disponibilityEntities);
			disponibilityEntities.forEach(item -> item.teacher(this));
		} else {
			disponibilities.forEach(item -> item.teacher(this));
		}
	}

	public void addDisponibility(DisponibilityEntity disponibilityEntity) {
		if (CollectionUtils.isEmpty(disponibilities)) {
			disponibilities = new ArrayList<>();
		}
		disponibilities.add(disponibilityEntity);
		disponibilityEntity.teacher(this);
	}

	public void removeDisponibility(DisponibilityEntity disponibilityEntity) {
		disponibilities.remove(disponibilityEntity);
		disponibilityEntity.teacher(null);
	}

	public void removeDisponibilities(List<DisponibilityEntity> disponibilityEntities) {
		disponibilities.removeAll(disponibilityEntities);
		disponibilityEntities.forEach(item -> item.teacher(null));
	}

	public String getTeacherId() {
		return teacherId;
	}

	public TeacherEntity teacherId(String teacherId) {
		this.teacherId = teacherId;
		return this;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getUp() {
		return up;
	}

	public TeacherEntity up(String up) {
		this.up = up;
		return this;
	}

	public DepartmentEntity getDepartement() {
		return departement;
	}

	public TeacherEntity departement(DepartmentEntity departement) {
		this.departement = departement;
		return this;
	}

	public List<AssignClassModuleEntity> getAssignClasses() {
		return assignClasses;
	}

	public TeacherEntity assignClasses(List<AssignClassModuleEntity> assignClasses) {
		this.assignClasses = assignClasses;
		return this;
	}

	public String getFullName() {
		return fullName;
	}

	public TeacherEntity fullName(String fullName) {
		this.fullName = fullName;
		return this;
	}

	public String getEmail() {
		return email;
	}

	public TeacherEntity email(String email) {
		this.email = email;
		return this;
	}

	public List<DisponibilityEntity> getDisponibilities() {
		return disponibilities;
	}

	public TeacherEntity disponibilities(List<DisponibilityEntity> disponibilities) {
		this.disponibilities = disponibilities;
		return this;
	}

}