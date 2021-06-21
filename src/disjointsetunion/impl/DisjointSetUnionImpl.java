package disjointsetunion.impl;

import disjointsetunion.DisjointSetUnion;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 并查集
 *
 * @param <E> 并查集中元素类型
 */
public class DisjointSetUnionImpl<E> implements DisjointSetUnion<E> {
    /**
     * 每个元素代表的Node节点
     */
    List<Node<E>> parent;
    /**
     * 每个Node节点的秩，即每个结点以自己为根节点的森林的深度
     * （每个元素的秩并不是准确的）
     */
    List<Integer> rank;

    public DisjointSetUnionImpl() {
        parent = new ArrayList<>();
        rank = new ArrayList<>();
    }

    public DisjointSetUnionImpl(List<List<E>> sets) {
        init(sets);
    }

    public boolean add(E e) {
        rank.add(1);
        return parent.add(new Node<>(parent.size(), e));
    }

    public Node<E> get(int index) {
        return parent.get(index);
    }

    public int indexOf(E element) {
        return parent.indexOf(new Node<>(0, element));
    }

    public int lastIndexOf(E element) {
        return parent.lastIndexOf(new Node<>(0, element));
    }


    public void clear() {
        parent.clear();
        rank.clear();
    }

    /**
     * 批量初始化方法
     */
    public void init(List<List<E>> sets) {
        parent = new ArrayList<>();
        rank = new ArrayList<>();
        for (List<E> set : sets) {
            if (set == null)
                continue;
            if (set.isEmpty())
                continue;
            Node<E> first = new Node<>(parent.size(), set.remove(0));
            parent.add(first);
            int root = rank.size();
            rank.add(1);
            for (E element : set) {
                parent.add(new Node<>(parent.size(), first, element));
                rank.add(1);
                rank.set(root, rank.get(root) + 1);
            }
        }
    }

    /**
     * 合并index1索引对应的Node结点的集合和index2索引对应的Node结点的集合
     */
    @Override
    public void union(int index1, int index2) {
        int rootIndex1 = find(index1);
        int rootIndex2 = find(index2);
        if (rootIndex1 == rootIndex2)
            return;
        /*
         * 按秩合并
         */
        int root1 = rank.get(rootIndex1);
        int root2 = rank.get(rootIndex2);
        if (root1 <= root2)
            parent.get(rootIndex1).prev = parent.get(rootIndex2);
        else
            parent.get(rootIndex2).prev = parent.get(rootIndex1);
        if (root1 == root2)
            rank.set(rootIndex2, rank.get(rootIndex2) + 1);
    }

    /**
     * 查询index1索引对应的Node结点所在的集合的根节点的索引值
     *
     * @return index1索引对应的Node结点所在的集合的根节点的索引值
     */
    @Override
    public int find(int index) {
        rangeCheck(index);
        Node<E> node = parent.get(index);
        /*
         * 路径压缩
         *
         */
        if (node != node.prev)
            node.prev = parent.get(find(node.prev.id));
        return node.prev.id;
    }

    /**
     * 判断两个元素是否在同一集合中
     *
     * @return true表示两个元素在同一个集合中
     */
    public boolean isConnected(int index1, int index2) {

        return find(index1) == find(index2);
    }

    @Override
    public String toString() {
        return parent.toString() + "\n" + rank.toString();
    }

    private void rangeCheck(int index) {
        int size = parent.size();
        if (index >= size)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
    }

    /**
     * 封装并查集中每个元素的Node结点
     *
     * @param <E> 元素类型
     */
    private static class Node<E> {
        int id;
        E item;
        Node<E> prev;

        Node(int id, Node<E> prev, E element) {
            this.id = id;
            this.item = element;
            this.prev = prev;
        }

        Node(int id, E element) {
            this.id = id;
            this.item = element;
            this.prev = this;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "id=" + id +
                    ", item=" + item +
                    ", prev→" + prev.id +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node<?> node = (Node<?>) o;
            return Objects.equals(item, node.item);
        }

    }
}
