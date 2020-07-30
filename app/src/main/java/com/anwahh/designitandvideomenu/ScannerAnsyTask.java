package com.anwahh.designitandvideomenu;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.anwahh.designitandvideomenu.bean.MediaBean;
import com.anwahh.designitandvideomenu.commonUtils.FileUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @describe 异步扫描类
 * @author Anwahh
 * @date 2020-04-10
 */
public class ScannerAnsyTask extends AsyncTask<Void, Integer, List<MediaBean>> {

    /**
     * 媒体列表类
     */
    private List<MediaBean> mMediaBeanList = new ArrayList<MediaBean>();
    /**
     * 依附于某个Activity
     * 因为AsyncTask要在UI线程中执行
     */
    private Activity mActivity;
    /**
     * 待加载的ProgressBar
     */
    private ProgressBar mProgressBar;
    /**
     * 打印Log时所用的TAG
     */
    private static final String TAG = "VideoMenuActivity";

    public ScannerAnsyTask() {
        super();
    }

    public ScannerAnsyTask(Activity activity, ProgressBar progressBar) {
        super();
        this.mActivity = activity;
        this.mProgressBar = progressBar;
    }

    @Override
    protected List<MediaBean> doInBackground(Void... voids) {
        mMediaBeanList = getFileFromJson(mMediaBeanList, Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/DataSource.json");
        Log.d(TAG,"---ScannerAnsyTask---");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mMediaBeanList;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(List<MediaBean> mediaBeans) {
        super.onPostExecute(mediaBeans);
        Log.d(TAG, "---ScannerAnsyTask---View.GONE---");
        mProgressBar.setVisibility(View.GONE);
    }

    private List<MediaBean> getFileFromJson(List<MediaBean> list, String path) {
        try {
            FileInputStream fileInputStream = new FileInputStream(path);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            StringBuilder builder = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                builder.append(line);
            }
            bufferedReader.close();
            inputStreamReader.close();
            JSONObject jsonObject = new JSONObject(builder.toString());

            JSONArray array = jsonObject.getJSONArray("thumb");

            for (int i = 0; i < array.length(); i++) {
                JSONObject thumb = array.getJSONObject(i);
//                StringBuilder s = new StringBuilder();
//                s.append(thumb.getString("thumbId") + "   ");
//                s.append(thumb.getString("name") + "   ");
//                s.append(thumb.getString("poster") + "   ");
//                s.append(thumb.getString("path") + "   ");
//                s.append(thumb.getString("photo") + "   ");
//                s.append(thumb.getString("longphoto"));
//                s.append(thumb.getString("p1"));
//
//                Log.d(TAG, "result" + s.toString());
                MediaBean file = new MediaBean();
                    file.setName(thumb.getString("name"));
                    file.setPoster(thumb.getString("poster"));
                    file.setTrainId(thumb.getString("train"));
                    file.setTone(thumb.getString("t1"));
                    file.setPath(thumb.getString("path"));
                    file.setPhoto(thumb.getString("photo"));
                    file.setLongphoto(thumb.getString("longphoto"));
                    file.setPone(thumb.getString("p1"));
                    file.setPtwo(thumb.getString("p2"));
                    file.setPthree(thumb.getString("p3"));
                    file.setPfour(thumb.getString("p4"));
                    file.setPfive(thumb.getString("p5"));

                list.add(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
