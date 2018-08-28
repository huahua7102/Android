package datastructure;

public class HashMap<K, V> implements Map<K,V>{
	HashSet<KeyValue<K,V>> set;
	
	public HashMap(int length){
		this.set = new HashSet<KeyValue<K,V>>(length);
	}
	public HashMap(){
		this.set=new HashSet<KeyValue<K,V>>();
	}
	public boolean isEmpty(){
		return this.set.isEmpty();
	}
	public int size(){
		return this.set.size();
	}
	public String toString(){
		return this.set.toString();
	}
	public V get(K key){
		KeyValue<K,V> find=this.set.search(new KeyValue<K,V>(key,null));
		return find!=null?find.value:null;
	}
	
	public V put(K key,V value){
		KeyValue<K,V> kv=new KeyValue<K,V>(key,value);
		if(!this.set.add(kv))
			this.set.search(kv).value=value;
		return value;
	}
	public V remove(K key){
		return this.set.remove(new KeyValue<K,V>(key,null)).value;
	}
	public boolean containsKey(K key){
		return this.get(key)!=null;
	}
	public void clear(){
		this.set.clear();
	}

	
}
