package com.apap.Tugas1.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apap.Tugas1.model.JabatanModel;

@Repository
public interface JabatanDb extends JpaRepository<JabatanModel, Long>{
	Optional<JabatanModel> findById(long id);
	List<JabatanModel> findAll();
}


