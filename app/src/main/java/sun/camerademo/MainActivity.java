package sun.camerademo;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ImageView;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback {

    @BindView(R.id.main_surface)
    SurfaceView mainSurface;
    @BindView(R.id.main_browser)
    ImageView mainBrowser;
    @BindView(R.id.main_catch)
    ImageView mainCatch;

    private Camera camera;
    private Camera.Parameters parameters;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        mainSurface.getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            camera = Camera.open(); // 打开摄像头
            camera.setPreviewDisplay(holder); // 设置用于显示拍照影像的SurfaceHolder对象
            camera.setDisplayOrientation(getPreviewDegree(MainActivity.this));
            camera.startPreview(); // 开始预览
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.i("info", "MainActivity:surfaceChanged----------------------");
        Log.i("info", ":" + width + " " + height);
        parameters = camera.getParameters(); // 获取各项参数
        parameters.setPictureFormat(PixelFormat.JPEG); // 设置图片格式
        parameters.setPreviewSize(width, height); // 设置预览大小
        parameters.setPreviewFrameRate(5);  //设置每秒显示4帧
        parameters.setPictureSize(width, height); // 设置保存的图片尺寸
        parameters.setJpegQuality(80); // 设置照片质量
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (camera != null) {
            camera.release();
            camera = null;
        }
    }

    public static int getPreviewDegree(Activity activity) {
        // 获得手机的方向
        int rotation = activity.getWindowManager().getDefaultDisplay()
                .getRotation();
        int degree = 0;
        // 根据手机的方向计算相机预览画面应该选择的角度
        switch (rotation) {
            case Surface.ROTATION_0:
                degree = 90;
                break;
            case Surface.ROTATION_90:
                degree = 0;
                break;
            case Surface.ROTATION_180:
                degree = 270;
                break;
            case Surface.ROTATION_270:
                degree = 180;
                break;
        }
        return degree;
    }


}
