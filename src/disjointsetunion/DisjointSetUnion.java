package disjointsetunion;

public interface DisjointSetUnion<E> {
    void union(int index1,int index2);
    int find(int index);
}
