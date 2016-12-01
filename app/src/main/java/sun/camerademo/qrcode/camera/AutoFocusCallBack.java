package sun.camerademo.qrcode.camera;

import android.hardware.Camera;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

/**
 * Created by ZS27 on 2016/12/1.
 */

public class AutoFocusCallBack implements Camera.AutoFocusCallback {


    private final Camera camera;

    private HandlerThread handlerThread;
    private Handler handler;
    public static final int DELAY = 2000;
    public static final int AUTOFOCUS = 0x100;

    public boolean isFocusing = false;

    public AutoFocusCallBack(final Camera camera) {
        this.camera = camera;
        handlerThread = new HandlerThread("autofocus");
        handlerThread.start();
        handler = new Handler(handlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                start();
            }
        };

        start();
    }

    public void stop() {
        handler.removeMessages(AUTOFOCUS);
        handlerThread.quit();
        handlerThread = null;
        handler = null;
    }

    public void start() {
        if (!isFocusing) {
            isFocusing = true;
            camera.autoFocus(AutoFocusCallBack.this);
        }
    }

    @Override
    public void onAutoFocus(boolean success, Camera camera) {
        isFocusing = false;
        handler.sendEmptyMessageDelayed(AUTOFOCUS, DELAY);
    }
}
