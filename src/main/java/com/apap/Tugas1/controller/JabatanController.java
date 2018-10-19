package com.apap.Tugas1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.Tugas1.model.JabatanModel;
import com.apap.Tugas1.model.JabatanPegawaiModel;
import com.apap.Tugas1.service.JabatanService;

@Controller
public class JabatanController {
	@Autowired
	private JabatanService jabatanService;
	
	
	@RequestMapping(value = "/jabatan/tambah", method = RequestMethod.GET)
	private String tambahJabatan(Model model) {
		model.addAttribute("title", "Tambah Jabatan");
		model.addAttribute("jabatan", new JabatanModel());
		return "addJabatan";
	}
	
	@RequestMapping(value = "/jabatan/tambah", method = RequestMethod.POST)
	private String tambahJabatanSukses(@ModelAttribute JabatanModel jabatan,Model model) {
		model.addAttribute("title", "Sukses");
		jabatanService.addJabatan(jabatan);

		model.addAttribute("output", "Jabatan Berhasil Ditambah");
		return "output";
	}
	
	@RequestMapping("/jabatan/view")
	private String viewJabatan(@RequestParam(value = "idJabatan", required = true) long idJabatan, Model model) {
		model.addAttribute("title", "Detail Jabatan");
		JabatanModel jabatan = jabatanService.findJabatanById(idJabatan);
		
		int count = 0;
		for(JabatanPegawaiModel jp : jabatan.getPegawaiJabatan()) {
			if(jp.getIdPegawai().getId() == idJabatan) {
				count++;
			}
		}
		model.addAttribute("jumlahPegawai", count);

		model.addAttribute("jabatan", jabatan);
		return "viewJabatan";
	}
	
	@RequestMapping(value="/jabatan/ubah", method = RequestMethod.GET)
	private String ubahJabatan(@RequestParam(value = "idJabatan", required = true) long idJabatan, Model model) {
		model.addAttribute("title", "Detail Jabatan");

		model.addAttribute("jabatan", jabatanService.findJabatanById(idJabatan));
		
		return "ubahJabatan";
	}
	
	@RequestMapping(value="/jabatan/ubah", method = RequestMethod.POST)
	private String ubahJabatanSukses(@ModelAttribute JabatanModel jabatan, Model model) {		
		model.addAttribute("title", "Detail Jabatan");

		jabatanService.addJabatan(jabatan);
		
		model.addAttribute("output", "Jabatan Berhasil Diubah");
		return "output";
	}
	
	@RequestMapping(value = "/jabatan/hapus", method = RequestMethod.POST)
	private String hapusJabatan(@RequestParam(value = "idJabatan", required = true) long idJabatan, Model model) { 
		model.addAttribute("title", "Detail Jabatan");
		try {
        	jabatanService.deleteById(idJabatan);
        	model.addAttribute("output", "Jabatan Berhasil Dihapus");
		}
		catch (Exception e) {
        	model.addAttribute("output", "Penghapusan Jabatan Gagal, Terdapat Pegawai Didalamnya ");
		}
		
		return "output";
	}
	
	@RequestMapping(value ="/jabatan/viewall", method = RequestMethod.GET)
	private String viewAll(Model model) {
		model.addAttribute("title", "ViewAll Jabatan");

		model.addAttribute("listJabatan", jabatanService.getAllJabatan());
		return "viewAllJabatan";		
	}

}
