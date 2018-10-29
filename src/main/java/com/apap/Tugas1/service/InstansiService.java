package com.apap.Tugas1.service;

import java.util.List;

import com.apap.Tugas1.model.InstansiModel;
import com.apap.Tugas1.model.ProvinsiModel;

public interface InstansiService {
	InstansiModel getInstansiDetailById(Long id);
	List<InstansiModel> getAllInstansi();
	List<InstansiModel> getInstansiByProvinsi(ProvinsiModel provinsi);

}
