package ru.firstSet.kotlinOne.utils

import android.app.Activity
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import ru.firstSet.kotlinOne.data.AppPermission

class RequestPermissions(val activity: Activity) {

    fun checkPermission(permissions: List<AppPermission>): Boolean {
        var boolean: Boolean = true
        activity.let {
            for (permission in permissions) {
                if (ContextCompat.checkSelfPermission(
                        it, permission.permissionName
                    ) != PermissionChecker.PERMISSION_GRANTED
                ) boolean = false
            }
        } ?: false
        return boolean
    }

    companion object {
        const val PERMISSION_REQUEST_CODE = 152
    }
}
