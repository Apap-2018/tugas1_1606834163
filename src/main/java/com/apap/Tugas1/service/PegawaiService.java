package com.apap.Tugas1.service;

import java.sql.Date;
import java.util.List;

import com.apap.Tugas1.model.InstansiModel;
import com.apap.Tugas1.model.JabatanModel;
import com.apap.Tugas1.model.PegawaiModel;

public interface PegawaiService {
	PegawaiModel getPegawaiByNip(String nip);

	PegawaiModel getTermuda(InstansiModel instansi);

	PegawaiModel getTertua(InstansiModel instansi);
	
	double GetTotalGajiPegawai(PegawaiModel pegawai);

	void addPegawai(PegawaiModel pegawai);

	List<PegawaiModel> getPegawaiByJabatanAndInstansi(JabatanModel jabatan, InstansiModel instansi);

	List<PegawaiModel> getPegawaiByInstansi(InstansiModel instansi);

	List<PegawaiModel> getPegawaiByJabatan(JabatanModel jabatan);

	List<JabatanModel> getPegawaiByInstansiAndTanggalLahirAndTahunMasuk(InstansiModel instansiPegawai,
			Date tanggalLahir, String tahunMasuk);

}
