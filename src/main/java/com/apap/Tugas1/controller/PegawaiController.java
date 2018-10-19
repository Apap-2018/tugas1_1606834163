package com.apap.Tugas1.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.Tugas1.model.InstansiModel;
import com.apap.Tugas1.model.JabatanModel;
import com.apap.Tugas1.model.JabatanPegawaiModel;
import com.apap.Tugas1.model.PegawaiModel;
import com.apap.Tugas1.model.ProvinsiModel;
import com.apap.Tugas1.service.InstansiService;
import com.apap.Tugas1.service.JabatanService;
import com.apap.Tugas1.service.PegawaiService;
import com.apap.Tugas1.service.ProvinsiService;

@Controller
public class PegawaiController {
	@Autowired
	private PegawaiService pegawaiService;
	
	@Autowired
	private InstansiService instansiService;
	
	@Autowired
	private JabatanService jabatanService;
	
	@Autowired
	private ProvinsiService provinsiService;
	
	@RequestMapping("/")
	private String home(Model model) {
		model.addAttribute("title", "Home");
		
		//untuk lihat jabatan
		List<JabatanModel> listJabatan = new ArrayList<>();
		listJabatan = jabatanService.getAllJabatan();
		
		List<InstansiModel> listInstansi = new ArrayList<>();
		listInstansi = instansiService.getAllInstansi();
		
		model.addAttribute("jabatan", listJabatan);
		model.addAttribute("instansi", listInstansi);

		
		return "home";
	}
	
	@RequestMapping(value = "/pegawai", method = RequestMethod.GET)
	private String viewPegawai(@RequestParam ("nip") String nip, Model model) {
		model.addAttribute("title", "Detail Pegawai");
		PegawaiModel detailPegawai= pegawaiService.getPegawaiByNip(nip);
		model.addAttribute("pegawai", detailPegawai);
		
		double gajiTertinggi=0;
		double persenTunjangan= detailPegawai.getInstansiPegawai().getProvinsiInstansi().getPresentaseTunjangan();
		List<String> jabatan = new ArrayList<>();
		for(JabatanPegawaiModel jabatanPegawai : detailPegawai.getJabatanPegawai()) {
			double tempGaji = 0;
			if(jabatanPegawai.getIdPegawai().getId() == detailPegawai.getId()) {
				tempGaji = jabatanPegawai.getIdJabatan().getGajiPokok();
				jabatan.add(jabatanPegawai.getIdJabatan().getNama());
				if(gajiTertinggi < tempGaji) {
					gajiTertinggi=tempGaji;
				}
			}
		}
		
		model.addAttribute("gaji", gajiTertinggi);
		model.addAttribute("jabatan", jabatan);
		
		return "viewPegawai";
	}
	
	@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.GET)
	private String tambahPegawai(Model model) {
		model.addAttribute("title", "Tambah Pegawai");
		
		List<InstansiModel> listInstansi = instansiService.getAllInstansi();
		List<ProvinsiModel> listProvinsi = provinsiService.getAllProvinsi();
		List<JabatanModel> listJabatan = jabatanService.getAllJabatan();
		
		model.addAttribute("pegawai", new PegawaiModel());
		model.addAttribute("listInstansi", listInstansi);
		model.addAttribute("listProvinsi", listProvinsi);
		model.addAttribute("listJabatan", listJabatan);

		return "addPegawai";
	}
	
	@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.POST)
	private String tambahPegawaiSukses( @ModelAttribute PegawaiModel pegawai,Model model) {
		model.addAttribute("title", "Tambah Pegawai");
		
		pegawaiService.addPegawai(pegawai);
		model.addAttribute("output", "Pegawai Berhasil Ditambah");

		return "output";
	}
	
	
	@RequestMapping(value = "/pegawai/ubah", method = RequestMethod.GET)
	private String ubahPegawai(@RequestParam(value = "nip", required = true) String nip,Model model) {
		model.addAttribute("title", "Tambah Pegawai");
		
		model.addAttribute("pegawai", pegawaiService.getPegawaiByNip(nip));
		return "ubahPegawai";
	}
	
	@RequestMapping(value="/pegawai/ubah", method = RequestMethod.POST)
	private String ubahJabatanSukses(@ModelAttribute PegawaiModel pegawai, Model model) {		
		model.addAttribute("title", "Detail Jabatan");

		pegawaiService.addPegawai(pegawai);
		
		model.addAttribute("output", "Pegawai Berhasil Diubah");
		return "output";
	}
	
	@RequestMapping(value = "/pegawai/termuda-tertua", method = RequestMethod.GET)
	private String terMudaTua(@RequestParam("idInstansi") long id, Model model) {
		InstansiModel instansi = instansiService.getInstansiDetailById(id);
		
		PegawaiModel pegawaiTermuda = pegawaiService.getTermuda(instansi);
		PegawaiModel pegawaiTertua = pegawaiService.getTermuda(instansi);
		
		double gajiTermuda = 0;
		double gajiTertua = 0;
		List<String> jabatanTermuda = new ArrayList<>();
		List<String> jabatanTertua = new ArrayList<>();
		
		for(JabatanPegawaiModel jabatanPegawai : pegawaiTermuda.getJabatanPegawai()) {
			double temp = 0;	
			temp = jabatanPegawai.getIdJabatan().getGajiPokok();
			jabatanTermuda.add(jabatanPegawai.getIdJabatan().getNama());
			if(gajiTermuda < temp) {
				gajiTermuda = temp;
			}
		}
		for(JabatanPegawaiModel jabatanPegawai : pegawaiTermuda.getJabatanPegawai()) {
			double temp = 0;	
			temp = jabatanPegawai.getIdJabatan().getGajiPokok();
			jabatanTertua.add(jabatanPegawai.getIdJabatan().getNama());
			if(gajiTertua < temp) {
				gajiTertua = temp;
			}
		
		}
		double persentunjangan = pegawaiTermuda.getInstansiPegawai().getProvinsiInstansi().getPresentaseTunjangan();
		
		model.addAttribute("pegawaiTermuda", pegawaiTermuda);
		model.addAttribute("pegawaiTertua", pegawaiTertua);
		model.addAttribute("gajiTermuda", "Rp"+ String.format("%.0f", gajiTermuda * persentunjangan/100 + gajiTermuda));
		model.addAttribute("gajiTertua", "Rp"+ String.format("%.0f", gajiTertua * persentunjangan/100 + gajiTertua));
		model.addAttribute("jabatanTermuda", jabatanTermuda);
		model.addAttribute("jabatanTertua", jabatanTertua);

		return "viewTertuaTermuda";
		
	}
}
