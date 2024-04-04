# 使用 LinkedHashMap 实现
## LinkedHashMap 介绍
### 特性
- 可以按照插入顺序有序遍历
- 可以按照元素访问顺序排序
- 内部使用双向链表
### 使用
#### 1. 遍历
##### 按照插入顺序
直接创建
##### 按照访问顺序
排序模式：accessOrder 属性，true 为按照访问顺序，false 为按照插入顺序  
构造：`LinkedHashMap<Integer, String> map = new LinkedHashMap<>(16, 0.75f, true);`
#### 2. 移除
`removeEldestEntry`方法返回一个 boolean 值，判断是否需要移除链表首元素  
超出容量时返回 true
#### 3. get 方法
`accessOrder`为 true 时，在元素查询完成后，将当前访问的元素移动到链表末尾  
#### 4. put 方法
如果 key 已经存在，则更新 value  
如果 key 不存在，在末尾插入，并判断是否需要移除链表首元素  
## 实现思路
- 指定容量
- 实现 `get`、`put`方法
- 重载`removeEldestEntry`方法，当超过容量时，移除最老的元素

```java
import java.util.LinkedHashMap;
import java.util.Map;

public class LRUcache<K, V> extends LinkedHashMap<K, V> {

    private final int capacity;

    public LRUcache(int capacity) {
        super(capacity, 0.75f, true);
        this.capacity = capacity;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > capacity;
    }

    public static void main(String[] args) {
        LRUcache<Integer, String> cache = new LRUcache<>(3);

        cache.put(1, "One");
        cache.put(2, "Two");
        cache.put(3, "Three");
        cache.get(2);
        cache.put(4, "Four");
        System.out.println(cache);
    }
}
```
# 哈希表 + 双向链表
```java
import java.util.HashMap;
import java.util.Map;

public class LRUCache {

    class DLinkedNode {
        int key;
        int value;
        DLinkedNode prev;
        DLinkedNode next;
        public DLinkedNode() {}
        public DLinkedNode(int _key, int _value) {key = _key; value = _value;}
    }

    private Map<Integer, DLinkedNode> cache = new HashMap<Integer, DLinkedNode>();
    private int size;
    private int capacity;
    private DLinkedNode head, tail;

    public LRUCache(int capacity) {
        this.size = 0;
        this.capacity = capacity;
        // 使用伪头部和伪尾部节点
        head = new DLinkedNode();
        tail = new DLinkedNode();
        head.next = tail;
        tail.prev = head;
    }

    public int get(int key) {
        DLinkedNode node = cache.get(key);
        if (node == null) {
            return -1;
        }
        // 如果 key 存在，先通过哈希表定位，再移到头部
        moveToHead(node);
        return node.value;
    }

    public void put(int key, int value) {
        DLinkedNode node = cache.get(key);
        if (node == null) {
            // 如果 key 不存在，创建一个新的节点
            DLinkedNode newNode = new DLinkedNode(key, value);
            // 添加进哈希表
            cache.put(key, newNode);
            // 添加至双向链表的头部
            addToHead(newNode);
            ++size;
            if (size > capacity) {
                DLinkedNode tail = removeTail();
                // 删除哈希表中对应的项
                cache.remove(tail.key);
                --size;
            }
        }
        else {
            // 如果 key 存在，先通过哈希表定位，再修改 value，并移到头部
            node.value = value;
            moveToHead(node);
        }
    }

    private void addToHead(DLinkedNode node) {
        node.prev = head;
        node.next = head.next;
        head.next.prev = node;
        head.next = node;
    }

    private void removeNode(DLinkedNode node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private void moveToHead(DLinkedNode node) {
        removeNode(node);
        addToHead(node);
    }

    private DLinkedNode removeTail() {
        DLinkedNode res = tail.prev;
        removeNode(res);
        return res;
    }
}
```
