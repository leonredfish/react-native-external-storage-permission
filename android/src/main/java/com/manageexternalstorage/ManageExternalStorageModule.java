package com.manageexternalstorage;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

import android.os.Build;
import android.os.Environment;
import android.content.pm.PackageManager;
import android.content.Intent;
import android.app.Activity;
import android.net.Uri;
import android.provider.Settings;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.uimanager.IllegalViewOperationException;

public class ManageExternalStorageModule extends ReactContextBaseJavaModule implements ActivityEventListener {

    private Promise mGrantedPromise;

    public ManageExternalStorageModule(@Nullable ReactApplicationContext reactContext) {
        super(reactContext);
        reactContext.addActivityEventListener(this);
    }

    @NonNull
    @Override
    public String getName() {
        return "ManageExternalStorage";
    }

    @ReactMethod
    public void checkAndGrantPermission(Promise promise) {
        try {
            if (!checkPermission()) {
                mGrantedPromise = promise;
                requestPermission();
            } else {
                promise.resolve(true);
            }
        } catch (IllegalViewOperationException e) {
            promise.reject(e);
        }
    }

    private boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= 30) {
            return Environment.isExternalStorageManager();
        } else {
            int result = ContextCompat.checkSelfPermission(getReactApplicationContext(), READ_EXTERNAL_STORAGE);
            int result1 = ContextCompat.checkSelfPermission(getReactApplicationContext(), WRITE_EXTERNAL_STORAGE);
            return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
        }
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= 30) {
            try {
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(Uri.parse(String.format("package:%s",getReactApplicationContext().getPackageName())));
                getCurrentActivity().startActivityForResult(intent, 2296);
            } catch (Exception e) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                getCurrentActivity().startActivityForResult(intent, 2296);
            }
        } else {
            //below android 11
            ActivityCompat.requestPermissions(getCurrentActivity(), new String[]{WRITE_EXTERNAL_STORAGE}, 100);
        }
    }

    @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
        if (requestCode == 2296) {
            if (Build.VERSION.SDK_INT >= 30) {
                if (Environment.isExternalStorageManager()) {
                    if (mGrantedPromise != null) {
                        mGrantedPromise.resolve(true);
                    }
                } else {
                    if (mGrantedPromise != null) {
                        mGrantedPromise.resolve(false);
                    }
                }
            } else {
                if (ContextCompat.checkSelfPermission(getReactApplicationContext(), WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    if (mGrantedPromise != null) {
                        mGrantedPromise.resolve(true);
                    }
                } else {
                    if (mGrantedPromise != null) {
                        mGrantedPromise.resolve(false);
                    }
                }
            }
        } else {
            if (mGrantedPromise != null) {
                mGrantedPromise.resolve(false);
            }
        }

        mGrantedPromise = null;
    }

    @Override
    public void onNewIntent(Intent intent) {
        // do nothing
    }
}
