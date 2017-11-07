
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


##  Part 2 特殊字符串 ##



- 特殊字符串是指常量池中的符号引用的数据项，见Part 1。特殊字符串包含三种：类的全限定名，字段和方法的描述符，特殊方法的方法名。

#### 1. 类的全限定名 ####

源文件中的全限定名和class文件中的全限定名不是相同的概念。源文件中的全限定名是包名+类名，包名的各个部分之间，包名和类名之间，使用点号（.）分割，如Object类对应的源文件的全限定名为**<span style="color:red">java.lang.Object</span>**。编译后的class文件中对应的全限定名将点号替换为“/”，即java.lang.Object在class文件中的全限定名为**<span style="color:red">java/lang/Object</span>**.

#### 2. 字段和方法的描述符 ####


class文件中字段和方法的描述符并不会把方法和字段的所有信息全部描述出来（描述符只是一个简单的字符串）。所有的类型在描述符中都有对应的字符或者字符串。



- **<span style="color:blue">基本类型</span>**


|基本数据类型和void类型	|类型的对应字符
|:-------------:|:-------------:|
|byte	|B
|char	|C
|double	|D
|float	|F
|int	|I
|long	|J
|short	|S
|boolean	|Z
|void	|V

	说明：基本上都是以类型的首字符变成大写来对应的，其中long和boolean是特例，long类型在描述符中对应的字符是J，boolean类型在描述符中对应的字符是Z。

- **<span style="color:blue">引用类型</span>**


引用类型在描述符中如何对应呢？引用类型在描述符中是使用一个字符串来表示的。字符串的固定格式：

**<span style="color:red">“L”+类型的全限定名+“;”</span>**

如Object在描述符中对应的字符串是：*Ljava/lang/Object;* ，自定义类型com.example.Person在描述符中的对应字符串是：*Lcom/example/Person;* 。

- **<span style="color:blue">数组类型</span>**

java语言中，数组的元素类型和维度决定了它的类型。如，int[] a声明中，变量a的类型为int[]；在Object[] c声明中，变量c的类型为Object[]。class文件的描述符中，数组类型的每个维度通过“[”表示，数组类型整个类型对应的字符串的固定格式为：

**<span style="color:red">若干个“[”  +  数组中元素类型的对应字符串</span>**

如int[]类型对应的是字符串为：*[I* ，int[][]类型对应的字符串为：*[[I* ，Object[]类型对应的字符串为: *[Ljava/lang/Object;* , Object[][]类型对应的字符串为：*[[Ljava/lang/Object;* 。

#### 3. 特殊方法的方法名 ####

这里的特殊方法指类的构造方法和类型初始化方法（即静态初始化块，也就是说静态初始化块，在class文件中是以一个方法表达的，这个方法同样有方法描述和方法名）。

类的构造方法的方法名使用字符串<init>表示，而静态初始化方法的方法名使用字符串<clinit>表示。除了这两种特殊的方法外，其他普通方法的方法名，和源文件中的方法名相同。


##  Part 3 常量池中格数据类型详解 ##

由Part 1可知，常量池中存在11种数据类型。其中两种比较基础的类型，即**<span style="color:red">CONSTANT_Utf8</span>**和**<span style="color:red">CONSTANT_NameAndType</span>**，这两种类型的数据项会被其他类型的数据项引用，并且CONSTANT_NameAndType类型的数据项（CONSTANT_NameAndType_info）也会引用CONSTANT_Utf8类型的数据项（CONSTANT_Utf8_info）。

#### 1. CONSTANT_Utf8_info ####

一个CONSTANT_Utf8_info是一个CONSTANT_Utf8类型的常量池数据项， 存储的是一个常量字符串。CONSTANT_Utf8_info数据项的存储格式：一个整形的标志位（tag）标识当前数据项的数据类型，数据值是1，占用第一个字节；存储数据项长度的length，占用两个字节；剩下的字节存储数据的value，占用length字节。
![](http://img.blog.csdn.net/20140320202350140?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvemhhbmdqZ19ibG9n/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

如果CONSTANT_Utf8_info存储的字符串是“Hello”，则实际的存储形式为：
![](http://img.blog.csdn.net/20140320203137171?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvemhhbmdqZ19ibG9n/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

CONSTANT_Utf8_info数据项存储的字符串主要包括：
- 程序中的字符串常量
- 常量池所在当前类（包括接口和枚举）的全限定名
- 常量池所在当前类的直接父类的全限定名
- 常量池所在当前类型所实现或继承的所有接口的全限定名
- 常量池所在当前类型中所定义的字段的名称和描述符
- 常量池所在当前类型中所定义的方法的名称和描述符
- 由当前类所引用的类型的全限定名
- 由当前类所引用的其他类中的字段的名称和描述符
- 由当前类所引用的其他类中的方法的名称和描述符
- 与当前class文件中的属性相关的字符串， 如属性名等

通过一个简单的示例，了解一下class文件的CONSTANT_Utf8_info数据项信息，Java的源码文件为Person.java:
	
	package com.lyne.tools;

	public class Person {
	
	    String name;
	
	    Integer age;
	
	    public String getName() {
	        return name;
	    }
	
	    public void setName(String name) {
	        this.name = name;
	    }
	
	    public Integer getAge() {
	        return age;
	    }
	
	    public void setAge(Integer age) {
	        this.age = age;
	    }
	}

通过javac Person.java编译源文件，然后通过javap -verbose Person.class可以获取到常量池数据：

	   #1 = Methodref          #5.#24         // java/lang/Object."<init>":()V
	   #2 = Fieldref           #4.#25         // com/lyne/tools/Person.name:Ljava/lang/String;
	   #3 = Fieldref           #4.#26         // com/lyne/tools/Person.age:Ljava/lang/Integer;
	   #4 = Class              #27            // com/lyne/tools/Person
	   #5 = Class              #28            // java/lang/Object
	   #6 = Utf8               name
	   #7 = Utf8               Ljava/lang/String;
	   #8 = Utf8               age
	   #9 = Utf8               Ljava/lang/Integer;
	  #10 = Utf8               <init>
	  #11 = Utf8               ()V
	  #12 = Utf8               Code
	  #13 = Utf8               LineNumberTable
	  #14 = Utf8               getName
	  #15 = Utf8               ()Ljava/lang/String;
	  #16 = Utf8               setName
	  #17 = Utf8               (Ljava/lang/String;)V
	  #18 = Utf8               getAge
	  #19 = Utf8               ()Ljava/lang/Integer;
	  #20 = Utf8               setAge
	  #21 = Utf8               (Ljava/lang/Integer;)V
	  #22 = Utf8               SourceFile
	  #23 = Utf8               Person.java
	  #27 = Utf8               com/lyne/tools/Person
	  #28 = Utf8               java/lang/Object

其中Utf8为CONSTANT_Utf8类型的数据项。

#### 2. CONSTANT_NameAndType_info ####

一个CONSTANT_NameAndType_info数据项描述了两部分信息，第一部分信息是名称（Name），第二部分信息是类型（Type）。如果Name部分是一个字段名称，Type字段就是相应字段的描述符；如果Name部分表示的是一个方法的名称，那么Type部分就是对应的方法的描述符。CONSTANT_NameAndType_info数据项的存储格式：第一个字节也是tag，值为12；指向常量池中一个CONSTANT_Utf8_info数据项的name_index，占用两个字节；指向常量池中一个CONSTANT_Utf8_info数据项的descriptor_index，占用两个字节。

![](http://img.blog.csdn.net/20140320221730843?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvemhhbmdqZ19ibG9n/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

 	  #24 = NameAndType        #10:#11        // "<init>":()V
	  #25 = NameAndType        #6:#7          // name:Ljava/lang/String;
	  #26 = NameAndType        #8:#9          // age:Ljava/lang/Integer;

参考链接：

	http://blog.csdn.net/zhangjg_blog/article/details/21486985