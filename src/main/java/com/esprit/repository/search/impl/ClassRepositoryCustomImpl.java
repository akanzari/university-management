package com.esprit.repository.search.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;

import com.esprit.domain.ClassEntity;
import com.esprit.repository.search.ClassRepositoryCustom;

public class ClassRepositoryCustomImpl implements ClassRepositoryCustom {

	private final EntityManager em;

	public ClassRepositoryCustomImpl(EntityManager em) {
		this.em = em;
	}

	@Override
	public List<ClassEntity> searchConventions(String name, String email, String speciality, int nbrStudents) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<ClassEntity> query = cb.createQuery(ClassEntity.class);

		Root<ClassEntity> classs = query.from(ClassEntity.class);
		List<Predicate> predicates = new ArrayList<>();

		if (!StringUtils.isBlank(name)) {
			predicates.add(cb.equal(classs.get("classId"), name));
		}
		if (!StringUtils.isBlank(email)) {
			predicates.add(cb.equal(classs.get("email"), email));
		}
		if (!StringUtils.isBlank(speciality)) {
			predicates.add(cb.equal(classs.get("speciality"), speciality));
		}
		predicates.add(cb.equal(classs.get("nbrStudents"), nbrStudents));

		query.where(predicates.toArray(new Predicate[0]));

		TypedQuery<ClassEntity> typedQuery = em.createQuery(query);
		return typedQuery.getResultList();
	}

}