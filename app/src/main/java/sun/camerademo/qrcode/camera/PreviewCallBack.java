package sun.camerademo.qrcode.camera;

import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

/**
 * Created by ZS27 on 2016/12/1.
 */

public class PreviewCallBack implements Camera.PreviewCallback {

    public static final String PREVIEWFRAME = "preview";

    private final Camera camera;
    private final Handler handler;

    public PreviewCallBack(Camera camera, Handler handler) {
        this.camera = camera;
        this.handler = handler;
        camera.setOneShotPreviewCallback(this);
    }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        Message message = handler.obtainMessage();
        Bundle bundle = new Bundle();
        bundle.putByteArray(PREVIEWFRAME, data);
        message.setData(bundle);
        message.sendToTarget();
    }
}
