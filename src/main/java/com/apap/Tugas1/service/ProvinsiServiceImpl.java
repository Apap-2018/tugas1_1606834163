package com.apap.Tugas1.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.Tugas1.model.ProvinsiModel;
import com.apap.Tugas1.repository.ProvinsiDb;

@Service
@Transactional
public class ProvinsiServiceImpl implements ProvinsiService{
	@Autowired
	private ProvinsiDb provinsiDb;
	
	@Override
	public List<ProvinsiModel> getAllProvinsi() {
		// TODO Auto-generated method stub
		return provinsiDb.findAll();
	}

	@Override
	public ProvinsiModel getProvinsiById(Long idProvinsi) {
		// TODO Auto-generated method stub
		return provinsiDb.findById(idProvinsi).get();
		}

}
