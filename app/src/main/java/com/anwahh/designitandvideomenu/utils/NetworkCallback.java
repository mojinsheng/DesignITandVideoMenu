package com.anwahh.designitandvideomenu.utils;

//TODO DOC
//TODO OP2 internal
public interface NetworkCallback {

    void onSuccess(int code, String data);

    void onFailure(int code, String message);
}
