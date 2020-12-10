## 自定义 View
### 视图层次结构
![avatar](https://github.com/xiaojing1031/personal-notes-android/raw/main/notes-chart/Android%20%E8%A7%86%E5%9B%BE%E5%B1%82%E7%BA%A7.png)


### MeasureSpec 和 LayoutParams
- 系统内部通过MeasureSpec 来进行View 的测量(measure) 过程
- MeasureSpec代表一个32位int值，高2位代表SpecMode，低30位代表SpecSize
- SpecMode：测量模式
1. UNSPECIFIED
2. EXACTLY
3. AT_MODE
- SpecSize：指在某种测量模式下的规格大小
- MeasureSpec一旦确定，onMeasure中就可以确定View的测量宽高  

| View | MeasureSpec确定方式 |
| :----: | :----: |
| 顶级View (DecorView) | 其MeasureSpec由窗口尺寸和其自身LayoutParams共同决定 |
| 其他View | 其MeasureSpec由父容器MeasureSpec和其自身LayoutParams共同决定 |

- 将View原有的 MeasureSpec 长度缩小到宽度的3/4
```
override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    val widthSize = MeasureSpec.getSize(widthMeasureSpec)
    val newHeightSize = (widthSize * 3F / 4F).toInt()
    super.onMeasure(
        MeasureSpec.makeMeasureSpec(widthSize, MeasureSpec.EXACTLY),
        MeasureSpec.makeMeasureSpec(newHeightSize, MeasureSpec.EXACTLY)
    )
}
```

### View 绘制流程
| 流程 | 作用 |
| :----: | :----: |
| measure 测量 | 系统会先根据xml布局文件和代码中对控件属性的设置，来获取或计算出每个View 和ViewGroup 的尺寸，并将这些尺寸保存下来 |
| layout 布局 | 根据测量出的结果及对应参数，来确定每个控件应该显示的位置|
| draw 绘制 | 确定好位置后，将控件绘制到屏幕上 |


