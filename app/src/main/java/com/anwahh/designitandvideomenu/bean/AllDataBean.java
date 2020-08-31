package com.anwahh.designitandvideomenu.bean;

import java.util.List;

public class AllDataBean {
    private List<MainBean> main;

    private List<LiveBean> live;
    private List<ActiveBean> active;

    public List<MainBean> getMain() {
        return main;
    }

    public void setMain(List<MainBean> main) {
        this.main = main;
    }

    public List<LiveBean> getLive() {
        return live;
    }

    public void setLive(List<LiveBean> live) {
        this.live = live;
    }

    public List<ActiveBean> getActive() {
        return active;
    }

    public void setActive(List<ActiveBean> active) {
        this.active = active;
    }
}
