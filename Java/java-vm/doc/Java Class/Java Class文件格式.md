- 整个Java技术体系结构中，class文件的位置：

![](http://img.blog.csdn.net/20140318212143937?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvemhhbmdqZ19ibG9n/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

- Class文件格式概述

		class文件：8位字节的二进制流文件。每个类（或者接口）都单独占据一个class文件。

		class文件中数据信息为一项一项排列的，每项数据都有它的固定长度，可以占用一个字节、两个字节、四个字节或者8个字节，不同长度分别用u1、u2、u4、u8表示。可以把u1、u2、u4、u8看作class文件数据项的“类型”。


| 类型	        | 名称			|数量
|:-------------:|:-------------:|:-----------:|
|u4				|magic			|1
|u2				|minor_version	|1
|u2				|major_version	|1
|u2				|constant_pool_count	|1
|cp_info		|constant_pool	|constant_pool_count - 1
|u2				|access_flags	|1
|u2				|this_class		|1
|u2				|super_class	|1
|u2				|interfaces_count	|1
|u2				|interfaces		|interfaces_count
|u2				|fields_count	|1
|field_info		|fields			|fields_count
|u2				|methods_count	|1
|method_info	|methods		|methods_count
|u2				|attribute_count	|1
|attribute_info	|attributes		|attributes_count

参考链接：


	http://blog.csdn.net/zhangjg_blog/article/details/21486985