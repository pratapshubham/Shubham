package com.braintech.carpooling.common.utility;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;

import com.braintech.carpooling.R;

import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Braintech on 9/12/2016.
 */
public class Utils {

    // for keyboard down
    public static void hideKeyboardIfOpen(Activity activity) {


        View view = activity.getCurrentFocus();

        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

    }


    public static boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


    public static Boolean isEmptyOrNull(String value) {
        if (value == null || value.isEmpty())
            return true;
        else
            return false;
    }

    public static String getDeviceId(Context context) {
        String android_id = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);

        return android_id;
    }

    public static Boolean isPasswordMatch(String password, String confirmPassword) {
        if (password.equals(confirmPassword))
            return true;
        else
            return false;
    }

    public static String convertDateFormat(String date, String inputFormat, String outputFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(inputFormat);
        Date testDate = null;
        String newFormat = "N/A";
        try {
            testDate = sdf.parse(date);
            SimpleDateFormat formatter = new SimpleDateFormat(outputFormat);
            newFormat = formatter.format(testDate);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return newFormat;
    }


    public static Boolean isVisibleOrNull(Fragment fragment) {
        if (fragment != null) {
            if (fragment.isVisible())
                return true;
            else
                return false;
        } else
            return false;
    }


    public static String getRealPathFromURI(String contentURI, Context context) {
        Uri contentUri = Uri.parse(contentURI);
        Cursor cursor = context.getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(index);
        }
    }

    public static Bitmap resizeImageForImageView(Bitmap bitmap, float rotation) {

        Bitmap resizedBitmap = null;
        int scaleSize = 1024;
        int originalWidth = bitmap.getWidth();
        int originalHeight = bitmap.getHeight();
        int newWidth = -1;
        int newHeight = -1;
        float multFactor = -1.0F;
        if (originalHeight > originalWidth) {
            newHeight = scaleSize;
            multFactor = (float) originalWidth / (float) originalHeight;
            newWidth = (int) (newHeight * multFactor);
        } else if (originalWidth > originalHeight) {
            newWidth = scaleSize;
            multFactor = (float) originalHeight / (float) originalWidth;
            newHeight = (int) (newWidth * multFactor);
        } else if (originalHeight == originalWidth) {
            newHeight = scaleSize;
            newWidth = scaleSize;
        }


        Matrix matrix = new Matrix();
        matrix.postRotate(rotation);

        resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, newWidth, newHeight, matrix, true);

        //resizedBitmap = createScaledBitmap(bitmap, newWidth, newHeight, false, rotation);
        return resizedBitmap;
    }

    public static Bitmap rotateImage(Bitmap bitmap, float rotation) {

        Bitmap resizedBitmap = null;
        int scaleSize = 1024;
        int originalWidth = bitmap.getWidth();
        int originalHeight = bitmap.getHeight();


        Matrix matrix = new Matrix();
        matrix.postRotate(rotation);

        return Bitmap.createBitmap(bitmap, 0, 0, originalWidth, originalHeight, matrix, true);

    }

    public static String formatCurrencyUk(Double amount) {

        String s = null;
        try {

            NumberFormat n = NumberFormat.getCurrencyInstance(Locale.UK);
            s = n.format(amount);

        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
        return s;
    }


    public static String formatDecimal(double amount) {
        DecimalFormat form = new DecimalFormat("0.00");
        return form.format(amount);
    }

    public static String getColoredSpanned(String text, String color) {
        String input = "<font color=" + color + ">" + text + "</font>";
        return input;
    }

    public static Spanned fromHtml(String text) {
        Spanned result;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            result = Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(text);
        }

        return result;
    }

    public static CharSequence setFontsize(Context context, String firstString, String secondString) {

        int textSize1 = context.getResources().getDimensionPixelSize(R.dimen.text_size_40_sp);
        int textSize2 = context.getResources().getDimensionPixelSize(R.dimen.text_size_30_sp);

        SpannableString span1 = new SpannableString(firstString);
        span1.setSpan(new AbsoluteSizeSpan(textSize1), 0, firstString.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);

        SpannableString span2 = new SpannableString(secondString);
        span2.setSpan(new AbsoluteSizeSpan(textSize2), 0, secondString.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);

// let's put both spans together with a separator and all
        CharSequence finalText = TextUtils.concat(span1, span2);

        CharSequence trimmedText = trimTrailingWhitespace(finalText);
        return trimmedText;
    }

    public static CharSequence trimTrailingWhitespace(CharSequence source) {

        if (source == null)
            return "";

        int i = source.length();

        // loop back to the first non-whitespace character
        while (--i >= 0 && Character.isWhitespace(source.charAt(i))) {
        }

        return source.subSequence(0, i + 1);
    }

    public static String getVersionNumber(Context context) {
        PackageManager manager = context.getPackageManager();
        PackageInfo info = null;
        String version = "";
        try {
            info = manager.getPackageInfo(
                    context.getPackageName(), 0);
            version = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return version;

    }

    public static boolean fileSizeExceed(String path) {
        File file = new File(path);

        // Get length of file in bytes
        long fileSizeInBytes = file.length();

        // Convert the bytes to Kilobytes (ic_splash KB = 1024 Bytes)
        long fileSizeInKB = fileSizeInBytes / 1024;

        // Convert the KB to MegaBytes (ic_splash MB = 1024 KBytes)
        long fileSizeInMB = fileSizeInKB / 1024;

        Log.e("filesize",""+fileSizeInMB);

        if (fileSizeInMB > 50) {
            return true;
        }
        return false;
    }

    public static String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }

    public static boolean isInPortraitMode(String path) {
        MediaMetadataRetriever metaRetriever = new MediaMetadataRetriever();
        metaRetriever.setDataSource(path);
        String height = metaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT);
        String width = metaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return true;
        } else {
            try {
                // Log.d("Width ", width + " Height " + height);
                if (Integer.parseInt(height) > Integer.parseInt(width)) {
                    //   Log.d("Mode ", " portrait ");
                    return true;
                } else {
                    //  Log.d("Mode ", " landscape");
                    return false;
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return false;

            }
        }
    }

}
