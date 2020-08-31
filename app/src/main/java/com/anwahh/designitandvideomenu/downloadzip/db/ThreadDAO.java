package com.anwahh.designitandvideomenu.downloadzip.db;




import com.anwahh.designitandvideomenu.downloadzip.entities.ThreadInfo;

import java.util.List;

public interface ThreadDAO {
    /**
     * 插入线程信息
     *
     * @param threadInfo
     */
    void insertThread(ThreadInfo threadInfo);

    /**
     * 删除信息
     *
     * @param url
     * @param threadId
     */
    void deleteThread(String url, int threadId);

    /**
     * 更新信息
     *
     * @param url
     * @param threadId
     */
    void updateThread(String url, int threadId, int finished);

    /**
     * 查询信息
     *
     * @param url
     * @return
     */
    List<ThreadInfo> queryThread(String url);

    /**
     * 判断线程信息是否存在
     *
     * @param url
     * @param threadId
     * @return
     */
    boolean isExists(String url, int threadId);
}
