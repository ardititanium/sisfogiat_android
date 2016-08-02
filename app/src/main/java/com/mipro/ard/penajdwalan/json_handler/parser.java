package com.mipro.ard.penajdwalan.json_handler;

/**
 * Created by ard on 7/23/2016.
 */
public class parser {
    //IP DEFAULT
    public static final String IP_PUBLIC = "192.168.1.6";

    // SATALANTAS LOADER
    public static final String DATA_SAT_SPINNER = "http://"+ IP_PUBLIC +"/ditlantas/json/satlantas/view_spinner.php";
    public static final String DATA_SATLANATAS = "http://"+ IP_PUBLIC +"/ditlantas/json/satlantas/view.php";
    public static final String ID_SATALTANS = "idSatuan";
    public static final String NAMA_SATUAN = "namaSatuan";
    public static final String ALAMAT_SATUAN = "alamat";
    public static final String SATLANTAS_JSON = "result";


    // KATEGORI LOADER
    public static final String DATA_KAT_SPINNER = "http://"+ IP_PUBLIC +"/ditlantas/json/kategori/view_spinner.php";
    public static final String DATA_KATEGORI = "http://"+ IP_PUBLIC +"/ditlantas/json/kategori/view.php";
    public static final String ID_KATEGORI = "idKategori";
    public static final String NAMA_KATEGORI = "namaKategori";
    public static final String KATEGORI_JSON = "result";

    // SP UPLOADER
    public static final int PICK_IMAGE_REQUEST= 1;

    // PEROSNIL LOADER
    public static final String DATA_PERSONIL = "http://"+ IP_PUBLIC +"/ditlantas/json/personil/view.php";



    // KEGIATAN LOADER
    public static final String DATA_KEGIATAN= "http://"+ IP_PUBLIC +"/ditlantas/json/kegiatan/view.php";

    // SURAT SAVER
    public static final String DATA_SURAT = "http://"+ IP_PUBLIC +"/ditlantas/json/surat/insert.php";


    // LOGIN HANDLER
    public static final String DATA_LOGIN = "http://"+ IP_PUBLIC +"/ditlantas/json/login/login.php";
    public static final String SHARED_PREF_NAME = "loginsisfo";
    public static final String LOGGEDIN_SHARED_PREF = "loggedin";
    public static final String LOGIN_SUCCESS = "success";

    public static final String NRP_SHARED_PREF      = "nrp";
    public static final String NAMA_SHARED_PREF     = "nama";
    public static String AKSES_SHARED_PREF;
    public static final String ID_SHARED_PREF       = "id";
    public static final String SATUAN_SHARED_PREF   = "satuan";
    public static final String PASS_SHARED_PREF     = "pass";
    public static final String PANGKAT_SHARED_PREF  = "pangkat";

}
