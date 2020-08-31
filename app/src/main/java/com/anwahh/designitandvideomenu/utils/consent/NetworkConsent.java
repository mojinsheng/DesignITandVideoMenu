package com.anwahh.designitandvideomenu.utils.consent;



public abstract class NetworkConsent {


    /**
     * 渠道的地址
     */


    public static final String BASE_FMAPI_URL = "";

    //渠道SDK的初始化域名
    public static final String WT_CHANNL_INIT = BASE_FMAPI_URL+"android/login/sdkInit";
    //渠道SDK的微信登录域名
    public static final String WT_CHANNL_WX = BASE_FMAPI_URL+"app/login/weChat";
    //渠道SDK的QQ登录域名
    public static final String WT_CHANNL_QQ = BASE_FMAPI_URL+"app/login/qq";
    //渠道SDK的生成快速账号
    public static final String WT_CHANNL_QUICKREG = BASE_FMAPI_URL+"user/register/quickReg";
    //渠道SDK的快速登录域名
    public static final String WT_CHANNL_QUICK_LOGIN = BASE_FMAPI_URL+"user/login/quickLogin";
    //渠道获取手机验证码
    public static final String WT_CHANNL_VALIDATECODE = BASE_FMAPI_URL+"user/register/validateCode";
    //绑定手机
    public static final String WT_CHANNL_BIND_MOBILE = BASE_FMAPI_URL+"user/binding/mobile";

    //绑定手机
    public static final String WT_CHANNL_CHANGEMOBILE = BASE_FMAPI_URL+"user/binding/changeMobile";


    //手机注册
    public static final String WT_CHANNL_PHONE_LOGIN = BASE_FMAPI_URL+"user/register/mobileReg";

    //找回密码验证码
    public static final String WT_CHANNL_PHONE_PASSCODE = BASE_FMAPI_URL+"user/register/passCode";
    //修改密码
    public static final String WT_CHANNL_UPDATE_PASSCODE = BASE_FMAPI_URL+"user/binding/resetPass";
    //找回密码验证码
    public static final String WT_CHANNL_PASSCODE = BASE_FMAPI_URL+"user/binding/resetPsd";

    //检验实名认证接口
    public static final String WT_CHANNL_CHECKIDCARD = BASE_FMAPI_URL+"user/info/checkIdCard";


    //检验玩家是否绑定手机
    public static final String WT_CHANNL_CHECKBIND = BASE_FMAPI_URL+"user/binding/checkBind";



    //实名认证接口
    public static final String WT_CHECKIDCARD = BASE_FMAPI_URL+"user/info/idCard";

    //在线时长接口
    public static final String WT_CHECKAGE = BASE_FMAPI_URL+"user/limit/checkAge";


    //公告接口
    public static final String WT_ANNOUNCE= BASE_FMAPI_URL+"game/announce/info";

    //活动详情
    public static final String WT_ACTIVITY = BASE_FMAPI_URL+"game/activity/detailActive";

    //防沉迷充值接口
    public static final String WT_LIMITPAY = BASE_FMAPI_URL+"user/limit/limitPay";

    //支付
    public static final String WT_PAY= BASE_FMAPI_URL+"pay/jump/paysdk";
    //支付方式
    public static final String WT_PAY_TYPE= BASE_FMAPI_URL+"pay/app/index";

    //角色上报
    public static final String WT_ROLE = BASE_FMAPI_URL+"game/play/gameRole";




    //礼品
    public static final String WT_GAMEGIFT = BASE_FMAPI_URL+"game/gift/gameGift";

    //领取礼品
    public static final String WT_RECEIVECODE = BASE_FMAPI_URL+"game/gift/receiveCode";

    //个人中心新闻接口
    public static final String WT_GAMEMIX = BASE_FMAPI_URL+"game/announce/gameMix";

    public static final String PAY_URL="/v1/trade/trade";
    public static final String FLAG_PARAM_LINK = "&";
    public static final String PARAM_DATA_HEAD = "data=";
    public static final String PARAM_SIGN = "sign";
    public static final String PARAM_KEY = "fSDK!GA*&CKEY";

    public static final String HEAD_ACCEPT = "Accept-Encoding";

    public static final String GZIP = "gzip";

    public static final int READ_Timeout = 15000*3; //unit: milliseconds 15s
    public static final int CONN_Timeout = 15000*3; //unit: milliseconds 15s

    public static final int JM_OK_RESPONSE = 200;


    public static final String HTTP_POST = "POST";
    public static final String HTTP_GET = "GET";
    public static final String HTTP_PUT = "PUT";
    public static final String HTTP_DELETE = "DELETE";

    public static final String DECODE_MD5 = "MD5";




}
