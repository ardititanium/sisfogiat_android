package com.mipro.ard.penajdwalan.json_handler;

/**
 * Created by ard on 7/23/2016.
 */
public class parser {
    //IP DEFAULT
    public static final String IP_PUBLIC = "192.168.185.1";

    // SATALANTAS LOADER
    public static final String DATA_SATLANATAS = "http://"+ IP_PUBLIC +"/ditlantas/json/satlantas/view.php";
    public static final String ID_SATALTANS = "idSatuan";
    public static final String NAMA_SATUAN = "namaSatuan";
    public static final String ALAMAT_SATUAN = "alamat";
    public static final String SATLANTAS_JSON = "result";


    // KATEGORI LOADER
    public static final String DATA_KATEGORI = "http://"+ IP_PUBLIC +"/ditlantas/json/kategori/view.php";
    public static final String ID_KATEGORI = "idKategori";
    public static final String NAMA_KATEGORI = "namaKategori";
    public static final String KATEGORI_JSON = "result";

    // SP UPLOADER
    public static final int PICK_IMAGE_REQUEST= 1;




}
