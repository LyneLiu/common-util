
1、问题语句：
ArrayList list = new ArrayList();
// 添加元素
list.add("1");
list.add("2");
list.add("3");
list.add("4");

(String[]) value.toArray();

2、解决办法：
toArray()方法默认是返回Object[]数组，数组不能强制转换。
public Object[] toArray() {
	return Arrays.copyOf(elementData, size);
}

使用泛型数据的方式解决问题：
(String[]) value.toArray(new String[0])

具体实现查看toArray(T[] a)方法：
public <T> T[] toArray(T[] a){}

参考链接：
https://stackoverflow.com/questions/4042434/converting-arrayliststring-to-string-in-java