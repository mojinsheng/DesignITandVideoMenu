package com.anwahh.designitandvideomenu.update.net;

import java.io.File;

/**
 * @describe 版本更新网络接口类
 * @author Anwahh
 * @date 2020-04-10
 */
public interface INetManager {

    void get(String url, INetCallBack callBack, Object tag);

    void download(String url, File targetFile, INetDownloadCallBack callBack, Object tag);

    void cancel(Object tag);
}
