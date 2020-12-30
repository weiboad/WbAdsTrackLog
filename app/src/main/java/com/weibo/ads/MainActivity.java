package com.weibo.ads;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.weibo.ads.sdk.WbReportHelper;
import com.weibo.ads.sdk.oaid.helpers.WbOaidHelper;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

  private TextView mTvOaid;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    mTvOaid = findViewById(R.id.tv_oaid);

  }

  /**
   * OAID 获取监听
   */
  public void getOAID(View view) {
    WbReportHelper.setOaidListener(new WbOaidHelper.OaidUpdaterListener() {
      @Override public void OnOaidAvalidListener(@NonNull String s) {
        mTvOaid.setText(s);
      }
    });
  }

  /**
   * 登录 行为 监听
   */
  public void onClickLogin(View view) {
    WbReportHelper.onEventLogin();
  }

  /**
   * 注册 行为 监听
   */
  public void onClickRegister(View view) {
    WbReportHelper.onEventRegister();
  }

  /**
   * 创建角色 行为 监听
   */
  public void onClickCreateRole(View view) {
    WbReportHelper.onEventCreateRole();
  }

  /**
   * 付费 行为 监听
   */
  public void onClickPurchase(View view) {
    WbReportHelper.onEventPurchase("666");
  }

  /**
   * 自定义 行为 监听
   */
  public void onClickDefine(View view) {
    JSONObject paramsObj = new JSONObject();
    try {
      paramsObj.put("video_title", "WEIBO SDK"); //事件属性 视频标题
      paramsObj.put("duration", 20); //事件属性 播放时⻓长
    } catch (JSONException e) {
      e.printStackTrace();
    }
    WbReportHelper.onEventDefine("video_click", paramsObj);
  }
}