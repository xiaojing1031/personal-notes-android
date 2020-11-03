# View 事件体系

## 基础

### 
- View 是android 中所有控件的基类
- ViewGroup: 控件组，包含多个控件，继承于View

### View 位置参数
- 四个顶点：
```
左上角：纵坐标top； 横坐标left
右下角：纵坐标bottom；横坐标right
width = right - left 
height = bottom - top
```

### MotionEvent 和 TouchSlop
- MotionEvent
```
ACTION_DOWN：手指刚接触屏幕
ACTION_MOVE：手指在屏幕上移动
ACTION_UP：手指从屏幕松开的一瞬间
```
- TouchSlop：系统能识别出的被认为是滑动的最小距离
> 常量，和设备有关
> 获取方法： ViewConfiguration.get(getContext()).getScaledTouchSlop()

### VelocityTracker, GestureDetector & Scroller
- VelocityTracker: 追踪手指在滑动中的速度
```
在View的 onTouchEvent方法中追踪当前单击事件的速度
velocityTracker = VelocityTracker.obtain()
velocityTracker.addMovement(event)
```

```
获取速度
velocityTracker.computeCurrentVelocity(100)
xVelocity = velocityTracker.getXVelocity()
yVelocity = velocityTracker.getYVelocity()
```
计算公式：速度 = （终点位置 - 起点位置）/ 时间段 （单位：ms）

```
回收内存
velocityTracker.clear()
velocityTracker/recycle()
```
- GestureDector
> 手势检测：用户的单击、滑动、长按、双击等行为

- Scroller
> 弹性滑动对象

## 滑动
------ 

## 事件分发机制 (MotionEvent的事件分发过程)

```
> 通过三个方法共同完成：
dispatchTouchEvent(ev: MotionEvent): Boolean
onInterceptTouchEvent(ev: MotionEvent): Boolean
onTouchEvent(ev: MotionEvent): Boolean

> 拦截关系（伪代码）
private boolean dispatchTouchEvent（MotionEvent ev) {
    boolean consume = false;
    if (onInterceptTouchEvent(ev)) {
        consume = onTouchEvent();
    } else 
        consume = child.dispatchTouchEvent(ev);
    return consume;
}

```
- 总结
1. Activity对事件的分发： Activity -> Window -> Decor View（底层容器，继承自FrameLayout，父View） -> 子View （通过setContentView设置的View，又称顶级View，根View，一般是ViewGroup）
2. 顶级View 对事件的分发（上述伪代码）
3. 消费顺序： onTouchListener > onTouchEvent > onLongClickListener > onClickListener
4. 子View 可以调用 requestDisallowInterceptTouchEvent 方法，用于阻止父View的 onInterveptTouchEvent拦截事件，使其强制返回false（ACTION_DOWN事件除外）


## 滑动冲突




