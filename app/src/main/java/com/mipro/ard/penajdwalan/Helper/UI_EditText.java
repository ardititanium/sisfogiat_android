package com.mipro.ard.penajdwalan.Helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

/**
 * Created by ard on 8/14/2016.
 */
public class UI_EditText extends AppCompatActivity{

    public void SingleLineDone(EditText et){
        et.setSingleLine();
        et.setImeOptions(EditorInfo.IME_ACTION_DONE);
        et.setHorizontallyScrolling(true);
    }

    public void SingleLineNext(EditText et){
        et.setSingleLine();
        et.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        et.setHorizontallyScrolling(true);
    }

    public void SingleLineGo(EditText et){
        et.setSingleLine();
        et.setImeOptions(EditorInfo.IME_ACTION_GO);
        et.setHorizontallyScrolling(true);
    }

    public void SingleLineDoneTv(TextView tv){
        tv.setSingleLine();
        tv.setImeOptions(EditorInfo.IME_ACTION_DONE);
        tv.setHorizontallyScrolling(true);
    }

    public void SingleLineNextTv(TextView tv){
        tv.setSingleLine();
        tv.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        tv.setHorizontallyScrolling(true);
    }

    public void SingleLineGoTv(TextView tv){
        tv.setSingleLine();
        tv.setImeOptions(EditorInfo.IME_ACTION_GO);
        tv.setHorizontallyScrolling(true);
    }

    public void disableImageBtn(ImageButton btn){
        btn.setEnabled(false);
        btn.setClickable(false);
    }

    public void disabeBtn(Button btn){
        btn.setEnabled(false);
        btn.setClickable(false);
    }

    public void dialogHapus(final Context context, final Class namaClass){
        new MaterialDialog.Builder(context)
                .content("Anda Yakin?")
                .positiveText("YA")
                .negativeText("BATAL")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Intent intentYa = new Intent(context, namaClass);
                        startActivity(intentYa);
                        finish();
                    }
                })
                .show();
    }

}
