package ru.firstSet.kotlinOne.data

import android.Manifest
import ru.firstSet.kotlinOne.R
import ru.firstSet.kotlinOne.utils.RequestPermissions.Companion.PERMISSION_REQUEST_CODE

sealed class AppPermission(
    val permissionName: String,
    val requestCode: Int,
    val deniedMessageId: Int,
    val explanationMessageId: Int
) {
    companion object {
        val permissionsMovieApp: List<AppPermission> by lazy {
            listOf(
                READ_CALENDAR
            )
        }
    }
}

object READ_CALENDAR : AppPermission(
    Manifest.permission.READ_CALENDAR, PERMISSION_REQUEST_CODE,
    R.string.permission_denied, R.string.permission_is_required_select
)

