/*
* Copyright 2015 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.wenjackp.android.lib.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * 权限管理工具类
 * 用于Android6.0权限检测判断
 *
 * @author WenJackp
 * @version 1.0
 */
public class PermissionUtils {

    /**
     * 判断权限是否已经注册
     *
     * @param activity    上下文对象
     * @param permissions 多个权限
     * @return true没注册
     */
    public static boolean checkSelfPermission(Activity activity, String[] permissions) {

        if (!EmptyUtils.emptyOfArray(permissions)) {
            for (String permission : permissions) {
                boolean self = ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED;
                if (self) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断权限是否已经注册
     *
     * @param context    上下文对象
     * @param permission 单个权限
     * @return true没注册
     */
    public static boolean checkSelfPermission(Context context, String permission) {
        return ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED;
    }

    public static boolean shouldShowRequestPermissionRationale(Activity activity, String[] permissions) {

        if (!EmptyUtils.emptyOfArray(permissions)) {
            for (String permission : permissions) {
                boolean self = shouldShowRequestPermissionRationale(activity, permission);
                if (self) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean shouldShowRequestPermissionRationale(Activity activity, String permission) {
        return ActivityCompat.shouldShowRequestPermissionRationale(activity, permission);
    }

    /**
     * 校验权限状态
     *
     * @param grantResults 权限状态集合
     * @return true全部已经注册
     */
    public static boolean verifyPermission(int[] grantResults) {
        // At least one result must be checked.
        if (grantResults.length < 1) {
            return false;
        }

        // Verify that each required permission has been granted, otherwise return false.
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

}
