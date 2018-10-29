package com.apap.Tugas1.service;

import java.sql.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.Tugas1.model.InstansiModel;
import com.apap.Tugas1.model.JabatanModel;
import com.apap.Tugas1.model.JabatanPegawaiModel;
import com.apap.Tugas1.model.PegawaiModel;
import com.apap.Tugas1.repository.PegawaiDb;

@Service
@Transactional
public class PegawaiServiceImpl implements PegawaiService{
	@Autowired
	private PegawaiDb pegawaiDb;

	@Override
	public PegawaiModel getPegawaiByNip(String nip) {
		// TODO Auto-generated method stub
		return pegawaiDb.findByNip(nip);
	}

	@Override
	public PegawaiModel getTermuda(InstansiModel instansi) {
		// TODO Auto-generated method stub
		List<PegawaiModel> pegawaiInstansi = pegawaiDb.findByInstansiOrderByTanggalLahirAsc(instansi);
		return pegawaiInstansi.get(0);
	}

	@Override
	public PegawaiModel getTertua(InstansiModel instansi) {
		// TODO Auto-generated method stub
		List<PegawaiModel> pegawaiInstansi = pegawaiDb.findByInstansiOrderByTanggalLahirAsc(instansi);
		return pegawaiInstansi.get(pegawaiInstansi.size()-1);
	}

	@Override
	public double GetTotalGajiPegawai(PegawaiModel pegawai) {
		double gaji =0; 
		for(JabatanPegawaiModel jabatanPegawai : pegawai.getJabatanPegawai()) {
			double temp = 0;	
			temp = jabatanPegawai.getIdJabatan().getGajiPokok();
			if(gaji < temp) {
				gaji = temp;
			}
		}
		
		double persenTunjangan = pegawai.getInstansiPegawai().getProvinsiInstansi().getPresentaseTunjangan();
		return gaji*persenTunjangan/100 + gaji;

	}

	@Override
	public void addPegawai(PegawaiModel pegawai) {
		// TODO Auto-generated method stub
		pegawaiDb.save(pegawai);
	}

	@Override
	public List<PegawaiModel> getPegawaiByJabatanAndInstansi(JabatanModel jabatan, InstansiModel instansi) {
		// TODO Auto-generated method stub
		return pegawaiDb.findByListJabatanAndInstansi(jabatan, instansi);
		}

	@Override
	public List<PegawaiModel> getPegawaiByInstansi(InstansiModel instansi) {
		// TODO Auto-generated method stub
		return pegawaiDb.findByInstansi(instansi);
		}

	@Override
	public List<PegawaiModel> getPegawaiByJabatan(JabatanModel jabatan) {
		// TODO Auto-generated method stub
		return pegawaiDb.findByListJabatan(jabatan);
		}

	@Override
	public List<JabatanModel> getPegawaiByInstansiAndTanggalLahirAndTahunMasuk(InstansiModel instansiPegawai,
			Date tanggalLahir, String tahunMasuk) {
		// TODO Auto-generated method stub
		return pegawaiDb.findByInstansiAndTanggalLahirAndTahunMasuk(instansiPegawai,
				 tanggalLahir, tahunMasuk);
		}
	

}
