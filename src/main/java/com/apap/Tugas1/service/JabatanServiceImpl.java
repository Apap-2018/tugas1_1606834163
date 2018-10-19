package com.apap.Tugas1.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.Tugas1.model.JabatanModel;
import com.apap.Tugas1.repository.JabatanDb;

@Service
@Transactional
public class JabatanServiceImpl implements JabatanService{
	@Autowired
	private JabatanDb jabatanDb;
	
	@Override
	public void addJabatan(JabatanModel jabatan) {
		// TODO Auto-generated method stub
		jabatanDb.save(jabatan);
	}

	@Override
	public List<JabatanModel> getAllJabatan() {
		// TODO Auto-generated method stub
		return jabatanDb.findAll();
	}

	@Override
	public JabatanModel findJabatanById(long idJabatan) {
		// TODO Auto-generated method stub
		return jabatanDb.findById(idJabatan).get();
	}

	@Override
	public void deleteById(long idJabatan) {
		// TODO Auto-generated method stub
		jabatanDb.deleteById(idJabatan);
	}

}
