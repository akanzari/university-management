package com.esprit.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class DepartmentEntity extends Auditable implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "department-uuid")
	@GenericGenerator(name = "department-uuid", strategy = "uuid2")
	private String departmentId;

	@Column(unique = true)
	private String code;

	private String label;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = " department_site_entity", joinColumns = @JoinColumn(name = "departmentId"), inverseJoinColumns = @JoinColumn(name = "siteId"))
	private List<SiteEntity> sites;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<SpecialityEntity> specialities;

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<SiteEntity> getSites() {
		return sites;
	}

	public void setSites(List<SiteEntity> sites) {
		this.sites = sites;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public List<SpecialityEntity> getSpecialities() {
		return specialities;
	}

	public void setSpecialities(List<SpecialityEntity> specialities) {
		this.specialities = specialities;
	}

}