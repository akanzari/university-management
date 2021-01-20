package com.esprit.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "EPREUVE")
public class ExamEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "exam-uuid")
	@GenericGenerator(name = "exam-uuid", strategy = "uuid2")
	private String examId;

	@ElementCollection
	@CollectionTable(name = "EPREUVE_LEVELS")
	private List<Integer> levels;
	
	@Column(name="EXAM_SESSION")
	private String session;

	@OneToMany(mappedBy = "exam", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<AssignClassExamEntity> assignClasses;

	public void addAssignClasses(List<AssignClassExamEntity> assignClassExamEntities) {
		if (CollectionUtils.isEmpty(assignClasses)) {
			assignClasses = new ArrayList<>();
			assignClasses.addAll(assignClassExamEntities);
			assignClassExamEntities.forEach(item -> item.setExam(this));
		} else {
			assignClasses.forEach(item -> item.setExam(this));
		}
	}

	public void removeAssignClasses(List<AssignClassExamEntity> assignClassExamEntities) {
		assignClasses.removeAll(assignClassExamEntities);
		assignClassExamEntities.forEach(item -> item.setExam(null));
	}

	public String getExamId() {
		return examId;
	}

	public void setExamId(String examId) {
		this.examId = examId;
	}

	public List<Integer> getLevels() {
		return levels;
	}

	public void setLevels(List<Integer> levels) {
		this.levels = levels;
	}

	public String getSession() {
		return session;
	}

	public void setSession(String session) {
		this.session = session;
	}

	public List<AssignClassExamEntity> getAssignClasses() {
		return assignClasses;
	}

	public void setAssignClasses(List<AssignClassExamEntity> assignClasses) {
		this.assignClasses = assignClasses;
	}

}