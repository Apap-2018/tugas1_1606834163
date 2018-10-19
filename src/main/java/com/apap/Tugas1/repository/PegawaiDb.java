package com.apap.Tugas1.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apap.Tugas1.model.InstansiModel;
import com.apap.Tugas1.model.PegawaiModel;

@Repository
public interface PegawaiDb extends JpaRepository<PegawaiModel, Long>{
	PegawaiModel findByNip(String nip);
	Optional<PegawaiModel> findById(Long id);
	List<PegawaiModel> findByInstansiOrderByTanggalLahirAsc(InstansiModel instansi);
}
