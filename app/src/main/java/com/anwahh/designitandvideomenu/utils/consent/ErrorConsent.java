package com.anwahh.designitandvideomenu.utils.consent;



public abstract class ErrorConsent {

    //No Error
    public static final int SUCCESS = 200;
    public static final int CLIENT_ERROR_UNDEFINE = 20911;// This error happen if error code in UserInfo haven't init
    public static final int UN_CATCH_EXCEPTION = 20119;

    //Integration Error
    public static final int INIT_MISS = 20101;
    public static final int KEY_INVALIDATE = 20102;
    public static final int LOGIN_MISS = 20103;

    //Client Error
    public static final int CLIENT_ERROR = 20600;
    public static final int URL_BUILD_EXCEPTION = 20602;
    public static final int URL_CONNECTION_OPEN_EXCEPTION = 20610;
    public static final int URL_CONNECTION_CONNECT_EXCEPTION = 20611;
    public static final int RESPONSE_READ_EXCEPTION = 20612;
    public static final int RESPONSE_EMPTY = 20613;
    public static final int HTTP_METHOD_SET_EXCEPTION = 20615;
    public static final int EMPTY_DOMAIN = 20620;
    public static final int PARSE_JSON_EXCEPTION = 20631;
    public static final int PERMISSION_MISS = 20641;
    public static final int PASSED_ILLEGAL_PAY_INFO = 20666;
    public static final int NOT_IN_UI_THREAD = 20676;
    public static final int MULTI_CALLS = 20677;
    public static final int INIT_FAILURE = 20678;
    public static final int LOGOUT_FAILURE = 20679;


    //Store Error
    public static final int STORE_ERROR = 20500; //use this error if store didn't return a error code
    public static final int STORE_LOGIN_ERROR = 20510; //store gives success response but code is not success code
    public static final int STORE_LOGIN_EXCEPTION = 20511; // exception happen inside store
    public static final int STORE_LOGIN_FAILURE = 20512; //store gives failure response
    public static final int STORE_LOGIN_CANCEL = 20513;
    public static final int STORE_PAY_FAILURE = 20530;
    public static final int STORE_PAY_CANCEL = 20533;
    public static final int STORE_EXIT_CANCEL = 20563;

    //Server Error usually could get directly from JM service


}
