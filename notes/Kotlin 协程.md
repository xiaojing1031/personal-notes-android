## Kotlin 协程
- 轻量级线程（线程内自主可控的任务集，不需要等待系统切换线程进行调度，从而轻量化线程的操作）
- 采用非阻塞式挂起的协程概念：当协程任务挂起时，不会阻塞协程其他任务，也不会阻塞当前线程的运行

### suspend修饰的函数比普通函数多两个操作：
1. suspend：暂停当前协程的执行，保存所有的局部变量
2. resume：从协程被暂停的地方继续执行协程

### 启动协程的相关函数
| 函数 | 作用 | 
| :----: | :----: |
| launch、async | 启动一个新协程 |
| withContext | 不启动新协程，在原来的协程中切换线程，需传入一个CoroutineContext对象 | 
| coroutineScope、supervisorScope | 启动新协程，使用父协程的job |

### CoroutineScope（一个接口）
1. 是一个作用范围，可以通过CorouutineScope的扩展函数创建一个协程。
    viewModelScope、lifecycleScope：当作用范围被取消，它内部的协程也会被取消  
    GlobalScope：全局性的，无法通过自身取消内部协程  
2. 内部方法lauch函数：返回一个Job对象，可通过Job管理协程   
    函数内参数：   
    - CoroutineContext：上下文
    - CoroutineStart：协程启动模式
    - suspend CoroutineScope.() -> Unit 协程体
    
### CoroutineContext
1. 是一个数据集合接口声明，可以通过它拿到Job、Dispatcher调度器等数据
2. CoroutineContext 定义集合的能力，CombinedContext 是CoroutineContext 集合能力的具体实现，这个实现是一个左向链表

### CoroutineStart 启动模式
| 4种模式 | 作用 | 
| :----: | :----: |
| DEFAULT | 立即调度，可以在执行前被取消 |
| LAZY | 需要时才启动，需要start、join等函数触发才可进行调度 | 
| ATOMIC | 立即调度，协程肯定会执行，执行前不可以被取消 |
| UNDISPATCHED | 立即在当前线程执行，直到遇到第一个挂起点（可能切线程）| 

### suspend CoroutineScope.() -> Unit 协程体
1. lambada 表达式，协程中要执行的代码块，即lauynch函数闭包中的代码
```
CoroutineScope.launch(Dispatchers.Default) {
    // 创建一个协程并启动它
        // 闭包内为协程体
}

```

### Dispatchers调度器
1. 协程中提供的线程调度器，用来切换线程，指定协程所运行的线程
| 4种类型 | 作用 | 
| :----: | :----: |
| DEFAULT | 默认调度器，适合CPU密集型任务调度器 比如逻辑计算 |
| Main |  UI调度器 | 
| IO | IO调度器，适合IO密集型任务调度器 比如读写文件，网络请求等 |
| Unconfined | 无限制调度器，对协程执行的线程不做限制，协程恢复时可以在任意线程 | 

2. 所有调度器都是CoroutineDispatcher子类，CoroutineDispatcher 是一个抽象类
3. CoroutineDispatcher 继承自 AbstractCoroutineContextElement (Element接口的一个抽象实现类)；Element 又实现CoroutineContext 接口。所以调度器本身既是一个CoroutineContext，也可以作为CoroutineContext集合的元素存放其中。
4. CoroutineDispatcher还实现ContinuationInterceptor接口，也就是说调度器本质也是一个拦截器，所有的调度器都是继承这个类来实现自身的调度逻辑


### 小结
1. 协程初衷是为解决并发问题，并发常用场景是多线程；单线程中的协程总执行时间并不会比不用协程小
2. kotlin协程通过 挂起 的机制实现异步操作
