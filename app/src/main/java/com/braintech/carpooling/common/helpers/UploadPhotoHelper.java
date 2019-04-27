package com.braintech.carpooling.common.helpers;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.Log;

import com.braintech.carpooling.common.utility.Utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Braintech on 11-Nov-16.
 */
public class UploadPhotoHelper {
    private static volatile Matrix sScaleMatrix;


    public static boolean checkPermissions(Activity activity) {
        PackageManager pm = activity.getPackageManager();
        int hasPermWrie = pm.checkPermission(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                activity.getPackageName());

        int hasPermRead = pm.checkPermission(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                activity.getPackageName());

        int hasPermCamara = pm.checkPermission(
                Manifest.permission.CAMERA,
                activity.getPackageName());

        if (hasPermWrie != PackageManager.PERMISSION_GRANTED || hasPermRead != PackageManager.PERMISSION_GRANTED || hasPermCamara != PackageManager.PERMISSION_GRANTED) {
            return false;
        } else {
            return true;
        }

    }

    public static Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.PNG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    // convert image (bitmap) to base64
    public static String convertImageToBase64(Bitmap bmp) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);

        return encodedImage;
    }

    public static void openCamera(Activity activity, int REQUEST_CAMERA) {

        int permissionCheckCamera = ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA);

        int permissionCheckRead = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);

        int permissionCheckWrite = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


            if ((permissionCheckCamera == -1) || (permissionCheckRead == -1) || (permissionCheckWrite == -1)) {

                if (!Settings.System.canWrite(activity)) {
                    activity.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 2909);
                } else {
                    //  openFrontFacingCameraGingerbread();
                    // continue with your code
                    openCameraMethod(activity, REQUEST_CAMERA);
                }
            } else {
                //openFrontFacingCameraGingerbread();
                openCameraMethod(activity, REQUEST_CAMERA);
            }
        } else {
            // continue with your code
            openCameraMethod(activity, REQUEST_CAMERA);
            //    openFrontFacingCameraGingerbread();
        }
        //calling method to select image from camera or device
    }

  /* public void sendIntentToOpenCamera(){
       Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
       File f = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");
       intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
       imageToUploadUri = Uri.fromFile(f);
       startActivityForResult(intent, CAMERA_PIC_REQUEST);
    }*/

    public static void openCameraMethod(Activity activity, int REQUEST_CAMERA) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 0);
        intent.putExtra("aspectY", 0);
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);
        intent.putExtra("return-data", true);
        try {
            activity.startActivityForResult(intent, REQUEST_CAMERA);
        } catch (ActivityNotFoundException e) {
            // Do nothing for now
        }


    }

    public static void openGallery(Activity activity, int RESULT_LOAD_IMG) {
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        activity.startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);

    }

    public static Bitmap compressByteImage(byte[] image, Context context) {

        return BitmapFactory.decodeByteArray(image, 0, image.length, null);

    }


    // Compress image
    public static Bitmap compressImage(String imageUri, Context context, float rotation) {
        String filePath = getRealPathFromURI(imageUri, context);

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        int imageHeight = options.outHeight;
        int imageWidth = options.outWidth;

        // Calculate inSampleSize
        // options.inSampleSize = calculateInSampleSize(options, imageHeight, imageWidth);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);

        Bitmap resizeBitmap = resizeImageForImageView(bitmap, rotation);

        return resizeBitmap;

    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        Log.e("size", "" + inSampleSize);

        return inSampleSize;
    }

    public static Bitmap rotateImage(Bitmap bitmap, float rotation) {

        Bitmap resizedBitmap = null;
        int scaleSize = 1024;
        int originalWidth = bitmap.getWidth();
        int originalHeight = bitmap.getHeight();
       /* int newWidth = -ic_splash;
        int newHeight = -ic_splash;
        float multFactor = -ic_splash.0F;
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
        }*/

        //  Log.e("rotation", "" + rotation);

        Matrix matrix = new Matrix();
        matrix.postRotate(rotation);

        return Bitmap.createBitmap(bitmap, 0, 0, originalWidth, originalHeight, matrix, true);

        //resizedBitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, false);
        //return resizedBitmap;
    }

    public static Bitmap resizeImageForImageView(Bitmap bitmap, float rotation) {

        Bitmap resizedBitmap = null;
        int scaleSize = 512;
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

        // resizedBitmap = createScaledBitmap(bitmap, newWidth, newHeight, false, rotation);
        resizedBitmap = createScaledBitmap(bitmap, newWidth, newHeight, false, rotation);
        return resizedBitmap;
    }

    public static Bitmap createScaledBitmap(Bitmap src, int dstWidth, int dstHeight,
                                            boolean filter, float rotation) {
        Matrix m;
        synchronized (Bitmap.class) {
            // small pool of just 1 matrix
            m = sScaleMatrix;
            sScaleMatrix = null;
        }

        if (m == null) {
            m = new Matrix();
        }

        m.postRotate(rotation);

        final int width = src.getWidth();
        final int height = src.getHeight();
        final float sx = dstWidth / (float) width;
        final float sy = dstHeight / (float) height;
        m.setScale(sx, sy);
        Bitmap b = Bitmap.createBitmap(src, 0, 0, width, height, m, filter);

        synchronized (Bitmap.class) {
            // do we need to check for null? why not just assign everytime?
            if (sScaleMatrix == null) {
                sScaleMatrix = m;
            }
        }

        return b;
    }

    public static Bitmap resizeImageForGallery(Bitmap bitmap, float rotation) {

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

        Log.e("rotation", "" + rotation);

        Matrix matrix = new Matrix();
        matrix.preRotate(rotation);

        //return Bitmap.createBitmap(bitmap, 0, 0, newWidth, newHeight, matrix, true);

        resizedBitmap = createScaledBitmap(bitmap, newWidth, newHeight, false, rotation);
        return resizedBitmap;
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

    public static byte[] convertToByte(Bitmap bmp) {

        byte[] byteArray = null;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byteArray = stream.toByteArray();
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
        return byteArray;
    }

    public static String convertToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();

        String image = Base64.encodeToString(byteArray, Base64.DEFAULT);

        return image;
    }

    public static byte[] convertBaseToByte(String base64) {
        byte[] data = Base64.decode(base64, Base64.DEFAULT);
        return data;
    }

    public static Boolean isEmptOrNull(String value) {
        if (value == null || value.isEmpty())
            return true;
        else
            return false;
    }


    public static Bitmap convertToBitmap(byte[] encodeByte) {
        return BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
    }

    public static String getVersionCode(Context context) {
        PackageInfo pInfo = null;
        try {
            pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String version = pInfo.versionName;

        return version;
    }

    public static float getOrientation(String path) {
        float rotate = 0;
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(path);
            //Log.d("imageUri", photo.getPath());
            //Toast.makeText(getActivity(), mImageUri.getPath(), Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int orientation = exif.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_270:
                rotate = 270;
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                rotate = 180;
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                rotate = 90;
                break;
        }
        return rotate;

    }

    public static File createTemporaryFile(String part, String ext) throws Exception {
        File tempDir = Environment.getExternalStorageDirectory();
        tempDir = new File(tempDir.getAbsolutePath() + "/.temp/");
        if (!tempDir.exists()) {
            tempDir.mkdirs();
        }
        return File.createTempFile(part, ext, tempDir);
    }


    public static ByteArrayInputStream getImageInByteArray(Bitmap bitmap) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        getSquareSizeImage(bitmap).compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
        byte[] bitmapdata = bos.toByteArray();
        ByteArrayInputStream bs = new ByteArrayInputStream(bitmapdata);
        return bs;
    }


    public static Bitmap getSquareSizeImage(Bitmap srcBmp) {

        Bitmap dstBmp;

        if (srcBmp.getWidth() >= srcBmp.getHeight()) {

            dstBmp = Bitmap.createBitmap(
                    srcBmp,
                    srcBmp.getWidth() / 2 - srcBmp.getHeight() / 2,
                    0,
                    srcBmp.getHeight(),
                    srcBmp.getHeight()
            );

        } else {

            dstBmp = Bitmap.createBitmap(
                    srcBmp,
                    0,
                    srcBmp.getHeight() / 2 - srcBmp.getWidth() / 2,
                    srcBmp.getWidth(),
                    srcBmp.getWidth()
            );
        }

        return dstBmp;
    }

    public static String getAbsolutePath(Bitmap imagePath, String resizeImageName) {

        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        //Bitmap b = BitmapFactory.decodeFile(imagePath);

        int scaleSize = 512;
        int originalWidth = imagePath.getWidth();
        int originalHeight = imagePath.getHeight();
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

        Bitmap out = Bitmap.createScaledBitmap(imagePath, newWidth, newHeight, false);

        File file = new File(dir, imagePath.getGenerationId() + resizeImageName);
        FileOutputStream fOut;
        try {
            fOut = new FileOutputStream(file);
            out.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            fOut.flush();
            fOut.close();
            out.recycle();
        } catch (Exception e) {
        }

        String newPath = file.getAbsolutePath();
        return newPath;
    }

    public static Bitmap generateThumbnail(String path) {
        Bitmap thumbail = ThumbnailUtils.createVideoThumbnail(path, MediaStore.Video.Thumbnails.MINI_KIND);
        return thumbail;
    }

    public static String generateThumbnailImage(String path, Context context) {
        Bitmap thumb = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(path), 130, 130);
        String imageName = "img_thumbnail" + System.currentTimeMillis();

        String thumbnailImage = UploadPhotoHelper.getAbsolutePath(thumb, imageName + ".JPEG");

        String pathImage = Utils.getRealPathFromURI(thumbnailImage, context);
        return pathImage;
    }
}
