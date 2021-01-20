package com.esprit.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.collections4.CollectionUtils;

@Entity
@Table(name = "SALLE")
public class RoomEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String classRoomId;

	private String label;

	private int capacity;

	private String pole;

	private String bloc;

	@OneToMany(mappedBy = "room", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<DisponibilityEntity> disponibilities;

	public void addDisponibilities(List<DisponibilityEntity> disponibilityEntities) {
		if (CollectionUtils.isEmpty(disponibilities)) {
			disponibilities = new ArrayList<>();
		}
		disponibilities.addAll(disponibilityEntities);
		disponibilityEntities.forEach(item -> item.setRoom(this));
	}

	public void removeDisponibility(DisponibilityEntity disponibilityEntity) {
		disponibilities.remove(disponibilityEntity);
		disponibilityEntity.setRoom(null);
	}

	public String getClassRoomId() {
		return classRoomId;
	}

	public void setClassRoomId(String classRoomId) {
		this.classRoomId = classRoomId;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public String getPole() {
		return pole;
	}

	public void setPole(String pole) {
		this.pole = pole;
	}

	public String getBloc() {
		return bloc;
	}

	public void setBloc(String bloc) {
		this.bloc = bloc;
	}

	public List<DisponibilityEntity> getDisponibilities() {
		return disponibilities;
	}

	public void setDisponibilities(List<DisponibilityEntity> disponibilities) {
		this.disponibilities = disponibilities;
	}

}