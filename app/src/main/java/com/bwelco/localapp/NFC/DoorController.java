package com.bwelco.localapp.NFC;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.bwelco.localapp.socket.ActionModel;
import com.bwelco.localapp.socket.Actions;
import com.google.gson.Gson;

import java.io.OutputStream;
import java.net.Socket;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by bwelco on 2017/5/1.
 */

public class DoorController {

    public static void doAction(final Context context, final boolean openDoor) {
        final ProgressDialog progressDialog = new ProgressDialog(context);

        Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                subscriber.onStart();
                try {
                    Thread.sleep(1000);
                    Log.i("admin", "tag found");
                    Socket socket = new Socket("192.168.10.189", 1024);
                    OutputStream os = socket.getOutputStream();
                    ActionModel actionModel = new ActionModel();
                    actionModel.action = openDoor ? Actions.OPEN_DOOR : Actions.CLOSE_DOOR;
                    os.write(new Gson().toJson(actionModel).getBytes());
                    Log.i("admin", "writen");
                    socket.close();
                    subscriber.onCompleted();
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }

            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Boolean>() {

                    @Override
                    public void onStart() {
                        super.onStart();
                        progressDialog.setMessage(openDoor ? "正在开锁中" : "正在关闭中");
                        progressDialog.show();
                    }

                    @Override
                    public void onCompleted() {
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {

                    }
                });
    }
}
