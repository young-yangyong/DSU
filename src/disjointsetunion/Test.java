package disjointsetunion;

import disjointsetunion.impl.DisjointSetUnionImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Test {
    public static void main(String[] args) {
        ArrayList<List<String>> sets = new ArrayList<>();
        sets.add(Stream.of("1", "2", "3").collect(Collectors.toList()));
        sets.add(Stream.of("4", "5", "6").collect(Collectors.toList()));
        DisjointSetUnionImpl<String> disjointSetUnion = new DisjointSetUnionImpl<>(sets);
        System.out.println(disjointSetUnion);
        System.out.println(disjointSetUnion.find(0));
        disjointSetUnion.union(1, 5);
        System.out.println(disjointSetUnion);
        System.out.println(disjointSetUnion.find(2));
        System.out.println(disjointSetUnion);
        System.out.println(disjointSetUnion.isConnected(0, 1));
        System.out.println(disjointSetUnion.isConnected(0, 5));
    }
}
