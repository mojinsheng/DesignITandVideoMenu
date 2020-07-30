package com.anwahh.designitandvideomenu.bean;

/**
 * @describe 多媒体文件的实体类
 * @author Anwahh
 * @date 2020-04-10
 */
public class MediaBean {

    private String thumbId; // ID
    private String trainId; // ID
    private String name; // 文件名字
    private String poster; // 封面图
    private String path; // 视频路径
    private String photo; // 图片
    private String longphoto; // 长图
    private String pone; // p1视频
    private String ptwo; // p1视频
    private String pthree; // p1视频
    private String pfour; // p1视频
    private String pfive; // p1视频
    private String tone; // p1视频

    public String getThumbId() {
        return thumbId;
    }

    public void setThumbId(String thumbId) {
        this.thumbId = thumbId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getLongphoto() {
        return longphoto;
    }

    public void setLongphoto(String longphoto) {
        this.longphoto = longphoto;
    }

    public String getPone() {
        return pone;
    }

    public void setPone(String pone) {
        this.pone = pone;
    }

    public String getPtwo() {
        return ptwo;
    }

    public void setPtwo(String ptwo) {
        this.ptwo = ptwo;
    }

    public String getPthree() {
        return pthree;
    }

    public void setPthree(String pthree) {
        this.pthree = pthree;
    }

    public String getPfour() {
        return pfour;
    }

    public void setPfour(String pfour) {
        this.pfour = pfour;
    }

    public String getPfive() {
        return pfive;
    }

    public void setPfive(String pfive) {
        this.pfive = pfive;
    }

    public String getTrainId() {
        return trainId;
    }

    public void setTrainId(String trainId) {
        this.trainId = trainId;
    }

    public String getTone() {
        return tone;
    }

    public void setTone(String tone) {
        this.tone = tone;
    }

    @Override
    public String toString() {
        return "MediaBean [thumbId =" + thumbId + ", name=" + name + ", poster=" + poster + ", path=" + path + ", photo=" + photo + ", longphoto=" + longphoto + "]";
    }

}
