package com.apap.Tugas1.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.Tugas1.model.InstansiModel;
import com.apap.Tugas1.repository.InstansiDb;

@Service
@Transactional
public class InstansiServiceImpl implements InstansiService{
	@Autowired
	private InstansiDb instansiDb;
	
	@Override
	public InstansiModel getInstansiDetailById(long id) {
		// TODO Auto-generated method stub
		return instansiDb.findById(id).get();
	}

	@Override
	public List<InstansiModel> getAllInstansi() {
		// TODO Auto-generated method stub
		return instansiDb.findAll();
	}

}
