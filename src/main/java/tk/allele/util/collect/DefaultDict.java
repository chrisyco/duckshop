package tk.allele.util.collect;

import com.google.common.base.Supplier;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Wrapper around a Map that uses a Supplier to supply default values for keys
 * that do not exist.
 *
 * <p><strong>This implementation is not thread safe, even if the underlying
 * map is.</strong>
 *
 * @see <a href="http://stackoverflow.com/questions/1786206">Stack Overflow: Is there a Java equivalent of Python's defaultdict?</a>
 */
public class DefaultDict<K, V> implements Map<K, V> {
    final Map<K, V> innerMap;
    final Supplier<? extends V> factory;

    public static <K, V> DefaultDict<K, V> wrap(Map<K, V> innerMap, Supplier<? extends V> factory) {
        return new DefaultDict<K, V>(innerMap, factory);
    }

    public DefaultDict(Map<K, V> innerMap, Supplier<? extends V> factory) {
        this.innerMap = innerMap;
        this.factory = factory;
    }

    @Override
    @SuppressWarnings("unchecked")
    public V get(Object key) {
        V returnValue = innerMap.get(key);
        if (returnValue == null) {
            returnValue = factory.get();
            this.put((K) key, returnValue);
        }
        return returnValue;
    }

    @Override
    public int size() {
        return innerMap.size();
    }

    @Override
    public boolean isEmpty() {
        return innerMap.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return innerMap.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return innerMap.containsValue(value);
    }

    public V put(K k, V v) {
        return innerMap.put(k, v);
    }

    @Override
    public V remove(Object o) {
        return innerMap.remove(o);
    }

    public void putAll(Map<? extends K, ? extends V> map) {
        this.innerMap.putAll(map);
    }

    @Override
    public void clear() {
        innerMap.clear();
    }

    @Override
    public Set<K> keySet() {
        return innerMap.keySet();
    }

    @Override
    public Collection<V> values() {
        return innerMap.values();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return innerMap.entrySet();
    }

    @Override
    public boolean equals(Object o) {
        return innerMap.equals(o);
    }

    @Override
    public int hashCode() {
        return innerMap.hashCode();
    }
}
