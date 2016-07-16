package com.iyiyo.mvp.ui.interf;

import android.view.View;

/**
 * 
 * @author deyi
 *
 */
public interface BaseActivityInterface {

	public int getResourceId();

	public void initToolBar();

	public void initView();

	public void initData();

	public void onClick(int v);
	
}
