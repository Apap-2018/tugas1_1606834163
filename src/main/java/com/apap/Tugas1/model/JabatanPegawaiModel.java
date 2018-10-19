package com.apap.Tugas1.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "jabatan_pegawai")
public class JabatanPegawaiModel implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_pegawai", referencedColumnName = "id", nullable = false)
	@OnDelete(action = OnDeleteAction.NO_ACTION	)
	@JsonIgnore
	private PegawaiModel pegawai;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_jabatan", referencedColumnName = "id", nullable = false)
	@OnDelete(action = OnDeleteAction.NO_ACTION	)
	@JsonIgnore
	private JabatanModel jabatan;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public PegawaiModel getIdPegawai() {
		return pegawai;
	}

	public void setIdPegawai(PegawaiModel idPegawai) {
		this.pegawai = idPegawai;
	}

	public JabatanModel getIdJabatan() {
		return jabatan;
	}

	public void setIdJabatan(JabatanModel idJabatan) {
		this.jabatan = idJabatan;
	}
	
	
}