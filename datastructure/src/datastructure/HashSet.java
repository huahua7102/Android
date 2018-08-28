package datastructure;


public class HashSet<T> {
	
	private SinglyList<T>[] table;
	private int count=0;
	private static final float LOAD_FACTOR=0.75f;
	
	public HashSet(int length){
		if(length<10)
			length=10;
		this.table =new SinglyList[length];
		for(int i=0;i<this.table.length;i++){
			this.table[i]=new SinglyList<T>();
		}
	}
	
	public HashSet(){
		this(16);
	}
	
	public HashSet(T[] values){
		
	}
	
	private int hash(T x){
		int key=Math.abs(x.hashCode());
		return key%this.table.length;
	}
	
	public T search(T key){
		Node<T> find = this.table[this.hash(key)].search(key);//在单链表中查找关键字为key元素
		return find==null?null:find.data;
	}
	
	public boolean add(T x){
		if(this.count>=this.table.length*LOAD_FACTOR){
			SinglyList<T>[] temp =this.table;
			this.table=new SinglyList[this.table.length*2];
			for(int i=0;i<temp.length;i++){
				this.table[i]=new SinglyList<T>();
			}
			this.count=0;
			for(int i=0;i<temp.length;i++){
				for(Node<T> p=temp[i].head.next;p!=null;p=p.next){
					this.add(p.data);
				}
			}
		}
		
		boolean insert = this.table[this.hash(x)].insertDifferent(x)!=null;
		if(insert)
			this.count++;
		return insert;
	}
	
	public T remove(T key){
		T x=this.table[this.hash(key)].remove(key);
		if(x!=null)
			this.count--;
		return x;
	}
	
	 public String toString(){                            //返回散列表所有元素的描述字符串 {
	        String str=this.getClass().getName()+"(";
	        boolean first=true;
	        for (int i=0; i<this.table.length; i++)            //遍历各同义词单链表
	            for (Node<T> p=this.table[i].head.next;  p!=null;  p=p.next)
	            {
	                if (!first)
	                    str += ",";
	                first=false;
	                str += p.data.toString();
	            }
	        return str+")";
	    }
	 public int size(){
		 return count;
	 }
	 public boolean isEmpty(){
		 return this.size()==0;
	 }
	 
	 public void clear(){
		 for(int i=0;i<this.table.length;i++){
			 this.table[i].clear();
		 }
	 }
	

}
