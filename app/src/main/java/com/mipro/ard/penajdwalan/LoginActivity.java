package com.mipro.ard.penajdwalan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.mipro.ard.penajdwalan.json_handler.MyApplication;
import com.mipro.ard.penajdwalan.json_handler.parser;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    EditText nrp_login, pass_login;
    Button btn_login;
    public String get_nrp, get_pass;

    private boolean loggedin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        nrp_login = (EditText) findViewById(R.id.nrp_login);
        pass_login = (EditText) findViewById(R.id.pass_login);
        btn_login = (Button) findViewById(R.id.btn_login);

        btn_login.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        login();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences(parser.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        loggedin = sharedPreferences.getBoolean(parser.LOGGEDIN_SHARED_PREF, false);
        if (loggedin){
            Intent login = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(login);
        }

    }

    private void login(){
        get_nrp = nrp_login.getText().toString();
        get_pass = pass_login.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, parser.DATA_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Respon server", response);
                        if (response.equalsIgnoreCase(parser.LOGIN_SUCCESS)){
                            SharedPreferences sharedPreferences = LoginActivity.this.getSharedPreferences(parser.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean(parser.LOGGEDIN_SHARED_PREF, true);
                            editor.putString(parser.NRP_SHARED_PREF, get_nrp);
                            editor.commit();
                            
                            Intent login = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(login);
                        }else {
                            new MaterialDialog.Builder(LoginActivity.this)
                                    .title("Terjadi Kesalahan")
                                    .content("Persiksa Kembali Kombinasi NRP dan Password anda")
                                    .negativeText("SIAP")
                                    .backgroundColorRes(R.color.colorPrimary)
                                    .show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("nrp", get_nrp);
                params.put("password", get_pass);
                return params;


            }
        };
        Log.d("ISI USER", get_nrp +" "+ get_pass);
        MyApplication.getInstance().addToReqQueue(stringRequest);
    }

}
