package com.mipro.ard.penajdwalan.RecyclerHandler.l.agenda;

/**
 * Created by ard on 8/4/2016.
 */
public class Agenda {
    String deskripsi, jamMulai, jamSelesai, keterangan, durasi;

    public Agenda(){

    }

    public Agenda(String deskripsi, String jamMulai, String jamSelesai, String keterangan, String durasi) {
        this.deskripsi = deskripsi;
        this.jamMulai = jamMulai;
        this.jamSelesai = jamSelesai;
        this.keterangan = keterangan;
        this.durasi = durasi;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getJamMulai() {
        return jamMulai;
    }

    public void setJamMulai(String jamMulai) {
        this.jamMulai = jamMulai;
    }

    public String getJamSelesai() {
        return jamSelesai;
    }

    public void setJamSelesai(String jamSelesai) {
        this.jamSelesai = jamSelesai;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getDurasi() {
        return durasi;
    }

    public void setDurasi(String durasi) {
        this.durasi = durasi;
    }
}
