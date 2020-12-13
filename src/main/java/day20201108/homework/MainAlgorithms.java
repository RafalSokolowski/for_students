package day20201108.homework;

import java.util.Arrays;

public class MainAlgorithms {

    public static final String BLUE = "\u001B[34m";
    public static final String GREEN = "\u001B[32m";
    public static final String RESET = "\u001B[0m";

    public static void main(String[] args) {

        Fish fish1 = new Fish(true, 10, 8, Fish.Color.BLUE, new String[]{"Big Sea"});
        Fish fish2 = new Fish(true, 11, 1, Fish.Color.BLUE, new String[]{"Small Sea"});
        Fish fish3 = new Fish(true, 12, 5, Fish.Color.BLUE, new String[]{"Big Sea"});
        Fish fish4 = new Fish(true, 13, 3, Fish.Color.BLUE, new String[]{"Medium Sea"});
        Fish fish5 = new Fish(true, 14, 1, Fish.Color.BLUE, new String[]{"Big Sea"});
        Fish fish6 = new Fish(true, 14, 9, Fish.Color.BLUE, new String[]{"Big Sea"});

        Fish[] fish = {fish1, fish2, fish3, fish4, fish5, fish6};

        // 0. Original unsorted
        System.out.println(BLUE + "\n0. Fish array to be sort:" + RESET);
        Arrays.stream(fish).forEach(System.out::println);

        // 1. QuickSort by fish weight
        System.out.println(BLUE + "\n1. Sorted by weight with QuickSort:" + RESET);
        Arrays.stream(Algorithms.quickSort(fish, 0, fish.length-1)).forEach(System.out::println);

        // 2. CountingSort by fish weight
        System.out.println(BLUE + "\n2. Sorted by weight with CountingSort:" + RESET);
        Arrays.stream(Algorithms.countingSort(fish, 10)).forEach(System.out::println);

        // 3. InsertionSort
        System.out.println(BLUE + "\n3. Sorted by weight with InsertionSort:" + RESET);
        Arrays.stream(Algorithms.insertionSort(fish)).forEach(System.out::println);

        // 4. BucketSort
        System.out.println(BLUE + "\n4. Sorted by weight with BucketSort:" + RESET);
        Arrays.stream(Algorithms.bucketSort(fish, 10)).forEach(System.out::println);

        // 5. HeapSort
        System.out.println(BLUE + "\n5. Sorted by weight with HeapSort:" + RESET);
        Arrays.stream(Algorithms.heapSort(fish)).forEach(System.out::println);

        // 6. MergeSort
        System.out.println(BLUE + "\n6. Sorted by weight with MergeSort:" + RESET);
        Arrays.stream(Algorithms.mergeSort(fish,fish.length)).forEach(System.out::println);

    }
}
