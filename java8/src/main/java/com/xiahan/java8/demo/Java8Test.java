package com.xiahan.java8.demo;

import java.awt.event.ItemEvent;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

/**
 * .主要记录 java 8的一些常见用法
 * 
 * @author xiahan
 * @dateTime 2019年11月21日 下午10:29:34
 */
public class Java8Test {

    public static void main1(String[] args) {
        LocalDate now = LocalDate.now();
        Student student1 = new Student(1, "xiahan1", now);
        Student student2 = new Student(2, "xiahan1", now);
        Student student3 = new Student(1, "xiahan2", now.plusMonths(1));
        Student student4 = new Student(2, "xiahan2", now.minusYears(1));
        Student student5 = new Student(1, "xiahan1", now);
        Student student6 = new Student(1, "xiahan1", now.minusMonths(1));

        List<Student> studentList = Arrays.asList(student1, student2, student3, student4, student5, student6);

        // 遍历数组
//        studentList
////                .forEach(item -> System.out.println(item.toString()))
//                .forEach(System.out::println);
//        ;

        // map student 转换成 student
//		List<Teacher> collect1 = studentList.stream()
//		        .map(item->new Teacher(item.getId(), item.getName()))
//		        .collect(Collectors.toList())
//		        ;
		
		// filter 过滤 filter里面是一个boolean
//        List<Student> collect = studentList.stream()
////                .filter(item -> item.getId() > 1 )
//                .filter(item -> "xiahan".equals(item.getName()))
//                .collect(Collectors.toList());
		
        // limit 前 N 个数据
//        List<Student> collect = studentList.stream()
//                .limit(3)
//                .collect(Collectors.toList());
        
        // sorted 排序 但会结果 1 0 -1 代表正序，倒序
//        List<Student> collect = studentList.stream()
//                .sorted((item1, item2) -> item1.getId() - item2.getId() > 1 ? 1 : 0)
//                .collect(Collectors.toList());
        
        // 并行程序
//        studentList.parallelStream()
//            .forEach(item -> System.out.println(item.toString()));
        
        // 分组 先按照 id 分组 然后按照 name分组
//        Map<Integer, Map<String, List<Student>>> collect = studentList.stream()
//                .collect(Collectors.groupingBy(Student::getId,Collectors.groupingBy(Student::getName)));
    }
    
    public static void main2(String[] args) {
        List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
        
        IntSummaryStatistics stats = numbers.stream().mapToInt((x) -> x).summaryStatistics();
         
        System.out.println("列表中最大的数 : " + stats.getMax());
        System.out.println("列表中最小的数 : " + stats.getMin());
        System.out.println("所有数之和 : " + stats.getSum());
        System.out.println("平均数 : " + stats.getAverage());
        
    }
    
    // 推荐使用 mapToDouble
    public static void main(String[] args) {
        List<Double> list = Arrays.asList(0D, 1D, 1.2, 5.6);
        List<Integer> integers = Arrays.asList(0, 1, 2, 56);
        Double avg = list.stream().collect(Collectors.averagingDouble(Double::doubleValue));
        Double asDouble = list.stream().mapToDouble(Double::doubleValue).average().getAsDouble();
        Double avgs = list.stream().mapToDouble(Double::doubleValue).average().orElse(0D);
        Double intAvg = integers.stream().mapToInt(Integer::intValue).average().orElse(0D);
        Double min = list.stream().min(Double::compareTo).get();
        Double max = list.stream().max(Double::compareTo).get();
        Double sum = list.stream().mapToDouble(Double::doubleValue).sum();
        Double sums = list.stream().reduce(Double::sum).get();
    }

}
