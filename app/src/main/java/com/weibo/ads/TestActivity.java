package com.weibo.ads;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.weibo.ads.sdk.WbConfigOptions;
import com.weibo.ads.sdk.WbReportHelper;
import com.weibo.ads.sdk.WbSDKHelper;
import com.weibo.ads.sdk.oaid.helpers.WbOaidHelper;
import org.json.JSONException;
import org.json.JSONObject;

public class TestActivity extends AppCompatActivity {

  private TextView mTvOaid;
  private EditText et_sdkid;
  private EditText et_http;
  private EditText et_app_wm;
  private boolean isDebug;
  private boolean isHeart;
  private WbConfigOptions mWbConfigOptions;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_test);
    mTvOaid = findViewById(R.id.tv_oaid);
    et_sdkid = findViewById(R.id.et_sdkid);
    et_app_wm = findViewById(R.id.et_app_wm);
    et_http = findViewById(R.id.et_http);
  }

  public void getOAID(View view) {
    WbReportHelper.setOaidListener(new WbOaidHelper.OaidUpdaterListener() {
      @Override public void OnOaidAvalidListener(@NonNull String s) {
        mTvOaid.setText(s);
      }
    });
  }

  public void onClickLogin(View view) {
    WbReportHelper.onEventLogin();
  }

  public void onClickRegister(View view) {
    WbReportHelper.onEventRegister();
  }

  public void onClickCreateRole(View view) {
    WbReportHelper.onEventCreateRole();
  }

  public void onClickPurchase(View view) {
    WbReportHelper.onEventPurchase("666");
  }

  public void onClickDefine(View view) {
    JSONObject paramsObj = new JSONObject();
    try {
      paramsObj.put("video_title", "Lady Gaga on Oscar"); //事件属性 视频标题
      paramsObj.put("duration", 20); //事件属性 播放时⻓长
    } catch (JSONException e) {
      e.printStackTrace();
    }
    WbReportHelper.onEventDefine("video_click", paramsObj);
  }

  public void onClickOpenHeart(View view) {
    isHeart = true;
   /* WbConfigOptions configOptions = new WbConfigOptions("123", "weibo")
        .setEnablePlay(true)
        //是否联调，debug 版本时传入true；release 版本时，传入 true
        .setDebug(BuildConfig.DEBUG)
        //是否在控制台输出日志，可用于观察用户行为日志上报情况，建议仅在调试时使用，release版本请设置为false ！
        .setEnableLog(true);
    WbSDKHelper.init(this, configOptions);
    WbSDKHelper.startHeartBeatService();*/
  }

  public void onClickCloseHeart(View view) {
    isHeart = false;
   /* WbConfigOptions configOptions = new WbConfigOptions("123", "weibo")
        .setEnablePlay(false)
        //是否联调，debug 版本时传入true；release 版本时，传入 true
        .setDebug(BuildConfig.DEBUG)
        //是否在控制台输出日志，可用于观察用户行为日志上报情况，建议仅在调试时使用，release版本请设置为false ！
        .setEnableLog(true);
    WbSDKHelper.init(this, configOptions);
    WbSDKHelper.startHeartBeatService();*/
  }

  //联调 false
  public void onClickJoinDebug(View view) {
    isDebug = false;
  }

  //不联调 true
  public void onClickUnJoinDebug(View view) {
    isDebug = true;
  }

  public void onClickFinish(View view) {
    String sdkid = et_sdkid.getText().toString();
    String appwm = et_app_wm.getText().toString();
    String http = et_http.getText().toString();
    // AppPrefsUtils.putString("url", http);
    WbConfigOptions configOptions = new WbConfigOptions(sdkid, appwm)
        .setEnablePlay(isHeart)
        //是否联调，debug 版本时传入true；release 版本时，传入 true
        .setDebug(isDebug)
        //是否在控制台输出日志，可用于观察用户行为日志上报情况，建议仅在调试时使用，release版本请设置为false ！
        .setEnableLog(true);

    WbSDKHelper.init(SdkApplication.mContext.getApplicationContext(), configOptions);

  }
}