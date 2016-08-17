package com.mipro.ard.penajdwalan.jadwal.RecylerNavC;

/**
 * Created by ard on 7/26/2016.
 */
public class ListItemTanggal {
    private String tanggal;
    private String bulan;
    private String bln_num;

    public ListItemTanggal(){}

    public ListItemTanggal(String tanggal, String bulan) {
        this.tanggal = tanggal;
        this.bulan = bulan;

    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getBulan() {
        return bulan;
    }

    public void setBulan(String bulan) {
        this.bulan = bulan;
    }

    public String getBln_num() {
        return bln_num;
    }

    public void setBln_num(String bln_num) {
        this.bln_num = bln_num;
    }
}
