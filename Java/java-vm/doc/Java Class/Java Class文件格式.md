
##  Part 1 概述 ##

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

	claass文件数据项说明：
	1. magic
	位于class文件开头的四个字节， 这个魔数是class文件的标志，并且是一个固定的值： 0XCAFEBABE。 如果开头四个字节不是0XCAFEBABE， 那么就说明它不是class文件， 不能被JVM识别。
	2. minor_version 和 major_version
	minor_version和major_version占用4个字节，表示class文件的次版本号和主版本号。一般情况下，高版本的JVM能识别低版本的javac编译的class文件。如果使用低版本的JVM执行高版本的class文件，JVM会抛出异常：java.lang.UnsupportedClassVersionError。
	3. 位于版本号后面的是常量池相关的数据项。
	常量池中存放文字字符串、常量值、当前类的类名、字段名、方法名、各个字段和方法的描述符、对当前类的字段和方法的引用信息、当前类中对其他类的引用信息等等。常量池中几乎包含类中的所有信息的描述， class文件中的很多其他部分都是对常量池中的数据项的引用，比如后面要讲到的this_class, super_class, field_info, attribute_info等， 另外字节码指令中也存在对常量池的引用， 这个对常量池的引用当做字节码指令的一个操作数。  此外， 常量池中各个项也会相互引用。
	
|常量池中数据项类型	|类型标志	|类型描述
|:-------------:|:-------------:|:-----------:|
|CONSTANT_Utf8	|1	|UTF-8编码的Unicode字符串
|CONSTANT_Integer	|3	|int类型字面值
|CONSTANT_Float	|4	|float类型字面值
|CONSTANT_Long	|5	|long类型字面值
|CONSTANT_Double	|6	|double类型字面值
|CONSTANT_Class	|7	|对一个类或接口的符号引用
|CONSTANT_String	|8	|String类型字面值
|CONSTANT_Fieldref	|9	|对一个字段的符号引用
|CONSTANT_Methodref	|10	|对一个类中声明的方法的符号引用
|CONSTANT_InterfaceMethodref	|11	|对一个接口中声明的方法的符号引用
|CONSTANT_NameAndType	|12	|对一个字段或方法的部分符号引用

参考链接：


	http://blog.csdn.net/zhangjg_blog/article/details/21486985