package com.anwahh.designitandvideomenu.update;

import com.anwahh.designitandvideomenu.update.net.INetManager;
import com.anwahh.designitandvideomenu.update.net.OkHttpNetManager;

/**
 * @describe 版本更新基类
 * @author Anwahh
 * @date 2020-04-10
 */
public class AppUpdater {

    private static AppUpdater sInstance = new AppUpdater();

    private INetManager mNetManager = new OkHttpNetManager();

    public INetManager getNetManager() {
        return mNetManager;
    }

    public static AppUpdater getInstance() {
        return sInstance;
    }
}
