当有多个代码分支进行功能开发的时候，通常需要使用git merge命令将代码分支进行合并。

git merge有两种方式，一种是快速合并，另外一种是三方合并。
![](http://upload-images.jianshu.io/upload_images/972306-c0cbace0c872146b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

三方合并稍显复杂点，它会产生一个新的commit结点，并把head指向它。它会先去找这两个要合并分支的最近公有结点，如图中，C3 和 C5的最近公有父结点为C1。然后，git对 C1、C3和C5三个结点进行三方合并产生新结点C6。这里的三方合并，具体来说，就是把 C5相较于C1的 diff 应用到 C3上，最后产生C6 这个commit结点。

解决方案：
1、规范代码提交和分支管理；
2、通过rebase减少分支。

参考链接：
http://www.jianshu.com/p/603186352605
http://www.jianshu.com/p/58a166f24c81