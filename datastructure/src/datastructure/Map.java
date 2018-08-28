package datastructure;
//Ó³Éä½Ó¿Ú
public interface Map<K,V> {
	
	boolean isEmpty();
	int size();
	String toString();
	V get(K key);
	V put(K key,V value);
	V remove(K key);
	boolean containsKey(K key);
	void clear();
	/*Object[] values();*/
	

}
