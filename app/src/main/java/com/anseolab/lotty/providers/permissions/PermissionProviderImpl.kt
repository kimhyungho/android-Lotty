package com.anseolab.lotty.providers.permissions

import android.content.Context
import android.util.Log
import com.gun0912.tedpermission.rx3.TedPermission
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class PermissionProviderImpl @Inject constructor(
    @ApplicationContext
    private val applicationContext: Context
) : PermissionProvider {
    override fun checkPermissions(vararg permission: String): Single<Boolean> {
        return  TedPermission.create()
            .setRationaleTitle("ㅇㄴㄹㅁㄴㅇㄹㄴㅇㅁ")
            .setRationaleMessage("ㄹㄴㅁㅇㄹㄴㅁㅇㄹㄴㅁㅇㄹㄴㅁㅇ")
            .setPermissions(*permission)
            .request()
            .map { it.isGranted }

//        return TedPermission.create()
//            .setPermissions(*permission)
//            .request()
//            .doOnSuccess {
//                if(it.isGranted) return@doOnSuccess
//                // TODO showDeniedDialog
//            }.map {
//                it.isGranted
//            }
    }
}