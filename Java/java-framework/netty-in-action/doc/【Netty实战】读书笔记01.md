
> 在网络发展初期，需要花很多时间来学习socket的复杂，寻址等等，在C socket 库上进行编码，并需要在不同的操作系统上做不同的处理。
Java 早期版本(1995-2002)介绍了足够的面向对象的糖衣来隐藏一些复杂性，但实现复杂的客户端-服务器协议仍然需要大量的样板代码（和进行大量的监视才能确保他们是对的）。

网络初期的阶段，开发人员需要花费大量的精力实现基础编程。目前Java提供的更多的特性及lib，提高开发人员的开发效率，但是新的问题也是类似的，比如SOA微服务监控系统、Elastic日志监控系统为项目提供监控、trace的协助，快速定位问题、分析问题并解决问题。

> Bootstrapping 有两种类型，一种是用于客户端的Bootstrap，一种是用于服务端的ServerBootstrap。
>
| 分类	  | Bootstrap |  ServerBootstrap |
| :------ | :-------  | :--------- |
| 网络功能	        | 连接到远程主机和端口  | 绑定本地端口
| EventLoopGroup数量	| 1	                 | 2

说明：

1. Bootstrap用来连接远程主机，有1个EventLoopGroup;
一个BootStrap对应一个Client端SocketChannel，即workerGroup只需要有一个线程即可。
	
  	注：通常可以创建过个BootStrap实例实现多个Socket链接，提供Client端的吞吐能力。

2. ServerBootstrap用来绑定本地端口，有2个EventLoopGroup(parentGroup和childGroup)。

![](https://waylau.gitbooks.io/essential-netty-in-action/images/Figure%203.2%20Server%20with%20two%20EventLoopGroups.jpg)


**1.  问题：**
> echo server和echo client的demo中如何优雅地退出netty server呢？
>> kill -9 pid_num or taskkill /f /pid pid_num?
>> 通过client msg关闭netty server？
即在server端接收到固定的消息时执行 ctx.channel().parent().close(); 操作。（https://stackoverflow.com/questions/27467608/netty-how-to-make-server-program-not-to-quit）

> https://www.duzhi.me/article/37.html
> https://stackoverflow.com/questions/27467608/netty-how-to-make-server-program-not-to-quit

参考链接：

https://waylau.gitbooks.io/essential-netty-in-action/GETTING%20STARTED/Asynchronous%20and%20Event%20Driven.html
