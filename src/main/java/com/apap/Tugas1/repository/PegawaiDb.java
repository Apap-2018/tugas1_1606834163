package com.apap.Tugas1.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apap.Tugas1.model.InstansiModel;
import com.apap.Tugas1.model.JabatanModel;
import com.apap.Tugas1.model.PegawaiModel;

@Repository
public interface PegawaiDb extends JpaRepository<PegawaiModel, Long>{
	PegawaiModel findByNip(String nip);
	List<PegawaiModel> findByInstansiOrderByTanggalLahirAsc(InstansiModel instansi);
	List<PegawaiModel> findByListJabatanAndInstansi(JabatanModel jabatan, InstansiModel instansi);
	List<PegawaiModel> findByInstansi(InstansiModel instansi);
	List<PegawaiModel> findByListJabatan(JabatanModel jabatan);
	List<JabatanModel> findByInstansiAndTanggalLahirAndTahunMasuk(InstansiModel instansiPegawai, Date tanggalLahir,
			String tahunMasuk);
	
}
