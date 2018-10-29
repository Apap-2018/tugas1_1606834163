package com.apap.Tugas1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apap.Tugas1.model.InstansiModel;
import com.apap.Tugas1.model.ProvinsiModel;

@Repository
public interface InstansiDb extends JpaRepository<InstansiModel, Long> {

	List<InstansiModel> findByProvinsi(ProvinsiModel provinsi);

}
