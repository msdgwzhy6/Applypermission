package com.feng.ApplyPermission;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.ApplyPermission.R;

public class MainActivity extends AppCompatActivity {

    private ApplyPermissionHelp applyPermissionHelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void applyPermission(View view) {
        PermissionBean permissionBean = new PermissionBean();
        permissionBean.setPermissionName(Manifest.permission.READ_PHONE_STATE);
        permissionBean.setExplain("拨打电话");
        permissionBean.setApplyCode(100);
        PermissionBean permissionBean1 = new PermissionBean();
        permissionBean1.setPermissionName(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        permissionBean1.setExplain("读取短信");
        permissionBean1.setApplyCode(101);
        PermissionBean permissionBean2 = new PermissionBean();
        permissionBean2.setPermissionName(Manifest.permission.READ_EXTERNAL_STORAGE);
        permissionBean2.setExplain("读入权限");
        permissionBean2.setApplyCode(102);
        PermissionBean permissionBean3 = new PermissionBean();
        permissionBean3.setPermissionName(Manifest.permission.SEND_SMS);
        permissionBean3.setExplain("发送短信");
        permissionBean3.setApplyCode(103);

        PermissionBean[] permissions = {permissionBean, permissionBean1, permissionBean2, permissionBean3};
        applyPermissionHelp = new ApplyPermissionHelp(this, permissions, new PermissionListener());
        applyPermissionHelp.applyPermission();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (null != applyPermissionHelp) {
            applyPermissionHelp.onRequestPermissionResult(requestCode, permissions, grantResults);
        }
    }

    private class PermissionListener implements ApplyPermissionHelp.ApplyPermissionListener {

        @Override
        public void showRequestPermissionRationale(PermissionBean permissionBean) {
            Toast.makeText(MainActivity.this, permissionBean.getExplain(), Toast.LENGTH_SHORT).show();
            applyPermissionHelp.requestPermissions(permissionBean);
        }

        @Override
        public void applyPermissionGranted() {
            Toast.makeText(MainActivity.this, "权限申请完毕", Toast.LENGTH_SHORT).show();
        }
    }
}
