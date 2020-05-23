package com.vj.base.utils

import android.content.pm.PackageManager
import android.Manifest.permission
import android.app.Activity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.vj.base.constant.ActivityRequestCode

object PermissionsUtils {

    fun isNetworkPermissionGranted(activity: Activity): Boolean {
        if (ContextCompat.checkSelfPermission(activity, permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission.CALL_PHONE)) {
            } else {
                ActivityCompat.requestPermissions(activity, arrayOf(permission.CALL_PHONE), ActivityRequestCode.REQUEST_CODE_CALL_PHONE)
            }
        } else {
            return true
        }
        return false
    }
}