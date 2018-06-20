package com.example.administrator.moblieplayer.baen;

/**
 * Created by Administrator on 2018/6/20.
 */

public class NetVideoBean {
    /**
     "vpic": "http://pic9.qiyipic.com/image/20150803/96/f9/v_109343020_m_601.jpg",
     "shortTitle": "航海王 第1集",
     "vurl": "http://www.iqiyi.com/v_19rrok4nt0.html",
     "timeLength": 1500,
     "vt": "我是路飞！ 将要成为海贼王的男人",
     */
    private String vpic;
    private String shortTitle;
    private String timeLength;
    private String vt;

    public String getVpic() {
        return vpic;
    }

    public void setVpic(String vpic) {
        this.vpic = vpic;
    }

    public String getShortTitle() {
        return shortTitle;
    }

    public void setShortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
    }

    public String getTimeLength() {
        return timeLength;
    }

    public void setTimeLength(String timeLength) {
        this.timeLength = timeLength;
    }

    public String getVt() {
        return vt;
    }

    public void setVt(String vt) {
        this.vt = vt;
    }

    @Override
    public String toString() {
        return "NetVideoBean{" +
                "vpic='" + vpic + '\'' +
                ", shortTitle='" + shortTitle + '\'' +
                ", timeLength='" + timeLength + '\'' +
                ", vt='" + vt + '\'' +
                '}';
    }
}
