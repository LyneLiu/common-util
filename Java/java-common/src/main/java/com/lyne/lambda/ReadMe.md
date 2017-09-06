
## Java 8 lambda stream特性
    简单地说，对Stream的使用就是实现一个filter-map-reduce过程，产生一个最终结果，或者导致一个副作用（side effect）.
### 1、生成stream source的方式：
##### Collection和数组
    Collection.stream()
    Collection.parralStream()
    Arrays.stream(T array) or Stream.of()
##### BufferedReader
    java.io.BufferedReader.lines()
##### 静态工厂
    java.util.stream.IntStream.range()
    java.nio.file.Files.walk()
##### 自己构建
    java.util.Spliterator
##### 其他
    Random.ints()
    BitSet.stream()
    Pattern.splitAsStream(java.lang.CharSequence)
    JarFile.stream()

### 2、Stream的操作类型：
        intermediate：一个流可以跟随零个或者多个intermediate操作，其目的主要是打开流，做出某种程度的数据映射/过滤，
     然后返回一个新的流，交给下一个操作使用。这类操作都是惰性化（lazy）的，仅仅调用到这类方法，并没有真正开始流的遍历。
     
     map (mapToInt, flatMap 等)、 filter、 distinct、 sorted、 peek、 limit、 skip、 parallel、 sequential、 unordered
     
        terminal：一个流只能有一个terminal操作，当这个操作执行后，流就被使用“光”了，无法再被操作，所以必定是流的最后一个操作；
     terminal操作的执行，才会真正开始流的遍历，并且会生成一个结果，或者一个side effect。
     
     forEach、 forEachOrdered、 toArray、 reduce、 collect、 min、 max、 count、 anyMatch、 allMatch、 noneMatch、 findFirst、 findAny、 iterator
     
        Short-circuiting：
     
     anyMatch、 allMatch、 noneMatch、 findFirst、 findAny、 limit

### 3、性能问题：
        在对于一个Stream进行多次转换操作，每次都对Stream的每个元素进行转换，而且是执行多次，这样时间复杂度就是一个for循环里把所有操作都做掉的N（转换的次数）倍啊。
    其实不是这样的，转换操作都是lazy的，多个转换操作只会在汇聚操作（见下节）的时候融合起来，一次循环完成。我们可以这样简单的理解，
    Stream里有个操作函数的集合，每次转换操作就是把转换函数放入这个集合中，在汇聚操作的时候循环Stream对应的集合，然后对每个元素执行所有的函数。


### 4、基本数值型的Stream:
    IntStream、LongStream、DoubleStream。
    
参考链接：
https://www.ibm.com/developerworks/cn/java/j-lo-java8streamapi/
