import java.util.*;
import java.util.stream.Collectors;

public class StreamPractice {
    public static void main(String[] args) {
        List<String> names = List.of("Alice", "Bob", "Charlie", "David");

        // Filter: Select elements from the stream that matches a condition.
        List<String> result = names.stream()
                .filter(name -> name.startsWith("A")) // filter elements that starts with A
                .collect(Collectors.toList()); // collect final result into a list

        // Map: Transforms each element in the stream using a provided function.
        List<Integer> lengths = names.stream()
                .map(String::length) // map/transform each name to its length
                .collect(Collectors.toList());

        // Sorted: Returns a stream with the elements sorted according to their natural order or a custom comparator.
        List<String> sorted = names.stream()
                .sorted() // natural order, ascending by default
                .collect(Collectors.toList());

        List<String>descSorted = names.stream()
                .sorted(Comparator.reverseOrder()) //descending order
                .collect(Collectors.toList());

        List<String>customizedSorted = names.stream()
                .sorted(Comparator.comparingInt(String::length)) // customized comparator that compares the length of each string
                .collect(Collectors.toList());

        // Reduce: Combines all elements in the stream into a single result using a specified accumulation function.
        List<Integer> numbers = List.of(1, 2, 3, 4);
        int sum = numbers.stream()
                .reduce(0, Integer::sum);

        // findFirst / findAny: Find the first or any element from the stream if present
        Optional<String> first = names.stream() // use optional to prevent NullPointerException
                .findFirst();

        // count: count the number of elements (that meets given condition)
        long count = names.stream()
                .filter(name -> name.length() > 3)
                .count();

        // forEach: Performs action for each element in the stream
        names.stream()
                .forEach(System.out::println);

        // groupingBy: Groups elements in the stream according to a classification function, returning a Map.
        Map<Character, List<String>> grouped =
                names.stream()
                        .collect(Collectors.groupingBy(name -> name.charAt(0)));

        // flatMap: Flattens nested streams or collections into a single stream of elements.
        List<List<String>> listOfLists = List.of(
                List.of("a", "b"),
                List.of("c", "d")
        );

        List<String> flat =
                listOfLists.stream()
                        .flatMap(Collection::stream)
                        .collect(Collectors.toList());


        // Check results
        System.out.println(flat);
    }
}
