package com.mipro.ard.penajdwalan.jadwal.RecylerPersTerlibat;

/**
 * Created by ard on 7/25/2016.
 */
public class ListItemPersonilTerlibat {
    String nrp, nama, pangkat, idjadwal;
    private boolean selected;

    public String getNrp() {
        return nrp;
    }

    public void setNrp(String nrp) {
        this.nrp = nrp;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }


    public String getPangkat() {
        return pangkat;
    }

    public void setPangkat(String pangkat) {
        this.pangkat = pangkat;
    }

    public String getIdjadwal() {
        return idjadwal;
    }

    public void setIdjadwal(String idjadwal) {
        this.idjadwal = idjadwal;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
