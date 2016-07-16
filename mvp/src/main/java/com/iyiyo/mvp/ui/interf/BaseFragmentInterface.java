package com.iyiyo.mvp.ui.interf;

import android.view.View;


/**
 * 基类fragment实现接口
 * @author FireAnt（http://my.oschina.net/LittleDY）
 * @created 2014年9月25日 上午11:00:25
 *
 */
public interface BaseFragmentInterface {

	public int getResourceId();
	
	public void initView(View view);
	
	public void initData();

	public void onClick(int v);
}
