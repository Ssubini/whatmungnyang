package com.ssafy.meongnyang.db.repository;

import com.ssafy.meongnyang.db.entity.Disease;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DiseaseRepository extends JpaRepository<Disease, String> {
    Optional<Disease> findByCode(String code);
}