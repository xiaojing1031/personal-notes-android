## 自定义 View
### 视图层次结构

### MeasureSpec 和 LayoutParams
- 顶级View (DecorView)：其MeasureSpec由窗口尺寸和其自身LayoutParams共同决定
- 其他View：其MeasureSpec由父容器MeasureSpec和其自身LayoutParams共同决定
- MeasureSpec一旦确定，onMeasure中就可以确定View的测量宽高

### View 绘制流程
| 流程 | 作用 |
| :----: | :----: |
| measure 测量 | 系统会先根据xml布局文件和代码中对控件属性的设置，来获取或计算出每个View 和ViewGroup 的尺寸，并将这些尺寸保存下来 |
| layout 布局 | 根据测量出的结果及对应参数，来确定每个控件应该显示的位置|
| draw 绘制 | 确定好位置后，将控件绘制到屏幕上 |


