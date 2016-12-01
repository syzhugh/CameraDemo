package sun.camerademo.qrcode.camera;

import android.content.Context;
import android.hardware.Camera;
import android.os.Handler;

/**
 * Created by ZS27 on 2016/12/1.
 */

public class CameraManager {


    private Context context;
    private Camera camera;
    private CameraConfigurationManager cameraConfigurationManager;

    private boolean isPreviewing;
    private AutoFocusCallBack focusCallBack;
    private PreviewCallBack previewCallBack;

    public CameraManager(Context context) {
        this.context = context;
    }

    public void startPreview() {
        if (camera != null && !isPreviewing) {

            focusCallBack = new AutoFocusCallBack(camera);
            camera.setOneShotPreviewCallback(previewCallBack);
            camera.autoFocus(focusCallBack);
            camera.startPreview();
            isPreviewing = true;
        }
    }

    public void stopPreview() {
        if (focusCallBack != null) {
            focusCallBack.stop();
            focusCallBack = null;
        }
        if (camera != null && isPreviewing) {
            camera.stopPreview();
            isPreviewing = false;
        }
    }

    public void OpenDriver() throws Exception {
        if (camera == null) {
            camera = CameraOpenUtils.open();
            if (camera == null)
                throw new Exception("fail to open camera");
            initPreviewSize();
        }
    }

    public void initPreviewSize() {

    }


    public void CloseDriver() {
        if (camera != null) {
            camera.release();
            camera = null;
        }
    }

    public void requestPreviewFrame(Handler handler) {
        previewCallBack = new PreviewCallBack(camera, handler);
    }

}
