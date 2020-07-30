package com.anwahh.designitandvideomenu.update.net;

/**
 * @describe 版本更新网络回调接口类
 * @author Anwahh
 * @date 2020-04-10
 */
public interface INetCallBack {

    void success(String response);

    void failed(Throwable throwable);
}
