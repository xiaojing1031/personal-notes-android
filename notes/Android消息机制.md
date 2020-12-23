## Android 消息机制
- 指Handler 的运行机制
- Handler的运行需要底层的MessageQueue 和Looper 的支撑
- Handler作用：将一个任务切换到某个指定的线程中执行
- MessageQueue：消息队列（采用单链表的数据结构）
- Looper：处理消息队列中的消息
- Looper中ThreadLocal的作用：可以在每个线程中存储数据（通过ThreadLocal可以获取每个线程的Looper）
- 系统提供Handler的原因：线程间切换（解决在子线程中无法访问 UI  的矛盾） --> 访问UI的工作在主线程进行，耗时的操作如IO操作或者从服务端拉取信息需在子线程操作之后再切回主线程以避免ANR(程序 无法响应问题)

- 获取主线程Looper并加入MessageQueue等待运行
```
Handler(Looper.getMainLooper().post(r: Runnable))
```
