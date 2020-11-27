# Android 动画
## 属性动画
- API 11 新特征；可采用nineoldandroids兼容老版本
- 可以对任何对象的属性做动画，不仅仅是View
    - 改变对象的translationY属性
    - 改变对象背景色属性
    - 动画集合：一段时间内，旋转、平移、缩放、透明度都进行改变
- 如果通过xml定义，需定义在 res/anim/目录下

| 类型 | 标签 |
| :----: | :----: |
| ValueAnimator | "<animator>" |
| ObjectAnimator (继承自ValueAnimator) | "<ObjectAnimator>" |
| AnimatorSet (动画集合) | "<set>" (属性：android: ordering = "together" / "sequentially") |

属性动画监听
- AnimatorListener：监听动画开始、结束、取消、重复播放
- AnimatorUpdateListener：监听整个动画过程，每播放一帧，就会被调用一次

属性动画原理：
要求动画作用的对象提供该属性的get和set方法：属性动画根据外界传递的该属性的初始值和最终值，并多次调用set方法使得到的值不断接近最终值
1. 必须有get和set方法
2. set方法对属性所做的改变能够通过某种方法反映出来

针对第2点的解决方法
1. 如果有权限，给对象增加get和set方法
2. 用一个类包装原是对象，间接提供get/set方法
3. 采用ValueAnimator的 AnimatorUpdateListener，监听动画过程，自己实现属性的改变
```
// Eg of changing height of the view (animation to close a layout)
val layoutP = xxx.layoutParams as LayoutParams
val height = xxx.height
ValueAnimator.ofFloat(1f, 0f).apply {
    duration = xx
    interpolator = xx
    addUpdateListener { value -> 
        layoutP.height = ((value.animatedValue as Float) * height).toInt()
        xxx.layoutParams = layoutP
    }
}.start()

// rotation 
ValueAnimator.ofInt(0, 360).apply {
    duration = xx
    repeatCount = xx
    repeatMode = xx
    interpolator = xx
    addUpdateListener { value -> 
        imageView.rotation = ((value.animatedValue as Int) * height).toFloat()
    }
}.start()

```


## View 动画

需定义在 res/anim/目录下
| 名称 | 标签 | 子类 | 
| 平移动画 | <translate> | TranslateAnimation | 
| 缩放动画 | <scale | ScaleAnimation |
| 旋转动画 | <rotate> | RotateAnimation | 
| 透明度动画 | <alpha> | AlphaAnimation |


View动画其他特殊使用场景
- LayoutAnimation (xml)：作用于ViewGroup，控制子元素出场效果 (可通过LayoutAnimationController在代码中实现)
    - android: delay
    - android: animationOrder
    - android: animation

- Activity 切换效果：
    - overridePendingTransition(R.anim.enter, R.anim.exit)
    - 必须在startActivity 或 finish之后调用才会生效
    - Fragment：API 11 新类
        - 在Fragment中添加切换动画：可通过FragmentTransaction中的setCustomAnimations()方法 （用于兼容老版本）


## 帧动画
- 顺序播放一组预先定义好的图片，类似播放电影
- 用AnimationDrawable类来使用
- 尽量避免使用过多大尺寸图片，容易引起OOM




