package com.esprit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.esprit.domain.ClassEntity;
import com.esprit.repository.search.ClassRepositoryCustom;

public interface ClassRepository extends JpaRepository<ClassEntity, String>, ClassRepositoryCustom {

	ClassEntity findByLabel(String label);

	@Query(nativeQuery = true, value = "select * from CLASSE cl join ESP_MODULE_CLASS cm ON cm.fk_class = cl.classId where cm.fk_module = ?1")
	List<ClassEntity> findByModule(String module);

}