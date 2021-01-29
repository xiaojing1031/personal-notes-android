### Android启动流程
- ...
- attachBaseContext
- Application attach 
- installContentProviders：启动执行各个ContentProvider 的onCreate 方法
- Application onCreate
- Looper.loop
- Activity onCreate, onResume



