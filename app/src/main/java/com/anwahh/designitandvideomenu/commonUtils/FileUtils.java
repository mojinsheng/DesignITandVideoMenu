package com.anwahh.designitandvideomenu.commonUtils;

/**
 * @describe 文件工具类
 * @author Anwahh
 * @date 2020-04-10
 */

import android.os.Environment;
import android.util.Log;

import java.io.File;

public class FileUtils {

    /**
     * 检查SDCard存在并且可以读写
     * @return
     */
    public static boolean isSDCardState() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 指定主页视频存放路径
     * @return
     */
    public static String DirPathForMainVideo() {
        if (isSDCardState()) {
            return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/mainVideo";
        } else {
            return Environment.getExternalStorageDirectory().getAbsolutePath() + "/mainVideo";
        }
    }

    /**
     * 指定封面图片存放路径
     * @return
     */
    public static String DirPathForThumb() {
        if (isSDCardState()) {
            return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/Thumb";
        } else {
            return Environment.getExternalStorageDirectory().getAbsolutePath() + "/Thumb";
        }
    }

    /**
     * 指定图片存放路径
     * @return
     */
    public static String DirPathForPhoto() {
        if (isSDCardState()) {
            return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/Photo";
        } else {
            return Environment.getExternalStorageDirectory().getAbsolutePath() + "/Photo";
        }
    }

    /**
     * 指定视频存放路径
     * @return
     */
    public static String DirPathForVideo() {
        if (isSDCardState()) {
            return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/Video";
        } else {
            return Environment.getExternalStorageDirectory().getAbsolutePath() + "/Video";
        }
    }

    /**
     * 指定长图片存放路径
     * @return
     */
    public static String DirPathForLongPhoto() {
        if (isSDCardState()) {
            return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/LongPhoto";
        } else {
            return Environment.getExternalStorageDirectory().getAbsolutePath() + "/LongPhoto";
        }
    }

    /**
     * 指定活动图片存放路径
     * @return
     */
    public static String DirPathForManoeuvre() {
        if (isSDCardState()) {
            return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/Manoeuvre";
        } else {
            return Environment.getExternalStorageDirectory().getAbsolutePath() + "/Manoeuvre";
        }
    }

    /**
     * 指定买家秀图片存放路径
     * @return
     */
    public static String DirPathForCustomerShow() {
        if (isSDCardState()) {
            return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/CustomerShow";
        } else {
            return Environment.getExternalStorageDirectory().getAbsolutePath() + "/CustomerShow";
        }
    }

    /**
     * 指定培训图片存放路径
     * @return
     */
    public static String DirPathForTrain() {
        if (isSDCardState()) {
            return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/Train";
        } else {
            return Environment.getExternalStorageDirectory().getAbsolutePath() + "/Train";
        }
    }

    /**
     *  新建目录
     * @param path  目录的绝对路径
     * @return 创建成功则返回true
     */
    public static boolean creatFolder(String path) {
        File file = new File(path);
        if(!file.exists()) {
            if(file.mkdir()) {
                return true;
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * 获取某个路径下的文件列表
     * @param path 文件路径
     * @return 文件列表 File[] files
     */
    public static File[] getFileList(String path) {
        File file = new File(path);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
             for(int i=0;i<files.length;i++){
                String fileName= files[i].getName();
                if(fileName.equals("Thumbs.db")){
                    files[i].delete();
                }
             }
            File[] filess = file.listFiles();
            if (filess != null) {
                return filess;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * 批量创建图片文件夹
     * @param num 创建文件夹的数量
     * @return 创建成功则返回true
     */
    public static boolean createFolderAutoForPhoto(int num) {
        FileUtils.creatFolder(FileUtils.DirPathForPhoto());
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + File.separator + "Photo" + File.separator;
        for (int i = 0; i < num; i++) {
            String string = "photo" + i + "/";
            path += string;
            Log.d("createFolderForPhoto", "正在创建第" + i + "个文件夹");
            creatFolder(path);

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + File.separator + "Photo" + File.separator;
        }
        return true;
    }

    /**
     * 批量创建视频文件夹
     * @param num 创建文件夹的数量
     * @return 创建成功则返回true
     */
    public static boolean createFolderAutoForVideo(int num) {
        FileUtils.creatFolder(FileUtils.DirPathForVideo());
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + File.separator + "Video" + File.separator;
        for (int i = 0; i < num; i++) {
            String string = "video" + i + "/";
            path += string;
            Log.d("createFolderForVideo", "正在创建第" + i + "个文件夹");
            creatFolder(path);

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + File.separator + "Video" + File.separator;
        }
        return true;
    }
}