package com.anwahh.designitandvideomenu.update.net;

import java.io.File;

/**
 * @describe 版本更新下载回调接口类
 * @author Anwahh
 * @date 2020-04-10
 */
public interface INetDownloadCallBack {

    void success(File apkFile);

    void progress(int progress);

    void failed(Throwable throwable);
}
