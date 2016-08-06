package com.mipro.ard.penajdwalan.json_handler;

import com.android.volley.Request;

import java.util.ArrayList;

/**
 * Created by ard on 8/7/2016.
 */
public class MyCommand<T> {
    ArrayList<Request<T>> requestsList = new ArrayList<>();


    public MyCommand(){

    }

    public void add(Request<T> request){
        requestsList.add(request);
    }

    public void remove(Request<T> request){
        requestsList.remove(request);
    }

    public void execute(){
        for (Request<T> request : requestsList){
            MyApplication.getInstance().addToReqQueue(request);
        }
    }
}
