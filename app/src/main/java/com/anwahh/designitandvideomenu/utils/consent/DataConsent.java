package com.anwahh.designitandvideomenu.utils.consent;


import androidx.annotation.RestrictTo;

/**
 * {@link DataConsent} storage all the json name used in FSDK
 * Please always use Strings provide by this class to parse Json
 *
 * The Naming rule for static variables in this class is:
 * FEATURE_NAME_WHATEVER_JSON_FirstVariable_SecondVariable_.....
 */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
public abstract class DataConsent {

    public final static String USER_MAP_TOKEN = "token";
    public final static String USER_MAP_SESSION_ID = "sessionId";
    public final static String USER_MAP_UID = "uid";


}
