### ViewPager
- 类似ListView和RecyclerView：使用适配器模式设计
- PagerAdapter：一般适用于ViewPager内部的实现是View
- FragmentPagerAdapter：继承自PagerAdapter，一般适用于ViewPager内部的实现为Fragment
- FragmentStatePagerAdapter：
1. 和FragmentPagerAdapter类似，两者都会保持当前item和前后一个item的状态
2. 不同点：对滑动过去的页面是否销毁；FragmentPagerAdapter会保留页面状态，并不会完全销毁；FragmentStatePagerAdapter会完全销毁滑过去的item，当滑出来时，会重新初始化页面


