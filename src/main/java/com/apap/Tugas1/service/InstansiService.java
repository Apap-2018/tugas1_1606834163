package com.apap.Tugas1.service;

import java.util.List;

import com.apap.Tugas1.model.InstansiModel;

public interface InstansiService {
	InstansiModel getInstansiDetailById(long id);
	List<InstansiModel> getAllInstansi();

}
