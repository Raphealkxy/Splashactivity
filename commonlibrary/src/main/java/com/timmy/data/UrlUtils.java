package com.timmy.data;

/**
 * Created by Timmy on 2017/9/27.
 */

public class UrlUtils {


    /**
     * 访问链接汇总
     */

    /**
     * 服务器访问站点
     */
    public final static  String NET="http://192.168.253.1:8080/AMSFull/";
    /**
     * 登录
     */

    public final static  String NET_LOGIN=NET+"action_CheckLogin";
    /**
     * 注册基本信息
     */
    public final  static  String NET_REGISTER=NET+"action_getdata";

    /**
     * 注册人脸
     */
    public final  static  String NET_REGISTERFACE=NET+"action_upload";


    /**
     * 修改个人密码
     */
    public final  static  String NET_MODIFIEDPASSWORD=NET+"action_UpdatePassword";

    /**
     * 修改个人信息
     */
    public final  static  String NET_MODIFIEDINFO=NET+"action_UpdateInfo";

/**
 * 出勤信息获取按班级
 */

    public final static  String NET_GETUSERLIST=NET+"action_getuserlist";
    /**
     * 出勤信息获取按个人
     */


    /**
     * 出勤信息获取按。。。
     */


}
