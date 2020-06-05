package com.paxx.app.ui.util;

import android.graphics.Typeface;
import android.util.SparseArray;

import com.paxx.app.MainApplication;

public class Gotham {
    private static SparseArray<Typeface> sTypefaces = new SparseArray();

    private static Typeface createTypeface(int paramInt) {
        switch (paramInt) {
            case 1:
                return Typeface.createFromAsset(MainApplication.getInstance().getAssets(), "Gotham_Bold.otf");
            case 2:
                return Typeface.createFromAsset(MainApplication.getInstance().getAssets(), "Gotham_Medium.otf");
            default:
                return null;
        }
    }

    public static Typeface getTypeface(int paramInt) {
        Typeface localTypeface = (Typeface) sTypefaces.get(paramInt);
        if (localTypeface == null) {
            localTypeface = createTypeface(paramInt);
            if (localTypeface != null) {
                sTypefaces.put(paramInt, localTypeface);
            }
        }
        return localTypeface;
    }
}


/* Location:              /Users/admin/Downloads/apktool-jdgui/apktool/dex2jar-0.0.9.15/classes_dex2jar.jar!/com/pax/app/util/Gotham.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */