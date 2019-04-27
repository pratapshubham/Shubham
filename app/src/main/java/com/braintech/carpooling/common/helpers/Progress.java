package com.braintech.carpooling.common.helpers;


import android.app.ProgressDialog;
import android.content.Context;
import android.view.WindowManager;

import com.braintech.carpooling.R;


public class Progress {
    //
    static ProgressDialog dialog;

    public static void start(Context context) {
        if (dialog != null)
            dialog.dismiss();

        dialog = new ProgressDialog(context, ProgressDialog.STYLE_HORIZONTAL);

        try {
            dialog.show();
        } catch (WindowManager.BadTokenException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }

        dialog.setIndeterminate(true);


        dialog.setContentView(R.layout.progressdialog);

        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);

    }

    public static void stop() {
        try {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        }catch (IllegalArgumentException ex)
        {
            ex.printStackTrace();
        }
    }
}
