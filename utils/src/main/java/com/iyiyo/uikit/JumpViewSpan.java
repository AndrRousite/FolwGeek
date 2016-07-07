package com.iyiyo.uikit;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

/**
 * TextView ClickableSpan 点击链接事件 改超链接颜色
 * Created by liu-feng on 2016/7/7.
 * 邮箱:w710989327@foxmail.com
 * https://github.com/Unlm
 */
public class JumpViewSpan extends ClickableSpan{

    private Intent intent;
    private Context context;

    public JumpViewSpan(Intent intent, Context context) {
        this.intent = intent;
        this.context = context;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setColor(0XFF339933);
        ds.setUnderlineText(true);  // 去掉下划线
    }

    @Override
    public void onClick(View widget) {
        context.startActivity(intent);
    }
}
