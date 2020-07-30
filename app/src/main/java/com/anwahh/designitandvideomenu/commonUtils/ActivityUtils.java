package com.anwahh.designitandvideomenu.commonUtils;

/**
 * @describe 服务判断类
 * @author Anwahh
 * @date 2020-04-10
 */
import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

public class ActivityUtils {
    /**
     * 判断某个服务是否正在运行的方法
     * @param mContext
     * @param serviceName 包名+服务的类名(例如 : net.loonggg.testbackstage.TestService)
     * @return true表示正在运行 , false表示服务当前没有在运行
     */

    public static boolean isServiceWork(Context mContext, String serviceName) {

        boolean isWork = false;

        ActivityManager myAM = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);

        List<ActivityManager.RunningServiceInfo> myList = myAM.getRunningServices(40);

        if(myList.size() <= 0) {
            return false;
        }
        for(int i = 0; i < myList.size(); i++) {
            String mName = myList.get(i).service.getClassName().toString();
            if(mName.equals(serviceName)) {
                isWork = true;
                break;
            }
        }
        return isWork;
    }

}
