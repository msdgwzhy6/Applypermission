# ApplyPermission
Android 基于 6.0 版本（API 级别 23）封装的一个权限申请的一个辅助类，可同时申请多个权限！
## android 6.0 权限申请辅助 
#### 效果图：
![效果图](https://github.com/shuangqingfeng/Applypermission/raw/master/screenshots/permission.gif)
#### 项目用法如下 ，只需拷贝两个类到想项目中即可完成 所需要的权限申请 ，也可自行更改：
```Java
 public void applyPermission(View view) {
        PermissionBean permissionBean = new PermissionBean();
        permissionBean.setPermissionName(Manifest.permission.READ_PHONE_STATE);
        permissionBean.setExplain("拨打电话");
        permissionBean.setApplyCode(100);
        PermissionBean permissionBean1 = new PermissionBean();
        permissionBean1.setPermissionName(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        permissionBean1.setExplain("读取短信");
        permissionBean1.setApplyCode(101);

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
```
    
## License
Copyright 2017 feng
