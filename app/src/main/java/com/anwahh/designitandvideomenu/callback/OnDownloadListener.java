package com.anwahh.designitandvideomenu.callback;

public interface OnDownloadListener {
    void onUpdate(int totalSize, int currentSize, int speed, int percent);

    void onFinish(String downloadUrl, String filepath);
}
