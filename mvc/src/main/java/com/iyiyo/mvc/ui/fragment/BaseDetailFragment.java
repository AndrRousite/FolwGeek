package com.iyiyo.mvc.ui.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import com.iyiyo.mvc.R;
import com.iyiyo.mvc.cache.CacheManager;
import com.iyiyo.mvc.emoji.OnSendClickListener;
import com.iyiyo.mvc.ui.widget.empty.EmptyLayout;
import com.iyiyo.mvc.utils.TDevice;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.ref.WeakReference;

import cz.msebera.android.httpclient.Header;

/**
 * 通用的详情fragment
 * Created by 火蚁 on 15/5/25.
 */
public abstract class BaseDetailFragment<T extends Serializable> extends BaseFragment implements OnSendClickListener {

    protected EmptyLayout mEmptyLayout;

    protected T mDetail;

    private AsyncTask<String, Void, T> mCacheTask;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public void initView(View view) {
        mEmptyLayout = (EmptyLayout) view.findViewById(R.id.error_layout);
    }

    @Override
    public void initData() {
        requestData(false);
    }

    private void requestData(boolean refresh) {
        String key = getCacheKey();
        if (TDevice.hasInternet()
                && (!CacheManager.isExistDataCache(getActivity(), key) || refresh)) {
            sendRequestDataForNet();
        } else {
            readCacheData(key);
        }
    }

    private void readCacheData(String cacheKey) {
        cancelReadCache();
        mCacheTask = new CacheTask(getActivity()).execute(cacheKey);
    }

    private void cancelReadCache() {
        if (mCacheTask != null) {
            mCacheTask.cancel(true);
            mCacheTask = null;
        }
    }

    protected AsyncHttpResponseHandler mDetailHeandler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            try {
                T detail = parseData(new ByteArrayInputStream(arg2));
                if (detail != null) {
                    mEmptyLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
                    executeOnLoadDataSuccess(detail);
                    saveCache(detail);
                } else {
                    executeOnLoadDataError();
                }
            } catch (Exception e) {
                e.printStackTrace();
                executeOnLoadDataError();
            }
        }

        @Override
        public void onStart() {
            super.onStart();
            mEmptyLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
        }

        @Override
        public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                              Throwable arg3) {
            readCacheData(getCacheKey());
        }
    };

    private class CacheTask extends AsyncTask<String, Void, T> {
        private final WeakReference<Context> mContext;

        private CacheTask(Context context) {
            mContext = new WeakReference<Context>(context);
        }

        @Override
        protected T doInBackground(String... params) {
            if (mContext.get() != null) {
                Serializable seri = CacheManager.readObject(mContext.get(),
                        params[0]);
                if (seri == null) {
                    return null;
                } else {
                    return (T) seri;
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(T detail) {
            super.onPostExecute(detail);
            mEmptyLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
            if (detail != null) {
                executeOnLoadDataSuccess(detail);
            } else {
                executeOnLoadDataError();
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mEmptyLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
        }
    }

    protected void executeOnLoadDataSuccess(T detail) {
        this.mDetail = detail;
        if (this.mDetail == null) {
            executeOnLoadDataError();
            return;
        }
    }

    protected void executeOnLoadDataError() {
        mEmptyLayout.setErrorType(EmptyLayout.NETWORK_ERROR);
        mEmptyLayout.setOnLayoutClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mState = STATE_REFRESH;
                mEmptyLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
                requestData(true);
            }
        });
    }

    protected void saveCache(T detail) {
        new SaveCacheTask(getActivity(), detail, getCacheKey()).execute();
    }

    private class SaveCacheTask extends AsyncTask<Void, Void, Void> {
        private final WeakReference<Context> mContext;
        private final Serializable seri;
        private final String key;

        private SaveCacheTask(Context context, Serializable seri, String key) {
            mContext = new WeakReference<Context>(context);
            this.seri = seri;
            this.key = key;
        }

        @Override
        protected Void doInBackground(Void... params) {
            CacheManager.saveObject(mContext.get(), seri, key);
            return null;
        }
    }

    // 刷新数据
    protected void refresh() {
        sendRequestDataForNet();
    }

    // 获取缓存的key
    protected abstract String getCacheKey();

    // 从网络中读取数据
    protected abstract void sendRequestDataForNet();

    // 解析数据
    protected abstract T parseData(InputStream is);

}
