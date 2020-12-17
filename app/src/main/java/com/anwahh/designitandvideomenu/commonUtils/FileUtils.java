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
    private static String pictrue1=Environment.DIRECTORY_PICTURES;

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
            return Environment.getExternalStorageDirectory()
                    .getAbsolutePath()+ File.separator +"Pictures"+ "/mainVideo";
        } else {
            return Environment.getExternalStorageDirectory().getAbsolutePath() + "/mainVideo";
        }
    }


    /**
     * 指定产品图片存放路径
     * @return
     */
    public static String DirPathForProjectPic() {
        if (isSDCardState()) {
            return Environment.getExternalStoragePublicDirectory(pictrue1).getAbsolutePath() + "/Thumb";
        } else {
            return Environment.getExternalStorageDirectory().getAbsolutePath() + "/Thumb";
        }
    }

    /**
     * 指定培训图片存放路径
     * @return
     */
    public static String DirPathForTrainPic() {
        if (isSDCardState()) {
            return Environment.getExternalStoragePublicDirectory(pictrue1).getAbsolutePath() + "/Train";
        } else {
            return Environment.getExternalStorageDirectory().getAbsolutePath() + "/Train";
        }
    }




    /**
     * 指定产品详情图片、视频存放路径
     * @return
     */
    public static String DirPathForProjectPicVideo() {
        if (isSDCardState()) {
            return Environment.getExternalStoragePublicDirectory(pictrue1).getAbsolutePath() + "/projectVideo";
        } else {
            return Environment.getExternalStorageDirectory().getAbsolutePath() + "/projectVideo";
        }
    }


    /**
     * 指定封面图片存放路径
     * @return
     */
    public static String DirPathForThumb() {
        if (isSDCardState()) {
            return Environment.getExternalStoragePublicDirectory(pictrue1).getAbsolutePath() + "/Thumb";
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
            return Environment.getExternalStoragePublicDirectory(pictrue1).getAbsolutePath() + "/Photo";
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
            return Environment.getExternalStoragePublicDirectory(pictrue1).getAbsolutePath() + "/Video";
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
            return Environment.getExternalStoragePublicDirectory(pictrue1).getAbsolutePath() + "/LongPhoto";
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
            return Environment.getExternalStoragePublicDirectory(pictrue1).getAbsolutePath() + "/Manoeuvre";
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
            return Environment.getExternalStoragePublicDirectory(pictrue1).getAbsolutePath() + "/Live";
        } else {
            return Environment.getExternalStorageDirectory().getAbsolutePath() + "/Live";
        }
    }

    /**
     * 指定产品头尾的路径
     * @return
     */
    public static String projectSet() {
        if (isSDCardState()) {
            return Environment.getExternalStoragePublicDirectory(pictrue1).getAbsolutePath() + "/projectSet";
        } else {
            return Environment.getExternalStorageDirectory().getAbsolutePath() + "/projectSet";
        }
    }

    /**
     * 指定培训头尾的路径
     * @return
     */
    public static String trainSet() {
        if (isSDCardState()) {
            return Environment.getExternalStoragePublicDirectory(pictrue1).getAbsolutePath() + "/trainSet";
        } else {
            return Environment.getExternalStorageDirectory().getAbsolutePath() + "/trainSet";
        }
    }

    /**
     * 指定培训图片存放路径
     * @return
     */
    public static String DirPathForTrain() {
        if (isSDCardState()) {
            return Environment.getExternalStoragePublicDirectory(pictrue1).getAbsolutePath() + "/Train";
        } else {
            return Environment.getExternalStorageDirectory().getAbsolutePath() + "/Train";
        }
    }
    /**
     * 指定培训详情的视频存放路径
     * @return
     */
    public static String DirPathForTrainVideo() {
        if (isSDCardState()) {
            return Environment.getExternalStoragePublicDirectory(pictrue1).getAbsolutePath() + "/trainVideo";
        } else {
            return Environment.getExternalStorageDirectory().getAbsolutePath() + "/trainVideo";
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
        String path = Environment.getExternalStoragePublicDirectory(pictrue1).getAbsolutePath() + File.separator + "Photo" + File.separator;
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

            path = Environment.getExternalStoragePublicDirectory(pictrue1).getAbsolutePath() + File.separator + "Photo" + File.separator;
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
        String path = Environment.getExternalStoragePublicDirectory(pictrue1).getAbsolutePath() + File.separator + "Video" + File.separator;
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

            path = Environment.getExternalStoragePublicDirectory(pictrue1).getAbsolutePath() + File.separator + "Video" + File.separator;
        }
        return true;
    }

    /**
     * 删除单个文件
     * @param   filePath    被删除文件的文件名
     * @return 文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.isFile() && file.exists()) {
            return file.delete();
        }
        return false;
    }

    /**
     * 删除文件夹以及目录下的文件
     * @param   filePath 被删除目录的文件路径
     * @return  目录删除成功返回true，否则返回false
     */
    public static boolean deleteDirectory(String filePath) {
        boolean flag = false;
        //如果filePath不以文件分隔符结尾，自动添加文件分隔符
        if (!filePath.endsWith(File.separator)) {
            filePath = filePath + File.separator;
        }
        File dirFile = new File(filePath);
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        flag = true;
        File[] files = dirFile.listFiles();
        //遍历删除文件夹下的所有文件(包括子目录)
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                //删除子文件
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag) break;
            } else {
                //删除子目录
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag) break;
            }
        }
        if (!flag) return false;
        //删除当前空目录
        return dirFile.delete();
    }

    /**
     *  根据路径删除指定的目录或文件，无论存在与否
     *@param filePath  要删除的目录或文件
     *@return 删除成功返回 true，否则返回 false。
     */
    public static boolean DeleteFolder(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            return false;
        } else {
            if (file.isFile()) {
                // 为文件时调用删除文件方法
                return deleteFile(filePath);
            } else {
                // 为目录时调用删除目录方法
                return deleteDirectory(filePath);
            }
        }
    }

}