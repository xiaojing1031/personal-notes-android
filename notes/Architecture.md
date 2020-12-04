# Architecture
## MVP
- 命令式M-V链接
- 通过接口（Presenter）隔离数据与显示，数据的变动通过接口回调方法去通知界面更新
- 缺点：M-V连接紧密，导致Presenter复用比较困难

## 响应式MVVM
- 使用观察者方式隔离数据与显示（View通过观察LiveData来驱动界面更新）
- 将View抽离为观察者，可实现响应式MVVM架构（View本身不是响应式的）





## MvRx真响应式MVVM
- 放弃LiveData，使用State通知View层数据的改变
- 4个概念：State、ViewModel、View、Async
