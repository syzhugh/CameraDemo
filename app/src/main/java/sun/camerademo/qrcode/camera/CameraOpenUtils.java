package sun.camerademo.qrcode.camera;

import android.hardware.Camera;

/**
 * Created by ZS27 on 2016/12/1.
 */

public class CameraOpenUtils {

    public static Camera open() {

        int numberOfCameras = Camera.getNumberOfCameras();
        if (numberOfCameras == 0)
            return null;

        for (int i = 0; i < numberOfCameras; i++) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                return Camera.open(i);
            }
        }
        return null;
    }

    public static Camera open(int cameraId) {

        int numberOfCameras = Camera.getNumberOfCameras();
        if (numberOfCameras == 0)
            return null;

        if (cameraId < 0 || cameraId > numberOfCameras - 1) {
            return null;
        } else {
            return Camera.open(cameraId);
        }
    }
}