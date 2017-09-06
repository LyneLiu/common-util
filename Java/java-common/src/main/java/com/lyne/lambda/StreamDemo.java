package com.lyne.lambda;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * ################################################
 * Java 8 lambda stream特性
 * 1、生成stream source的方式：
 * # Collection和数组
 * Collection.stream()
 * Collection.parralStream()
 * Arrays.stream(T array) or Stream.of()
 * 2、BufferedReader
 * java.io.BufferedReader.lines()
 * 3、静态工厂
 * java.util.stream.IntStream.range()
 * java.nio.file.Files.walk()
 * 4、自己构建
 * java.util.Spliterator
 * 5、其他
 * Random.ints()
 * BitSet.stream()
 * Pattern.splitAsStream(java.lang.CharSequence)
 * JarFile.stream()
 *
 * ################################################
 * Stream的操作类型：
 *      intermediate：一个流可以跟随零个或者多个intermediate操作，其目的主要是打开流，做出某种程度的数据映射/过滤，
 * 然后返回一个新的流，交给下一个操作使用。这类操作都是惰性化（lazy）的，仅仅调用到这类方法，并没有真正开始流的遍历。
 *      terminal：一个流只能有一个terminal操作，当这个操作执行后，流就被使用“光”了，无法再被操作，所以必定是流的最后一个操作；
 * terminal操作的执行，才会真正开始流的遍历，并且会生成一个结果，或者一个side effect。
 *
 * 性能问题：
 * 在对于一个Stream进行多次转换操作，每次都对Stream的每个元素进行转换，而且是执行多次，这样时间复杂度就是一个for循环里把所有操作都做掉的N（转换的次数）倍啊。
 * 其实不是这样的，转换操作都是lazy的，多个转换操作只会在汇聚操作（见下节）的时候融合起来，一次循环完成。我们可以这样简单的理解，
 * Stream里有个操作函数的集合，每次转换操作就是把转换函数放入这个集合中，在汇聚操作的时候循环Stream对应的集合，然后对每个元素执行所有的函数。
 * ################################################
 *
 *
 * Created by nn_liu on 2017/6/6.
 *
 * 参考链接：
 * https://www.ibm.com/developerworks/cn/java/j-lo-java8streamapi/
 */
public class StreamDemo {

    private enum Status {
        OPEN, CLOSED
    }

    private static final class Task {
        private final Status status;
        private final Integer points;

        Task(final Status status, final Integer points) {
            this.status = status;
            this.points = points;
        }

        public Status getStatus() {
            return status;
        }

        public Integer getPoints() {
            return points;
        }

        @Override
        public String toString() {
            return String.format("[%s, %d]", status, points);
        }

    }

    public static void main(String[] args) {

        List<Foo> foos = Arrays.asList(
                new Foo(),
                new Foo(),
                new Foo()
        );

        foos.stream().forEach(p -> p.setName("test"));

        foos.stream().forEach(p-> System.out.println(p.getName()));

        /*flatmap*/
        Stream<List<Integer>> inputStream = Stream.of(
                Arrays.asList(1),
                Arrays.asList(2, 3),
                Arrays.asList(4, 5, 6)
        );
        inputStream.flatMap((childList) -> childList.stream()).forEach(System.out :: print);

        final Collection<Task> tasks = Arrays.asList(
                new Task(Status.OPEN, 30),
                new Task(Status.OPEN, 13),
                new Task(Status.CLOSED, 8)
        );

        final long totalPointsOfOpenTask = tasks
                .stream()
                .filter(e -> e.getStatus() == Status.OPEN)
                .mapToInt(Task::getPoints)
                .sum();
        System.out.println("Total points:" + totalPointsOfOpenTask);


        final double totalPoints = tasks
                .stream()
                .parallel()
                .map(Task::getPoints)
                .reduce(0,Integer :: sum);
        System.out.println( "Total points (all tasks): " + totalPoints );

        /*
         * 使用stream的情况下需要注意数据类型
         * peek to debug
         */
        final Collection<String> result = tasks
                .stream()                                        // Stream< String >
                .mapToInt(Task::getPoints)                     // IntStream
                .asLongStream()                                  // LongStream
                .mapToDouble(points -> points / totalPoints)   // DoubleStream
                .peek(e -> System.out.println("double value: " + e))
                .boxed()                                        // Stream< Double >
                .mapToLong(weigth -> (long) (weigth * 100)) // LongStream
                .peek(e -> System.out.println("weight value: " + e))
                .mapToObj(percentage -> percentage + "%")      // Stream< String>
                .peek(e -> System.out.println("percentage value: " + e))
                .collect(Collectors.toList());                 // List< String >

        System.out.println(result);
    }
}
