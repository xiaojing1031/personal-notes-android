# View 事件体系


## 1. 基础

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
    - 常量，和设备有关
    - 获取方法： ViewConfiguration.get(getContext()).getScaledTouchSlop()

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
    - 手势检测：用户的单击、滑动、长按、双击等行为

- Scroller
    - 弹性滑动对象

------ 

## 2. 滑动3种实现方式
- 三种实现方式
1. View 本身提供的scrollTo / scrollBy 方法
2. 通过动画给View 施加平移效果实现滑动
3. 通过改变View 的LayoutParams 使得View重新布局从而实现滑动

### 使用 scrollTo / scrollBy
| 关键字 | 作用 |
| :----: | :----: |
| scrollBy(int x, int y) | 基于当前位置的相对滑动<br>内部调用scrollTo(mScroller+x, mScroller+y)方法 |
| scrollTo(int x, int y) | 基于所传参数的绝对滑动<br>x > 0，向左滑；y > 0，向上滑 |
| mScrollX | View内容相对于View本身左边缘水平上的距离，向左滑动mScrollX为正<br>通过getScrollX() 获得|
| mScrollY | View内容相对于View本身上边缘垂直上的距离，向上滑动mScrollY为正<br>通过getScrollY() 获得|
| mScrollX=0，mScrollY=0 或 scrollTo(0, 0) | View内容和View完全重合 (原始状态) |

### 使用动画
- 主要操作View的 translationX 和 translationY 属性
- 既可采用传统View 动画，也可采用属性动画 [View 动画具体查看](https://github.com/xiaojing1031/personal-notes-android/blob/main/Android%20%E5%8A%A8%E7%94%BB.md#android-%E5%8A%A8%E7%94%BB)
- View 动画是对View 的影像做操作，并不能真正改变View 的位置参数，包括宽 / 高
- 如需保留动画后的状态，fillAfter 的属性需要设置为true
- 因为本身位置参数未变，如果View 有设置点击事件，新位置无法触发onClick 事件

| 属性 | 示例 | 
| :----: | :----: |
| translationX |  向右平移：toXDelta: "100"; 向左平移：toXDelta: "-100"|
| translationY |  向下平移：toYDelta: "100"; 向上平移：toYDelta: "-100"  |
| 属性动画向右平移 | ObjectAnimator.ofFloat(targetView, "trranslationX", 0, 100).setDuration(100).start() |

- 传统动画向右平移
```
<translate 
    android:duration="100"
    android:fromXDelta="0"
    android:toXDelta = "100"
    android:interpolator="xxx"
/>
```

### 改变布局参数
- View 向右平移100px示例：
```
val layoutParams = xxx.layoutParams as MarginLayoutParams
layoutPrams.width += 100
layoutParams.rightMargin += 100
xxx.layoutParams = layoutParams
```

------ 

## 3. 弹性滑动
- 使用View 的scrollTo / scrollBy 进行滑动时，其过程是瞬间的；使用弹性滑动通过Scroller来实现有过度效果的滑动
- 统一思想：将一次大的滑动分成若干次小的滑动并在一段时间内完成
- 具体方法有多种： Scroller、Handler#postDelayed、Thread#sleep

### Scroller
- Scroller本身无法让View 弹性滑动，需借助View 的compteScroll方法
1. 重写compteScroll()：获得Scroller的curX和curY，然后通过scrollerTo方法实现滑动；调用postInvalidate() 进行二次重绘，循环至滑动过程结束
2. 通过View 的invalidate() 导致View 重绘
3. 在View 的draw方法会调用computeScroller

### [通过动画](https://github.com/xiaojing1031/personal-notes-android/blob/main/notes/Android%20%E5%8A%A8%E7%94%BB.md#android-%E5%8A%A8%E7%94%BB)

### 使用延迟策略
- 通过发送一系列延迟消息达到渐近式效果
- 使用Handler 或View 的postDelayed 方法
1. 不断延迟发送消息，在消息中进行View 的滑动
- 使用线程的sleep方法
1. 在while循环中不断滑动View 和Sleep

------ 

## 4. 事件分发机制 (MotionEvent的事件分发过程)

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

<img src="https://github.com/xiaojing1031/personal-notes-android/raw/main/notes-chart/%E7%BB%98%E5%9B%BE%E7%AC%94%E8%AE%B0%E8%B5%84%E6%BA%90/View%20%E4%BA%8B%E4%BB%B6%E5%88%86%E5%8F%91%E6%B5%81%E7%A8%8B%E5%9B%BE.png" align="center" width="700">

- 总结
1. Activity对事件的分发： Activity -> Window -> Decor View（底层容器，继承自FrameLayout，父View） -> 子View （通过setContentView设置的View，又称顶级View，根View，一般是ViewGroup）
2. 顶级View 对事件的分发（上述伪代码）
3. 消费顺序优先级： onTouchListener (调用setOnTouchListener() 时必须覆盖onTouch()的返回值 > onTouchEvent(内部onLongClickListener , onClickListener一个为true，onTouchEvent()就会返回true消耗此事件)
4. 子View 可以调用 requestDisallowInterceptTouchEvent 方法，用于阻止父View的 onInterveptTouchEvent拦截事件，使其强制返回false（ACTION_DOWN事件除外）

------ 

## 5. 滑动冲突
- 常见场景
    - 外部、内部滑动不一致
    - 外部、内部滑动一致
    - 上两种情况嵌套
- ViewPager作用：内部做过滑动处理，配合Fragment，可实现左右切换页面，且每个页面内又可上下滑动 （解决场景1）
- 不采用ViewPager时：
1. 外部拦截法：重写父容器onInterceptTouchEvent方法，在MotionEvent.ACTION_MOVE下做拦截（不能在 ACTION_DOWN 做拦截）
2. 内部拦截法：父容器不拦截任何事件，全传递给子元素。如果子元素需要则直接消耗掉，否则就交给父容器。需重写子元素dispatchTouchEvent方法，并结合parent.requestDisallowInterceptTouchEvent(true/false)

- 父容器不能拦截ACTION_DOWN的原因是因为ACTION_DOWN事件不受 FLAG_DISALLOW_INTERCEPT这个标记位的控制；所以一旦父容器拦截此动作，那么所有事件都无法传递到子元素

------ 
