package ru.firstSet.kotlinOne.utils

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import com.google.android.material.snackbar.Snackbar
import ru.firstSet.kotlinOne.R
import ru.firstSet.kotlinOne.movieDetails.FragmentMovieDetails

class RequestPermissions(val activity: Activity) {

    fun requestPermissionWithRationale() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                activity,
                Manifest.permission.READ_CALENDAR
            )
        ) {
            activity.let {
                Snackbar.make(
                    it.findViewById<View>(R.id.fmdBackground),
                    R.string.calendar_permission_is_required_select,
                    Snackbar.LENGTH_LONG
                )
                    .setAction("GRANT") { requestPerms() }
                    .show()
            }
        } else {
            requestPerms()
        }
    }

    private fun requestPerms() {
        val permissions = arrayOf(Manifest.permission.READ_CALENDAR)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(activity, permissions, FragmentMovieDetails.PERMISSION_REQUEST_CODE)
        }
    }

    fun hasPermissions(): Boolean {
        var res = 0
        val permissions = arrayOf(Manifest.permission.READ_CALENDAR)
        for (perms in permissions) {
            res = PermissionChecker.checkCallingOrSelfPermission(activity, perms)
            if (res != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    fun checkPermission(callbackId: Int, vararg permissionsId: String) {
        var permissions = true
        for (p in permissionsId) {
            permissions =
                permissions && activity.let {
                    ContextCompat.checkSelfPermission(
                        it,
                        p
                    )
                } == PackageManager.PERMISSION_GRANTED
        }
        if (!permissions) ActivityCompat.requestPermissions(
            activity,
            permissionsId,
            callbackId
        )
    }
}