## 自定义 View
### ViewRootImpl 和 DecorView
### MeasureSpec 和 LayoutParams
- 顶级View (DecorView)：其MeasureSpec由窗口尺寸和其自身LayoutParams共同决定
- 其他View：其MeasureSpec由父容器MeasureSpec和其自身LayoutParams共同决定
- MeasureSpec一旦确定，onMeasure中就可以确定View的测量宽高

### View 绘制流程
