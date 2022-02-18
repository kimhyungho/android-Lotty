package com.anseolab.lotty.providers.permissions

import io.reactivex.rxjava3.core.Single

interface PermissionProvider {
    fun checkPermissions(
        vararg permission: String
    ): Single<Boolean>
}