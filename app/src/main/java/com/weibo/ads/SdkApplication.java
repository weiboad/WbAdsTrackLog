package com.weibo.ads;

import android.app.Application;
import android.content.Context;
import com.weibo.ads.sdk.WbConfigOptions;
import com.weibo.ads.sdk.WbSDKHelper;

/**
 * @author : wuchao5
 * @date : 2020/11/30 15:34
 * @desciption :Ø
 */
public class SdkApplication extends Application {

  public static Context mContext;

  @Override public void onCreate() {
    super.onCreate();
    mContext = this;
    //app_wm：aedda57a8874dbc3c7c29721ab871ce3
    WbConfigOptions configOptions = new WbConfigOptions("11", "aedda57a8874dbc3c7c29721ab871ce3")
        .setEnablePlay(true)
        //是否联调，debug 版本时传入true；release 版本时，传入 true
        .setDebug(BuildConfig.DEBUG)
        // .setDebug(false)
        //是否在控制台输出日志，可用于观察用户行为日志上报情况，建议仅在调试时使用，release版本请设置为false ！
        .setEnableLog(true);
    WbSDKHelper.init(this, configOptions);
    // WbSDKHelper.setContext(this);
    // WbReportHelper.onEventActivate();
    // AppMonitorHelper.init(this);
  }
}
