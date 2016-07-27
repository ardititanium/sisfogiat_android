package com.mipro.ard.penajdwalan.fileUpload.Utils;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.mipro.ard.penajdwalan.fileUpload.template.EndpointAPI;
import com.mipro.ard.penajdwalan.fileUpload.template.Template;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.entity.mime.MultipartEntityBuilder;
import cz.msebera.android.httpclient.entity.mime.content.FileBody;

/**
 * Created by ard on 7/27/2016.
 */
public class MultiPartRequest extends Request<String> {
    private Response.Listener<String> mListener;
    private HttpEntity mHttpEntity;



    public MultiPartRequest(Response.ErrorListener errorListener, Response.Listener listener, File file){
        super(Method.POST, EndpointAPI.URLSURAT, errorListener);
        mListener = listener;
        mHttpEntity = buildMultipartEntity(file);
    }

    private HttpEntity buildMultipartEntity(File file){
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        FileBody fileBody = new FileBody(file);
        builder.addPart(Template.Query.KEY_IMAGE, fileBody);
        builder.addTextBody(Template.Query.KEY_DIRECTORY, Template.Query.VALUE_DIRECTORY);
        return builder.build();
    }


    @Override
    public String getBodyContentType() {
        return mHttpEntity.getContentType().getValue();
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            mHttpEntity.writeTo(bos);
            return bos.toByteArray();
        } catch (IOException e) {
            VolleyLog.e("" + e);
            return null;
        }catch (OutOfMemoryError e){
            VolleyLog.e("" + e);
            return null;
        }
    }






    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        try {
            return Response.success(new String(response.data, "UTF-8"),
                    getCacheEntry());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return Response.success(new String(response.data),
                    getCacheEntry());
        }
    }

    @Override
    protected void deliverResponse(String response) {
        mListener.onResponse(response);
    }
}
