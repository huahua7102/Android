package datastructure;

//单链表类
public class SinglyList<T> {

	public Node<T> head;
	
	public SinglyList(){
		this.head=new Node<T>();
	}
	public SinglyList(T[] values){
		this();
		Node<T> rear = this.head;
		for(int i=0;i<values.length;i++){
			if(values[i]!=null){
				rear.next=new Node(values[i],null);
				rear=rear.next;
			}
		}
	}
	public boolean isEmpty(){
		return this.head.next==null;
	}
	
	
	//存取
	public T get(int i){		//返回第i個元素
		Node<T> p=this.head.next;
		for(int j=0;p!=null&&j<i;j++){
			p=p.next;
		}
		return (i>=0&&p!=null)?p.data:null;
	}
	
	public String toString(){
		 String str=this.getClass().getName()+"(";          //返回类名
       for (Node<T> p=this.head.next;  p!=null;  p=p.next)//p遍历单链表
       {   str += p.data.toString();
           if (p.next!=null) 
               str += ",";                                //不是最后一个结点时，加分隔符
       }
       return str+")";
	}
	public Node<T> insert(int i,T x){
		if(x==null)
			throw new NullPointerException("x==null");
		Node<T> front =this.head;
		for(int j=0;front.next!=null&&j<i;j++){
			front=front.next;
		}
		front.next=new Node<T>(x,front.next);
		return front.next;
	}
	
	public Node<T> insert(T x){
		return insert(Integer.MAX_VALUE,x);
	}
	
	public T remove(int i){
		Node<T> front=this.head;
		for(int j=0;front.next!=null&&j<i;j++){
			front=front.next;
		}
		if(i>=0&&front.next!=null){
			T old = front.next.data;
			front.next=front.next.next;
			
			return old;
		}
		return null;
	}
	
	public Node<T> search(T key){
		for(Node<T> p=this.head.next;p!=null;p=p.next){
			if(key.equals(p.data))
				return p;
		}
		return null;
	}
	
	public boolean contains(T key){
		return this.search(key)!=null;
	}
	
	public Node<T> insertDifferent(T x){
		Node<T> front =this.head,p=front.next;
		
		while(p!=null&&!p.data.equals(x)){
			front=p;
			p=p.next;
		}
		if(p!=null){
			System.out.println("X="+x+",元素重复，未插入。");
			return null;
		}
		return front.next=new Node<T>(x,null);
	}
	
	public T remove(T key){
		Node<T> front = this.head,p=front.next;
		
		while(p!=null&&!key.equals(p.data)){
			front=p;
			p=p.next;
		}
		if(p!=null){
			front.next=p.next;
			return p.data;
		}
		return null;
	}
	
	 public void clear()                                    //删除单链表所有元素
	    {
	        this.head.next=null;                               //Java自动收回所有结点占用的存储空间
	    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
