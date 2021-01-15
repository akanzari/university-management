package com.esprit.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.esprit.enums.PoleEnum;

@Entity
@Table(name = "SALLE")
public class RoomEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String classRoomId;

	private String label;

	private int capacity;

	private PoleEnum pole;

	private String bloc;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "fk_room")
	private List<DisponibilityEntity> disponibilities;

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

	public PoleEnum getPole() {
		return pole;
	}

	public void setPole(PoleEnum pole) {
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