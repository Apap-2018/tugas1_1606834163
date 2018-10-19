package com.apap.Tugas1.service;

import com.apap.Tugas1.model.InstansiModel;
import com.apap.Tugas1.model.PegawaiModel;

public interface PegawaiService {
	PegawaiModel getPegawaiByNip(String nip);

	PegawaiModel getTermuda(InstansiModel instansi);

	PegawaiModel getTertua(InstansiModel instansi);
	
	double GetTotalGajiPegawai(PegawaiModel pegawai);

	void addPegawai(PegawaiModel pegawai);

}
