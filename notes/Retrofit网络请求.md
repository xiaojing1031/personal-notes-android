### Retrofit
默认步骤：
1. 创建一个Api接口
2. 创建Retrofit实例
3. 创建Api接口实例
4. 调用Api接口方法，生成Call
5. 通过Call发起同步或异步请求，然后获取返回结果Response

结合Kotlin协程和Moshi
Retrofit + OkHttp + Moshi (Json/Gson)
- OkHttp
1. OkHttp 是关于网络请求的第三方类库，封装了网络请求的get、post等操作的底层实现
- Retrofit
1. Retrofit 是一个RESTful的HTTP网络请求框架，封装了OkHttp的底层实现。它是通过注解配置网络参数的，支持多种数据的解析和序列化（Gson、Json、Moshi、xml等）。和仅使用OkHttp相比，请求接口和数据解析更加简洁了
- Gson和Moshi：
1. json序列化工具
2. 使用：序列化一个java object 到json， 或者将json 映射到java object里面

```
- Api接口 & 实例方法
interface UserApi {
    // suspend modifier on functions for kotlin, allows you to express the asynchrony of Http requests：Retrofit2.6开始内部支持协程，不需要我们做多余处理异步
    // 所以.addCallAdapterFactory(CoroutineCallAdapterFactory())废弃，被suspend取代
    @Get("user/login")
    suspend fun getUser(): Response<UserResp>
    
    fun create(): UserApi {
        return BaseApi.retrofitInstance.create(UserApi::class.java)
    }
}

```
```
-  UserResp数据：Moshi
@JsonClass(generateAdapter = true)
data class UserResp (
    @Json(name = "userId")
    val userId: String?,
    
    @Json(name = "userName")
    val userName: String?
)
```
```
- Retrofit实例
Object BaseApi {
    val retrofitInstance: Retrofit by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { 
        Retrofit.Builder()
            .baseUrl("https:...")
            // Moshi maps json to classes
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
        }
}
- 异步处理
1. The call adapter handles threads -> RxJava
.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
2. 默认情况和Kotlin配合使用协程

```
```
- 发起请求，并获得Response
val userData = UserApi.create().getUser().body()
```


### 主要模块
- retrofit-adapter
- retrofit-converters
- retrofit：3个接口的默认实现

| 接口 | 实现 |
| :----: | :----: |
| Call | 网络请求执行器，用于执行同步或异步的网络请求；内部默认通过OkHttp的Call发起网络请求 |
| CallAdapter | 网络请求适配器，用于吧默认的网络请求执行器的调用形式，适配成在不同平台下的网络请求执行器的调用形式；Rotrofit默认Call；RxjavaCallAdapter添加后，默认为Observable或Flowable |
| Converter | 数据转换器，双向；Api接口方法的参数注解的值和网络请求执行器需要的数据类型之间的转换 |

- retrofit-mock

