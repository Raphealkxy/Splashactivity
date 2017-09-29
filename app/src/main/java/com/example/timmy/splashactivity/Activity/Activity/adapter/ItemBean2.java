package com.example.timmy.splashactivity.Activity.Activity.adapter;

/**
 * Created by Timmy on 2017/9/29.
 */

public class ItemBean2 {
    private int imgleftid;
    private int imrightid;
    private String  text;

    public ItemBean2(int imgleftid, int imrightid, String text) {
        this.imgleftid = imgleftid;
        this.imrightid = imrightid;
        this.text = text;
    }

    public int getImgleftid() {
        return imgleftid;
    }

    public void setImgleftid(int imgleftid) {
        this.imgleftid = imgleftid;
    }

    public int getImrightid() {
        return imrightid;
    }

    public void setImrightid(int imrightid) {
        this.imrightid = imrightid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
