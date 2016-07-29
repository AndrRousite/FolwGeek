package com.iyiyo.mvc.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.iyiyo.mvc.R;
import com.iyiyo.mvc.config.Properties;
import com.iyiyo.mvc.ui.dialog.ImageMenuDialog;
import com.iyiyo.mvc.ui.widget.TouchImageView;
import com.iyiyo.mvc.utils.TDevice;

import org.kymjs.kjframe.Core;
import org.kymjs.kjframe.bitmap.BitmapCallBack;

import java.util.Calendar;

/**
 * 图片预览界面
 * Created by liu-feng on 2016/7/16.
 * 邮箱:w710989327@foxmail.com
 * https://github.com/Unlm
 */
public class PhotoViewActivity extends BaseActivity {

    public static final String BUNDLE_KEY_IMAGES = "bundle_key_images";
    private TouchImageView mTouchImageView;
    private ProgressBar mProgressBar;
    private ImageView mOption;
    private String mImageUrl;
    private long lastClickTime = 0;

    public static void newInstance(Context context,
                                   String imageUrl) {
        Intent intent = new Intent(context, PhotoViewActivity.class);
        intent.putExtra(BUNDLE_KEY_IMAGES, imageUrl);
        context.startActivity(intent);
    }

    @Override
    public int getResourceId() {
        return R.layout.activity_photoview;
    }

    @Override
    public void initToolBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    @Override
    public void initView() {
        mTouchImageView = (TouchImageView) findViewById(R.id.photoview);
        mTouchImageView.setOnClickListener(this);
        mProgressBar = (ProgressBar) findViewById(R.id.pb_loading);
        mOption = (ImageView) findViewById(R.id.iv_more);
        mOption.setOnClickListener(this);
    }

    @Override
    public void initData() {
        mImageUrl = getIntent().getStringExtra(BUNDLE_KEY_IMAGES);
        new Core.Builder()
                .view(mTouchImageView)
                .url(mImageUrl)
                .errorBitmapRes(R.drawable.load_img_error)
                .bitmapCallBack(new BitmapCallBack() {
                    @Override
                    public void onSuccess(Bitmap bitmap) {
                        super.onSuccess(bitmap);
                        mProgressBar.setVisibility(View.GONE);
                        mTouchImageView.setVisibility(View.VISIBLE);
                        mOption.setVisibility(View.VISIBLE);
                    }
                }).doTask();
    }

    @Override
    public void onClick(int v) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime > 1000) {
            lastClickTime = currentTime;
            if (v == R.id.photoview){
                finish();
            }else if(v == R.id.iv_more){
                 showOptionMenu();
            }
        }
    }

    private void showOptionMenu() {
        final ImageMenuDialog dialog = new ImageMenuDialog(this);
        dialog.show();
        dialog.setCancelable(true);
        dialog.setOnMenuClickListener(new ImageMenuDialog.OnMenuClickListener() {
            @Override
            public void onClick(TextView menuItem) {
                if (menuItem.getId() == R.id.menu1) {  // 保存图片
                    String filePath = Properties.DEFAULT_SAVE_IMAGE_PATH
                            + getFileName(mImageUrl);
                    Core.getKJBitmap().saveImage(PhotoViewActivity.this, mImageUrl, filePath);
                    showToast(getResources().getString(R.string.tip_save_image_suc),0,0);
                } else if (menuItem.getId() == R.id.menu2) {
                    //  不做
                } else if (menuItem.getId() == R.id.menu3) {
                    TDevice.copyTextToBoard(mImageUrl);
                    showToast("已复制到剪贴板",0,0);
                }
                dialog.dismiss();
            }
        });
    }

    private String getFileName(String imgUrl) {
        int index = imgUrl.lastIndexOf('/') + 1;
        if (index == -1) {
            return System.currentTimeMillis() + ".jpeg";
        }
        return imgUrl.substring(index);
    }

}
