
1、BlockingQueue成员
ArrayBlockingQueue：基于数组的阻塞队列实现，无法并行操作；
LinkedBlockingQueue：基于链表的阻塞队列实现，注意初始化容量大小，默认为一个类似无限大的容量（Integer.MAX_VALUE）；
DelayQueue：队列中元素需要实现Delayed接口，只有当其指定的延迟时间到了，才能够从队列中获取到改元素；
PriorityBlockingQueue：基于优先级的阻塞队列（优先级的判断通过构造函数传入的Compator对象来决定）；
SynchronousQueue：
参考链接：
http://tutorials.jenkov.com
http://www.cnblogs.com/jackyuj/archive/2010/11/24/1886553.html
