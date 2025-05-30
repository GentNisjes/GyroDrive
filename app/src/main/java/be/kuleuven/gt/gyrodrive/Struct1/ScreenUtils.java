package be.kuleuven.gt.gyrodrive.Struct1;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

public class ScreenUtils {

    private static final String TAG = "ScreenUtils";

    public static void logScreenSize(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (windowManager != null) {
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);
            int screenWidth = displayMetrics.widthPixels;
            int screenHeight = displayMetrics.heightPixels;
            Log.d(TAG, "Screen width: " + screenWidth + " pixels");
            Log.d(TAG, "Screen height: " + screenHeight + " pixels");
        } else {
            Log.e(TAG, "WindowManager is null");
        }
    }
}


