package com.braintech.carpooling.common.helpers;

import android.app.Activity;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

import com.braintech.carpooling.common.interfaces.SnakeOnClick;


public class SnackNotifyHelper {

    public static void checkConnection(Activity activity, final SnakeOnClick snakeOnClick, CoordinatorLayout coordinatorLayout) {

        try {
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "No internet connection!", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Retry", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //snakeOnClick.onRetryClick();
                        }
                    });

            // Changing message text color
            snackbar.setActionTextColor(Color.RED);

            // Changing action button text color
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            // Fonts.robotoRegular(activity, textView);
            textView.setTextColor(Color.YELLOW);
            snackbar.show();
        }
        catch(NullPointerException ex)
        {
            ex.printStackTrace();
        }
    }

    public static void showSnakeBarForBackPress(Activity activity, CoordinatorLayout coordinatorLayout) {
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, "Please click BACK again to exit.", Snackbar.LENGTH_LONG);

        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        // Fonts.robotoRegular(activity, textView);
        textView.setTextColor(Color.YELLOW);
        snackbar.show();

    }

    public static void showMessage(Activity activity, String msg, CoordinatorLayout coordinatorLayout) {
       try {
           Snackbar snackbar = Snackbar
                   .make(coordinatorLayout, msg, Snackbar.LENGTH_LONG);

           // Changing action button text color
           View sbView = snackbar.getView();
           TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
           // Fonts.robotoRegular(activity, textView);
           textView.setTextColor(Color.YELLOW);
           snackbar.show();
       }
       catch (NullPointerException ex)
       {
           ex.printStackTrace();
       }

    }


}
