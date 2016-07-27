package com.mipro.ard.penajdwalan.tambah;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.mipro.ard.penajdwalan.MainActivity;
import com.mipro.ard.penajdwalan.R;
import com.mipro.ard.penajdwalan.fileUpload.Utils.FileManager;
import com.mipro.ard.penajdwalan.fileUpload.Utils.MultiPartRequest;
import com.mipro.ard.penajdwalan.fileUpload.Utils.StringParser;
import com.mipro.ard.penajdwalan.fileUpload.template.Template;
import com.mipro.ard.penajdwalan.json_handler.MyApplication;

import java.io.File;

public class tambah_surat_perintah extends AppCompatActivity {
    private final String TAG = this.getClass().getName();
    ImageView mImage;
    EditText nama_sp;

    private Button mAdd;
    private ImageView mController;
    private VideoView mVideo;
    private TextView mInfo, mResponse;
    private ProgressBar mProgress;
    private static String[] CHOOSE_FILE = {"Photo", "File manager"};
    private Uri mOutputUri;
    private File mFile;
    private RequestQueue mRequest;
    private MultiPartRequest mMultiPartRequest;
    private MediaPlayer mMediaPlayer;
    private boolean mIsLoad = false;

    TextView title_bar;
    ImageButton mUpload, m_back_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_surat_perintah);

        mProgress = (ProgressBar) findViewById(R.id.progressBar);
        nama_sp = (EditText) findViewById(R.id.nama_sp_up);


        mAdd = (Button) findViewById(R.id.btn_cam_up_sp);
        mImage = (ImageView) findViewById(R.id.holder_up_sp);

        title_bar = (TextView) findViewById(R.id.bar_title);
        m_back_btn = (ImageButton) findViewById(R.id.kembali_btn);
        mUpload = (ImageButton) findViewById(R.id.search_btn);

        title_bar.setText("TAMBAH SURAT PERINTAH");

        m_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back_home = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(back_home);
            }
        });

        mUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadFile();
                mProgress.setVisibility(ProgressBar.VISIBLE);
                mIsLoad = true;
            }
        });

        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                mOutputUri = FileManager.getOutputMediaFileUri(Template.Code.CAMERA_IMAGE_CODE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, mOutputUri);
                startActivityForResult(intent, Template.Code.CAMERA_IMAGE_CODE);
            }
        });

    }

    void uploadFile(){
        mMultiPartRequest = new MultiPartRequest(new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mUpload.setVisibility(Button.VISIBLE);
                mProgress.setVisibility(ProgressBar.GONE);
                mIsLoad = false;
                setResponse(null, error);

            }
        }, new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                mUpload.setVisibility(Button.VISIBLE);
                mProgress.setVisibility(ProgressBar.GONE);
                mIsLoad = false;
                setResponse(response, null);


            }
        }, mFile);

        mMultiPartRequest.setTag("MultiRequest");
        mMultiPartRequest.setRetryPolicy(new DefaultRetryPolicy(Template.VolleyRetryPolicy.SOCKET_TIMEOUT,
                Template.VolleyRetryPolicy.RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        MyApplication.getInstance().addToReqQueue(mMultiPartRequest);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_menu_bar, menu);
        return true;

    }

    void setFile(int type, Uri uri) {
        mFile = new File(FileManager.getPath(getApplicationContext(), type, uri));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == Template.Code.FILE_MANAGER_CODE) {
                setFile(requestCode, data.getData());
                setView(requestCode, data.getData());
            } else {
                setFile(requestCode, mOutputUri);
                setView(requestCode, mOutputUri);
            }

        }
    }

    void setView(int type, Uri uri) {
        nama_sp.setText(mFile.getName());
        if (type == Template.Code.CAMERA_IMAGE_CODE) {
            mImage.setVisibility(ImageView.VISIBLE);
            mImage.setImageBitmap(BitmapFactory.decodeFile(FileManager.getPath(getApplicationContext(), type, uri)));
        } else {
            File file = new File(FileManager.getPath(getApplicationContext(), type, uri));
            int fileType = FileManager.fileType(file);
            if (fileType == Template.Code.CAMERA_IMAGE_CODE) {
                mImage.setVisibility(ImageView.VISIBLE);
                mImage.setImageBitmap(BitmapFactory.decodeFile(FileManager.getPath(getApplicationContext(), type, uri)));
            } else {
                mImage.setVisibility(ImageView.VISIBLE);
            }

        }
    }

    void setResponse(Object response, VolleyError error) {
        if (response == null) {
            mResponse.setText("Error\n" + error);
            Toast.makeText(getApplicationContext(), "Error" +error, Toast.LENGTH_LONG).show();
        } else {
            if (StringParser.getCode(response.toString()).equals(Template.Query.VALUE_CODE_SUCCESS))
                Toast.makeText(getApplicationContext(), "Succes" +StringParser.getMessage(response.toString()), Toast.LENGTH_LONG).show();
            else
            Toast.makeText(getApplicationContext(), "Error" +StringParser.getMessage(response.toString()), Toast.LENGTH_LONG).show();
        }
    }

    void savetoDB(){

    }



}
