package com.esprit.repository.search.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;

import com.esprit.domain.DepartmentEntity;
import com.esprit.domain.TeacherEntity;
import com.esprit.repository.search.TeacherRepositoryCustom;

public class TeacherRepositoryCustomImpl implements TeacherRepositoryCustom {

	private final EntityManager em;

	public TeacherRepositoryCustomImpl(EntityManager em) {
		this.em = em;
	}

	@Override
	public List<TeacherEntity> searcheachers(String teacherId, int cin, String departement, String classs) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<TeacherEntity> query = cb.createQuery(TeacherEntity.class);

		Root<TeacherEntity> teacher = query.from(TeacherEntity.class);

		List<Predicate> predicates = new ArrayList<>();

		if (!StringUtils.isBlank(teacherId)) {
			predicates.add(cb.equal(teacher.get("teacherId"), teacherId));
		}
		if (cin != 0) {
			predicates.add(cb.equal(teacher.get("cin"), cin));
		}
		if (!StringUtils.isBlank(departement)) {
			Join<TeacherEntity, DepartmentEntity> departementR = teacher.join("departement");
			predicates.add(cb.equal(departementR.get("departmenetId"), departement));
		}

		query.where(predicates.toArray(new Predicate[0]));

		TypedQuery<TeacherEntity> typedQuery = em.createQuery(query);
		return typedQuery.getResultList();
	}

}