package com.apap.Tugas1.service;

import java.util.List;

import com.apap.Tugas1.model.JabatanModel;

public interface JabatanService {
	List<JabatanModel> getAllJabatan();
	void addJabatan(JabatanModel jabatan);
	JabatanModel findJabatanById(long idJabatan);
	void deleteById(long idJabatan);

}
