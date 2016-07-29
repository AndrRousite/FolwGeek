package com.iyiyo.mvc.ui.activity;

import android.app.Service;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Vibrator;
import android.view.View;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.iyiyo.mvc.R;

import org.kymjs.kjframe.KJAnimations;

/**
 * 摇一摇界面
 * Created by liu-feng on 2016/7/16.
 * 邮箱:w710989327@foxmail.com
 * https://github.com/Unlm
 */
public class ShakeActivity extends BaseHoldBackActivity implements SensorEventListener {

    ImageView shakeImg;
    ProgressBar progress;
    FrameLayout shakeBottom;

    private SensorManager sensorManager = null;
    private Vibrator vibrator = null;
    private float lastX;
    private float lastY;
    private float lastZ;
    private long lastUpdateTime;
    private boolean isRequest = false;
    private static final int SPEED_SHRESHOLD = 45;// 这个值越大需要越大的力气来摇晃手机
    private static final int UPTATE_INTERVAL_TIME = 50;

    @Override
    public int getResourceId() {
        return R.layout.activity_shake;
    }

    @Override
    public void initToolBar() {
        super.initToolBar();
        mToolbar.setSubtitle("摇一摇");
    }

    @Override
    public void initView() {
        shakeImg = findView(R.id.shake_img);
        progress = findView(R.id.progress);
        shakeBottom = findView(R.id.shake_bottom);
    }

    @Override
    public void initData() {
        sensorManager = (SensorManager) this
                .getSystemService(Context.SENSOR_SERVICE);
        vibrator = (Vibrator) this.getSystemService(Service.VIBRATOR_SERVICE);
    }

    @Override
    public void onClick(int v) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        long currentUpdateTime = System.currentTimeMillis();
        long timeInterval = currentUpdateTime - lastUpdateTime;
        if (timeInterval < UPTATE_INTERVAL_TIME) {
            return;
        }
        lastUpdateTime = currentUpdateTime;

        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        float deltaX = x - lastX;
        float deltaY = y - lastY;
        float deltaZ = z - lastZ;

        lastX = x;
        lastY = y;
        lastZ = z;

        double speed = (Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ
                * deltaZ) / timeInterval) * 100;
        if (speed >= SPEED_SHRESHOLD && !isRequest) {
            shakeBottom.setVisibility(View.GONE);
            vibrator.vibrate(300);
            onShake();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (sensorManager != null) {
            Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            if (sensor != null) {
                sensorManager.registerListener(this, sensor,
                        SensorManager.SENSOR_DELAY_GAME);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    /**
     * 摇动手机成功后调用
     */
    private void onShake() {
        isRequest = true;
        progress.setVisibility(View.VISIBLE);
        Animation anim = KJAnimations.shakeAnimation(shakeImg.getLeft());
        anim.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                showToast("红薯跟你开了个玩笑",0,0);
                isRequest = false;
                progress.setVisibility(View.GONE);
            }
        });
        shakeImg.startAnimation(anim);
    }
}
