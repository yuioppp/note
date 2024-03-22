
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
