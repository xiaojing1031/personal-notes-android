# 设计模式

## 结构型模式
### 适配器模式
定义：将一个类的接口变成客户端所期待的另一个接口，从而使原本因接口不匹配而无法在一起工作的的两个类能够在一起工作

2种模式：类适配器、对象适配器

角色：
- Adapter适配器接口：目标角色，定义把其他类转换为我们期待的接口
- Adatptee被适配角色：源角色，一般是已存在的类，需要适配新的接口
- ConcreteAdapter具体适配器：实现适配器接口，把源角色接口转换为目标角色期待的接口

例子：
1. RecyclerView的内部类Adapter：充当适配器接口，目标角色；通过定义的3个override方法获得它需要的接口和数据等
2. xxAdapter：具体适配器；通过它把不同布局和RecyclerView联系起来
3. 各种不同的item布局：源角色
```
Android RecyclerView 中的 xxAdapter类的代码
class xxAdapter(val dataList: List<XX>): RecyclerView.Adapter<xxAdapter.ViewHolder>() {
    
    // 创建自定义的ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: int): ViewHolder {
        // 实例化要展示的view布局
    }
    
    // 绑定ViewHolder
    override fun onBindViewHolder(holder: ViewHolder, pos: int) {
        // ViewHolder中的view与数据进行绑定
    }
    
    override fun getItemCount() = data.size()

    
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        // 根据不同ViewHolder，持有View中不同的id对象
    }
}
```



## 创建型模式
### 单例模式
### 工厂模式

## 行为型模式
### 策略模式
### 观察者模式
### 命令模式

## 六大原则
- 单一职责原则
- 开闭原则
- 里氏替换原则
- 依赖倒置原则
- 接口隔离原则
- 迪米特原则
