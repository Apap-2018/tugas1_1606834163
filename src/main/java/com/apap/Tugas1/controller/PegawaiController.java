package com.apap.Tugas1.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
		
		model.addAttribute("gaji", (gajiTertinggi+(gajiTertinggi*persenTunjangan)));
		model.addAttribute("jabatan", jabatan);
		
		return "viewPegawai";
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
	
	@RequestMapping(value = "/pegawai/cari")
	private String cariPegawai(@RequestParam(value="idProvinsi", required=false) Long idProvinsi,
								@RequestParam(value="idInstansi", required=false) Long idInstansi,
								@RequestParam(value="idJabatan", required=false) Long idJabatan, Model model
			) {
		
		
		List<ProvinsiModel> listProvinsi = provinsiService.getAllProvinsi();
		List<InstansiModel> listInstansi = instansiService.getAllInstansi();
		List<JabatanModel> listJabatan = jabatanService.getAllJabatan();
		
		List<PegawaiModel> listPegawai = new ArrayList<>();
		if(idInstansi != null) {
			InstansiModel instansi = instansiService.getInstansiDetailById(idInstansi);
			if(idJabatan != null) {
				JabatanModel jabatan = jabatanService.findJabatanById(idJabatan);
				listPegawai = pegawaiService.getPegawaiByJabatanAndInstansi(jabatan, instansi);
			}else {
				listPegawai = pegawaiService.getPegawaiByInstansi(instansi);
			}
		}else {
			if(idProvinsi != null) {
				ProvinsiModel provinsi = provinsiService.getProvinsiById(idProvinsi);
				List<InstansiModel> listInstansiCurr = instansiService.getInstansiByProvinsi(provinsi);
				
				if(idJabatan != null) {
					JabatanModel jabatan = jabatanService.findJabatanById(idJabatan);
					for(InstansiModel instansi : listInstansiCurr) {
						listPegawai.addAll(pegawaiService.getPegawaiByJabatanAndInstansi(jabatan, instansi));
					}
				}else {
					for(InstansiModel instansi : listInstansiCurr) {
						listPegawai.addAll(pegawaiService.getPegawaiByInstansi(instansi));
					}
				}
				
			}else if(idJabatan != null) {
				JabatanModel jabatan = jabatanService.findJabatanById(idJabatan);
				listPegawai = pegawaiService.getPegawaiByJabatan(jabatan);
			}
			
		}
		
		
		model.addAttribute("listPegawai", listPegawai);
		model.addAttribute("listProvinsi", listProvinsi);
		model.addAttribute("listInstansi", listInstansi);
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("caripegawai", true);
		return "searchPegawai";
	}
	
	@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.GET)
	private String add(Model model) {
		List<ProvinsiModel> listProvinsi = provinsiService.getAllProvinsi();
		List<JabatanModel> listJabatan = jabatanService.getAllJabatan();
		
		List<InstansiModel> listInstansi = instansiService.getInstansiByProvinsi(listProvinsi.get(0));
		
		PegawaiModel pegawai = new PegawaiModel();
		pegawai.setListJabatan(new ArrayList<JabatanModel>());
		pegawai.getListJabatan().add(new JabatanModel());
		
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("listInstansi", listInstansi);
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("listProvinsi", listProvinsi);
		model.addAttribute("tambahpegawai", true);
		return "addPegawai";
	}
	
	@RequestMapping(value="/pegawai/tambah", params={"addRow"}, method = RequestMethod.POST)
	public String addRow(@ModelAttribute PegawaiModel pegawai, BindingResult bindingResult, Model model) {
	    return "addJabatan";
	}
	
	
	@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.POST)
	private String addPegawaiSubmit(@ModelAttribute PegawaiModel pegawai, Model model) {
		String kode = pegawai.getInstansiPegawai().getId() + "";
		SimpleDateFormat newFormat = new SimpleDateFormat("dd-MM-yy");
		String tanggalLahir = newFormat.format(pegawai.getTanggalLahir()).replaceAll("-", "");
		
		String tahunKerja = pegawai.getTahunMasuk();

		int urutan = pegawaiService.getPegawaiByInstansiAndTanggalLahirAndTahunMasuk(pegawai.getInstansiPegawai(), pegawai.getTanggalLahir(), pegawai.getTahunMasuk()).size()+1;
		
		String strUrutan="";
		if(urutan<10) {
			strUrutan="0"+urutan;
		}
		else {
			strUrutan=""+urutan;
		}
		
		String nip = kode + tanggalLahir + tahunKerja + strUrutan;
		
		pegawai.setNip(nip);
		
		pegawaiService.addPegawai(pegawai);
		
		String msg = "Pegawai dengan NIP "+ nip +" berhasil ditambahkan";
		model.addAttribute("output", msg);
		model.addAttribute("tambahpegawai", true);
		return "output";
	}
	
	@RequestMapping(value = "/pegawai/ubah", method = RequestMethod.GET)
	private String ubah(@RequestParam(value = "nip", required = true) String nip , Model model) {
		List<ProvinsiModel> listProvinsi = provinsiService.getAllProvinsi();
		List<JabatanModel> listJabatan = jabatanService.getAllJabatan();
		
		//default
		List<InstansiModel> listInstansi = instansiService.getInstansiByProvinsi(listProvinsi.get(0));
		
		PegawaiModel pegawai = pegawaiService.getPegawaiByNip(nip);
		pegawai.setListJabatan(new ArrayList<JabatanModel>());
		pegawai.getListJabatan().add(new JabatanModel());
		
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("listInstansi", listInstansi);
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("listProvinsi", listProvinsi);
		
		return "ubahPegawai";
	}
	
	@RequestMapping(value="/pegawai/ubah", params={"addRow"}, method = RequestMethod.POST)
	public String addRowUbah(@ModelAttribute PegawaiModel pegawai, BindingResult bindingResult, Model model) {
		
		List<ProvinsiModel> listProv = provinsiService.getAllProvinsi();
		List<JabatanModel> listJabatan = jabatanService.getAllJabatan();
		
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("listProvinsi", listProv);
		
		List<InstansiModel> listInstansi = instansiService.getInstansiByProvinsi(pegawai.getInstansiPegawai().getProvinsiInstansi());
		model.addAttribute("listInstansi", listInstansi);
		pegawai.getListJabatan().add(new JabatanModel());
	    model.addAttribute("pegawai", pegawai);
	    
	    return "ubahPegawai";
	}
	
	@RequestMapping(value="/pegawai/ubah", params={"deleteRow"}, method = RequestMethod.POST)
	public String deleteRowUbah(@ModelAttribute PegawaiModel pegawai, BindingResult bindingResult, HttpServletRequest req,Model model) {
		
		List<ProvinsiModel> listProv = provinsiService.getAllProvinsi();
		List<JabatanModel> listJabatan = jabatanService.getAllJabatan();
		
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("listProvinsi", listProv);
		

		List<InstansiModel> listInstansi = instansiService.getInstansiByProvinsi(pegawai.getInstansiPegawai().getProvinsiInstansi());
		model.addAttribute("listInstansi", listInstansi);
		
		Integer rowId = Integer.valueOf(req.getParameter("deleteRow"));
		System.out.println(rowId);
		pegawai.getListJabatan().remove(rowId.intValue());
	    model.addAttribute("pegawai", pegawai);
	    
	    return "ubahPegawai";
	}
	
	@RequestMapping(value = "/pegawai/ubah", method = RequestMethod.POST)
	private String ubahPegawaiSubmit(@ModelAttribute PegawaiModel pegawai, Model model) {
		String kode = pegawai.getInstansiPegawai().getId() + "";
		
		SimpleDateFormat newFormat = new SimpleDateFormat("dd-MM-yy");
		String tanggalLahir = newFormat.format(pegawai.getTanggalLahir()).replaceAll("-", "");
		
		String tahunKerja = pegawai.getTahunMasuk();

		int urutan = pegawaiService.getPegawaiByInstansiAndTanggalLahirAndTahunMasuk(pegawai.getInstansiPegawai(), pegawai.getTanggalLahir(), pegawai.getTahunMasuk()).size()+1;
		
		String strUrutan;
		if(urutan<10) strUrutan="0"+urutan;
		else strUrutan=""+urutan;
		
		String nip = kode + tanggalLahir + tahunKerja + strUrutan;
		
		pegawai.setNip(nip);
		
		pegawaiService.addPegawai(pegawai);
		
		String msg = "Pegawai dengan NIP "+ nip +" berhasil ubah";
		model.addAttribute("output", msg);
		
		return "output";
	}
}
