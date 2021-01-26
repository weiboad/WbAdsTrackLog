# WbAdsTrackLog
SDK 具体使用方式请查看： [Demo](https://github.com/weiboad/WbAdsTrackLog/blob/master/app/src/main/java/com/weibo/ads/MainActivity.java)

### 1. SDK 主要功能
- 设备注册/激活
- 手动上报用户行为日志
#### 2. 导入SDK
##### 2.1 手动导入
在 Android Studio 中将 WbAdsTrackLog-1.0.1.aar 引入 libs 文件夹中,并将 aar 文件添加到相应的 build.gradle 文件中

- 将打包出来的 aar 文件加入到 libs 中

- 在 module 的 build.gradle 中与 android{} 平级下加入
```
repositories {
  flatDir {
    dirs 'libs'
  }
}
```
- 在 module 的 build.gradle 中的 dependencies 里加入:
```
// 注意这里加入的名字没有后缀名
implementation(name: 'WbAdsTrackLog-1.0.1', ext: 'aar')
```

- 由于需要网络请求及数据转换，SDK引入了如下三方依赖库：

```
implementation 'com.squareup.okhttp3:okhttp:4.9.0'
implementation 'com.alibaba:fastjson:1.2.73'
```
##### 2.2 远端引用

在项目根目录下的 build.gradle 中添加：
```
allprojects {
  repositories {
    maven { url "https://dl.bintray.com/weiboad/WbAdsTrackLog/" }
  }
}
```
在 app 文件夹下的 build.gradle 文件的 dependencies 中添加：
```
implementation 'com.weiboad:WbAdsTrackLog:1.0.1'
```

#### 3. 初始化SDK
在Application 中 onCreate 中初始化 WbAdsTrackLog。

**注意**：对于适配了Android6.0以上(API >= 23)的 App，建议开发者在获得了动态权限之后，调用 SDK 的初始化代码，否则 SDK 可能受影响。这是因为 SDK 需要获得 android.permission.READ_PHONE_STATE 权限，来读取 IMEI 作为用户标识。
```
public class SdkApplication extends Application {

  @Override public void onCreate() {
    super.onCreate();
    WbConfigOptions configOptions = new WbConfigOptions("your_appid", "your_channel")
        .setEnablePlay(true)
        //是否联调，debug 版本时传入true；release 版本时，传入 true
        .setDebug(BuildConfig.DEBUG)
        //是否在控制台输出日志，可用于观察用户行为日志上报情况，建议仅在调试时使用，release版本请设置为false ！
        .setEnableLog(true);
    WbSDKHelper.init(this, configOptions);
  }
}
```
#### 4.配置心跳事件（时长统计）
需在初始化是手动配置打开心跳事件 play_session 上报，用于 App 时长统计。
```
// 每隔一分钟上报心跳日志
configOptions.setEnablePlay(true);
WbSDKHelper.init(this, configOptions);
```
#### 5.上报行为日志数据
- 必传埋点
投放深度转化须上报 purchase：付费埋点事件，由 SDK 预定义，调用如下接口即可：
```
// 付费
WbReportHelper.onEventPurchase("66");
```
- 其它预定义事件
SDK 提供了3个可选上报的预定义事件接口，如后续投放转化目标为创建角色、登录，则创建角色、登录事件必须上报。
```
// 登录
WbReportHelper.onEventLogin();
// 注册
WbReportHelper.onEventRegister();
// 创建角色
WbReportHelper.onEventCreateRole();
// 付费
WbReportHelper.onEventPurchase("66");
```
- 自定义埋点事件
用户行为日志采用事件（event） + 属性（params）形式，一个事件可以对应多个属性，具体埋点可根据各业务方需求而定，下面是一个视频点击事件和它的两个属性的举例：
```
//自定义埋点上报，在需要上报的地方增加上报调用
 public void onClick(final View v) {
    JSONObject paramsObj = new JSONObject();
     try {
        paramsObj.put("video_title","WEIBO SDK"); //事件属性 视频标题
        paramsObj.put("duration",20); //事件属性 播放时⻓长 
    } catch (JSONException e) { 
        e.printStackTrace();
    }
    WbReportHelper.onEventDefine("video_click", paramsObj);
}
```
