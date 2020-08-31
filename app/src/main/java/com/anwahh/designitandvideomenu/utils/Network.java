package com.anwahh.designitandvideomenu.utils;



import androidx.annotation.StringDef;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public interface Network {

    String UNKNOWN = "unknown";

    String WIFI = "wifi";

    String MOBILE_2G = "2G";

    String MOBILE_3G = "3G";

    String MOBILE_4G = "4G";

    String MOBILE_5G = "5G";

    @Retention(RetentionPolicy.CLASS)
    @Target({ElementType.PARAMETER, ElementType.FIELD, ElementType.METHOD})
    @StringDef({Network.UNKNOWN, Network.WIFI, Network.MOBILE_2G, Network.MOBILE_3G, Network.MOBILE_4G, Network.MOBILE_5G})
    @interface NetworkType{}

}
