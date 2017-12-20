package com.ngocthach.taskmanager.common;

import android.content.res.Resources;
import android.util.DisplayMetrics;

/**
 * ${CLASS}
 * Created by tryczson on 20/12/2017.
 */

public class UnitsConverter {

    public static float convertPixelsToDp(float px){
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float dp = px / (metrics.densityDpi / 160f);
        return Math.round(dp);
    }

    public static float convertDpToPixel(float dp){
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return Math.round(px);
    }
}
