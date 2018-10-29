package com.apap.Tugas1.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.UniqueElements;

import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity
@Table(name = "pegawai")
public class PegawaiModel implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	
	@NotNull
	@UniqueElements
	@Size(max = 255)
	@Column(name = "NIP", nullable = false, unique= true)
	private String nip;
	
	@NotNull
	@Size(max = 255)
	@Column(name = "nama", nullable = false)
	private String nama;
	
	@NotNull
	@Size(max = 255)
	@Column(name = "tempat_lahir", nullable = false)
	private String tempatLahir;
	
	@NotNull
	@Column(name = "tanggal_lahir", nullable = false)
	private Date tanggalLahir;
	
	@NotNull
	@Size(max = 255)
	@Column(name = "tahun_masuk", nullable = false)
	private String tahunMasuk;
	
	@OneToMany(mappedBy = "pegawai", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	private List<JabatanPegawaiModel> jabatanPegawai;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_instansi", referencedColumnName = "id", nullable = false)
	@OnDelete(action = OnDeleteAction.NO_ACTION)
	@JsonIgnore
	private InstansiModel instansi;

	@ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(name = "jabatan_pegawai", joinColumns = {@JoinColumn(name = "id_pegawai")}, inverseJoinColumns = {@JoinColumn(name = "id_jabatan")})
	private List<JabatanModel> listJabatan = new ArrayList<JabatanModel>();


	public long getId() {
		return id;
	}
	
	
	public void setId(long id) {
		this.id = id;
	}

	public String getNip() {
		return nip;
	}

	public void setNip(String nip) {
		this.nip = nip;
	}

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public String getTempatLahir() {
		return tempatLahir;
	}

	public void setTempatLahir(String tempatLahir) {
		this.tempatLahir = tempatLahir;
	}

	public Date getTanggalLahir() {
		return tanggalLahir;
	}

	public void setTanggalLahir(Date tanggalLahir) {
		this.tanggalLahir = tanggalLahir;
	}

	public String getTahunMasuk() {
		return tahunMasuk;
	}

	public void setTahunMasuk(String tahunMasuk) {
		this.tahunMasuk = tahunMasuk;
	}

	public InstansiModel getInstansiPegawai() {
		return instansi;
	}

	public void setInstansiPegawai(InstansiModel instansiPegawai) {
		this.instansi = instansiPegawai;
	}


	public List<JabatanPegawaiModel> getJabatanPegawai() {
		return jabatanPegawai;
	}


	public void setJabatanPegawai(List<JabatanPegawaiModel> jabatanPegawai) {
		this.jabatanPegawai = jabatanPegawai;
	}
	
	public List<JabatanModel> getListJabatan() {
		return listJabatan;
	}

	public void setListJabatan(List<JabatanModel> listJabatan) {
		this.listJabatan = listJabatan;
	}
	
	public double getGaji() {
		double presentaseTunjangan = this.instansi.getProvinsiInstansi().getPresentaseTunjangan();
		double gaji = this.listJabatan.get(0).getGajiPokok();
		for (int i = 1 ; i < this.listJabatan.size(); i ++) {
			double currGaji = this.listJabatan.get(i).getGajiPokok();
			if(gaji < currGaji) {
				gaji = currGaji;
			}
		}
		
		gaji = ((gaji * presentaseTunjangan /100) + gaji);
		
		return gaji;
	}
	
	public List<String> getListJabatanString(){
		List<String> listJabatanString = new ArrayList<>(); 
		for(JabatanModel j : this.listJabatan) {
			listJabatanString.add(j.getNama());
		}
		
		return listJabatanString;
	}
	
	
}