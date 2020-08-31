package com.anwahh.designitandvideomenu.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


//TODO DOC whole class
public abstract class ResourceUtils {
    /**
     * This method will be removed when we support all resource types
     * Get Identifier for named resource
     * @param activity context
     * @param name resource name
     * @param defType resource name type, check here for available types
     *                https://developer.android.com/guide/topics/resources/available-resources.html?hl=es
     * @return resource id in real APK R.java,return 0 if id not found or params invalid
     */
    @Deprecated
    public static int getId(Activity activity, String name, String defType){
        if(activity == null) return 0;
        else return activity.getResources().getIdentifier(name, defType, activity.getPackageName());
    }
    public static int getColor(Activity activity, String name){
        if(activity != null) {
            int id = activity.getResources().getIdentifier(name, "color", activity.getPackageName());
            return (id == 0) ? 0 : activity.getResources().getColor(id);
        }else {
            return 0;
        }
    }

    public static float getDimension(Activity activity, String name){
        if(activity != null) {
            int id = activity.getResources().getIdentifier(name, "dimen", activity.getPackageName());
            return (id == 0) ? 0 : activity.getResources().getDimension(id);
        }else {
            return 0;
        }
    }

    @NonNull
    public static String getString(Activity activity, String name){
        if(activity != null) {
            int id = activity.getResources().getIdentifier(name, "string", activity.getPackageName());
            return (id == 0) ? "" : activity.getResources().getString(id);
        }else {
            return "";
        }
    }

    @NonNull
    public static String getString(Context context, String name){
        if(context != null) {
            int id = context.getResources().getIdentifier(name, "string", context.getPackageName());
            return (id == 0) ? "" : context.getResources().getString(id);
        }else {
            return "";
        }
    }

    @NonNull
    public static int[] getIntArray(Activity activity, String name){
        if(activity != null) {
            int id = activity.getResources().getIdentifier(name, "array", activity.getPackageName());
            return (id == 0) ? new int[0] : activity.getResources().getIntArray(id);
        }else {
            return new int[0];
        }
    }

    @NonNull
    public static String[] getStringArray(Activity activity, String name){
        if(activity != null) {
            int id = activity.getResources().getIdentifier(name, "array", activity.getPackageName());
            return (id == 0) ? new String[0] : activity.getResources().getStringArray(id);
        }else {
            return new String[0];
        }
    }

    @Nullable
    public static Boolean getBoolean(Activity activity, String name){
        if(activity != null) {
            int id = activity.getResources().getIdentifier(name, "bool", activity.getPackageName());
            return (id == 0) ? null : activity.getResources().getBoolean(id);
        }else {
            return null;
        }
    }

    @Nullable
    public static Integer getInteger(Activity activity, String name){
        if(activity != null) {
            int id = activity.getResources().getIdentifier(name, "integer", activity.getPackageName());
            return (id == 0) ? null : activity.getResources().getInteger(id);
        }else {
            return null;
        }
    }

    @Nullable
    public static AssetManager getAsset(Activity activity){
        if(activity != null) {
            return activity.getResources().getAssets();
        }else {
            return null;
        }
    }

    @Nullable
    public static XmlResourceParser getAnim(Activity activity, String name){
        if(activity != null) {
            int id = activity.getResources().getIdentifier(name, "anim", activity.getPackageName());
            return (id == 0) ? null : activity.getResources().getAnimation(id);
        }else {
            return null;
        }
    }

    @Nullable
    public static Drawable getDrawable(Activity activity, String name){
        if(activity != null) {
            int id = activity.getResources().getIdentifier(name, "drawable", activity.getPackageName());
            return (id == 0) ? null : activity.getResources().getDrawable(id);
        }else {
            return null;
        }
    }

    @Nullable
    public static XmlResourceParser getLayout(Activity activity, String name){
        if(activity != null) {
            int id = activity.getResources().getIdentifier(name, "layout", activity.getPackageName());
            return (id == 0) ? null : activity.getResources().getLayout(id);
        }else {
            return null;
        }
    }

    @Nullable
    public static int getLayoutIdByName(Activity activity, String resourcesName)
    {
        return getId(activity, resourcesName, "layout");
    }
    @Nullable
    public static int getColorIdByName(Activity activity, String resourcesName)
    {
        return getId(activity, resourcesName, "color");
    }
    @Nullable
    public static int getArrayIdByName(Activity activity, String resourcesName)
    {
        return getId(activity, resourcesName, "array");
    }
    @Nullable
    public static int getStringIdByName(Activity activity, String resourcesName)
    {
        return getId(activity, resourcesName, "string");
    }
    @Nullable
    public static String getStringByName(Activity activity, String resourcesName)
    {
        return activity.getResources().getString(getId(activity, resourcesName, "string"));
    }
    @Nullable
    public static int getViewIdByName(Activity activity, String resourcesName)
    {
        return getId(activity, resourcesName, "id");
    }
    @Nullable
    public static int getDrawableIdByName(Activity activity, String resourcesName)
    {
        return getId(activity, resourcesName, "drawable");
    }
    @Nullable
    public static int getMipmapIdByName(Activity activity, String resourcesName)
    {
        return getId(activity, resourcesName, "mipmap");
    }
    @Nullable
    public static int getAnimIdByName(Activity activity, String resourcesName)
    {
        return getId(activity, resourcesName, "anim");
    }
    @Nullable
    public static int getStyleIdByName(Activity activity, String resourcesName)
    {
        return getId(activity, resourcesName, "style");
    }
}
