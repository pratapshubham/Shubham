package com.braintech.carpooling.common.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;

import com.braintech.carpooling.R;
import com.braintech.carpooling.common.interfaces.OnClickInterface;


public class AlertDialogHelper {

    public static void showMessageToFinish(String msg, final Context context) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setCancelable(false);

        // Setting Dialog Message
        alertDialog.setMessage(msg);

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                ((Activity) context).finish();
                dialog.dismiss();

            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    public static void showMessage(Context context, String msg) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        // Setting Dialog Message
        alertDialog.setMessage(msg);
        alertDialog.setCancelable(false);
        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });

        // Showing Alert Message
        alertDialog.show();
    }




    public static void alertInternetError(Context context, final OnClickInterface onClick) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        // Setting Dialog Message
        alertDialog.setMessage(context.getString(R.string.error_internet));
        alertDialog.setCancelable(true);
        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton(context.getString(R.string.title_retry), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                onClick.onClick();
                dialog.dismiss();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    public static void alertInflateError(Context context, final OnClickInterface onClick, String message) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        // Setting Dialog Message
        alertDialog.setMessage(message);
        alertDialog.setCancelable(false);
        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton(context.getString(R.string.title_retry), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                onClick.onClick();
                dialog.dismiss();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

   /* public static void alertLocationError(Context context, final OnClickInterface onClick) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        // Setting Dialog Message
        alertDialog.setMessage(context.getString(R.string.error_location));
        alertDialog.setCancelable(false);
        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton(context.getString(R.string.title_retry), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                onClick.onClick();
                dialog.dismiss();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }*/

   /* public static void showMessageToLogout(final Context context) {

        android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(new ContextThemeWrapper(context, android.R.style.Theme_DeviceDefault_Dialog_Alert));
        // Setting Dialog Message
        alertDialog.setMessage(context.getString(R.string.logout_message));
        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                LoginManagerSession.getInstance().logoutUser();

                context.startActivity(new Intent(context, NavigationActivity.class));
                ((Activity) context).finishAffinity();

                // clear cart data
                new DBHelper(context).deleteAllCartItem();

                dialog.dismiss();


            }
        });

        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });
        // Showing Alert Message
        alertDialog.show();
    }*/



    public static void alertEnableLocation(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(context.getString(R.string.title_gps))
                .setCancelable(false)
                .setPositiveButton(context.getString(R.string.title_yes), new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        context.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(context.getString(R.string.title_no), new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        System.exit(0);
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    public static void alertOpenAppSetting(String msg, final Context context) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        // Setting Dialog Message
        alertDialog.setMessage(msg);
        alertDialog.setCancelable(false);
        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton(context.getString(R.string.title_go_to_setting), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", context.getPackageName(), null);
                intent.setData(uri);
                context.startActivity(intent);
            }
        });

        alertDialog.setNegativeButton(context.getString(R.string.title_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                System.exit(0);
            }
        });

        // Showing Alert Message
        alertDialog.show();

    }

    public static void alertOpenAppCallSetting(String msg, final Context context) {

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        // Setting Dialog Message
        alertDialog.setMessage(msg);
        alertDialog.setCancelable(false);
        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton(context.getString(R.string.title_go_to_setting), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", context.getPackageName(), null);
                intent.setData(uri);
                context.startActivity(intent);
            }
        });

        alertDialog.setNegativeButton(context.getString(R.string.title_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        // Showing Alert Message
        alertDialog.show();

    }

}