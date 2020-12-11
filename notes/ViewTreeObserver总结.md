### ViewTreeObserver
- 用来监听全局变化
- ViewTreeObserver 会注册一个观察者来监听视图树，当视图树的布局、焦点、视图绘制、滚动等发生改变时，ViewTreeObserver会收到通知
- ViewTreeObserver 不能被实例化，可以调用View.getViewTreeObserver()来获得
