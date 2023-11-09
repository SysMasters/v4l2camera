package pri.tool.anv4l2camera;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import pri.tool.bean.Frame;
import pri.tool.bean.Parameter;
import pri.tool.v4l2camera.IStateCallback;
import pri.tool.v4l2camera.V4L2Camera;

public class V4L2CameraActivity2 extends Activity {

    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


    }

    public void initView() {
        surfaceView = findViewById(R.id.cameraSurface);

        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                initCamera();
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

            }
        });

    }

    public void initCamera() {
        V4L2Camera adCamera = new V4L2Camera();
        adCamera.init(new IStateCallback() {
            @Override
            public void onOpened() {
                Parameter parameter = adCamera.getCameraParameters().get(0);
                Frame frame = parameter.frames.get(0);
                adCamera.setPreviewParameter(frame.width, frame.height, parameter.pixFormat);
                adCamera.setSurface(surfaceHolder);
            }

            @Override
            public void onError(int error) {

            }
        }, this);
        adCamera.open(0);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

}
