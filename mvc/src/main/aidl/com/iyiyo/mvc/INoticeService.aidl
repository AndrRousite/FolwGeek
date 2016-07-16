// INoticeService.aidl
package com.iyiyo.mvc;

// Declare any non-default types here with import statements

interface INoticeService {
    void scheduleNotice();
           void requestNotice();
           void clearNotice(int uid,int type);
}
