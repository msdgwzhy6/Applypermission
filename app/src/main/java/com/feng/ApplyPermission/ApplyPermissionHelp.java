package com.feng.ApplyPermission;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

/**
 * @author feng
 * @Description: 权限申请辅助类
 * @date 2017/3/6
 */
public class ApplyPermissionHelp {
    private static final String TAG = ApplyPermissionHelp.class.getSimpleName();
    private Activity mActivity;
    private PermissionBean[] mPermissions;
    private ApplyPermissionListener mApplyPermissionListener;

    public ApplyPermissionHelp(Activity context, PermissionBean[] permission, ApplyPermissionListener listener) {
        mActivity = context;
        mPermissions = permission;
        mApplyPermissionListener = listener;
    }

    public void applyPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (PermissionBean mPermission : mPermissions) {
                if (ActivityCompat.checkSelfPermission(mActivity, mPermission.getPermissionName()) != PackageManager.PERMISSION_GRANTED) {
                    //开始申请权限
                    //判断用户是否第一次拒绝了权限，如果第一次拒绝那么第二次需要向用户解释为什么需要权限
                    requestPermissions(mPermission);
                    return;
                }

            }
            if (null != mApplyPermissionListener) {
                mApplyPermissionListener.applyPermissionGranted();
            }
        }
    }

    /**
     * 申请权限
     *
     * @param permissionBean
     */
    public void requestPermissions(PermissionBean permissionBean) {
        ActivityCompat.requestPermissions(mActivity, new String[]{permissionBean.getPermissionName()}, permissionBean.getApplyCode());
    }

    /**
     * 权限申请是否成功的回调
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public void onRequestPermissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        for (PermissionBean mPermission : mPermissions) {
            if (requestCode == mPermission.getApplyCode()) {
                if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    //判断用户是否第一次拒绝了权限，如果第一次拒绝那么第二次需要向用户解释为什么需要权限
                    if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity, mPermission.getPermissionName())) {
                        //自己弹出对话框提示用户
                        if (null != mApplyPermissionListener) {
                            mApplyPermissionListener.showRequestPermissionRationale(mPermission);
                            Log.d(TAG, "onRequestPermissionResult: 用户拒绝过一次，给予提示信息");
                        }
                    } else {
                        //引导用户去设置页面自动打开请求
//                        注：如果用户在过去拒绝了权限请求，并在权限请求系统对话框中选择了
//                        Don't ask again 选项，此方法将返回 false。
//                        如果设备规范禁止应用具有该权限，此方法也会返回 false。
                        //判断权限是否还有未申请的

                    }
                    return;
                }
                //检查是否还有为申请的
                if (!isAllRequestedPermissionGranted()) {
                    applyPermission();
                    Log.d(TAG, "onRequestPermissionResult: 继续申请权限");
                    return;
                }

                mApplyPermissionListener.applyPermissionGranted();
                Log.d(TAG, "onRequestPermissionResult: 申请完毕");
                return;
            }
        }
    }

    /**
     * 判断是否所有的权限都被授权了
     */
    public boolean isAllRequestedPermissionGranted() {
        for (PermissionBean mPermission : mPermissions) {
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(mActivity, mPermission.getPermissionName())) {
                return false;
            }
        }
        return true;
    }

    public interface ApplyPermissionListener {
        /**
         * 用户第一次拒绝以后，第二次开始向用户开始提示申请的理由
         *
         * @param permissionBean
         */
        void showRequestPermissionRationale(PermissionBean permissionBean);

        /**
         * 权限申请完毕
         */
        void applyPermissionGranted();
    }

}
