package com.braintech.carpooling.common.application;

import android.app.Application;
import android.content.Context;

import com.braintech.carpooling.common.utility.TypeFaceUtil;

import org.acra.ACRA;


/**
 * Created by Braintech on 6/29/2017.
 *//*
@ReportsCrashes(mailTo = "neha.tyagi@braintechnosys.biz", customReportContent = {
        ReportField.APP_VERSION_CODE, ReportField.APP_VERSION_NAME,
        ReportField.ANDROID_VERSION, ReportField.PHONE_MODEL,
        ReportField.CUSTOM_DATA, ReportField.STACK_TRACE, ReportField.LOGCAT},
        mode = ReportingInteractionMode.TOAST, resToastText = R.string.crash_toast_text)
*/
public class CarPoolingApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        context = this;

        //TypeFaceUtil.setDefaultFont(this, "MONOSPACE", "fonts/Poppins-Regular.otf");

       // ACRA.init(this);
    }


    public static Context getInstance() {
        return context;
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }
}
