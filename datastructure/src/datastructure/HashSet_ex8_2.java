package datastructure;
//ʹ��ɢ�б��ʾԪ�ػ���ļ���
public class HashSet_ex8_2 {

	public static Integer[] randomDifferent(int n,int size){
		Integer[] values=new Integer[n];
		HashSet<Integer> set = new HashSet<Integer>();
		int i=0;
		while(i<n){
			int key=(int)(Math.random()*size);
			
			if(set.add(key))
				values[i++]=key;
		}
		
		return values;
	}
	
	public static void print(Object[] value){
		for(Object obj:value){
			System.out.print(obj==null?"null":" "+obj.toString());
		}
		System.out.println();
	}
	public static void main(String[] args) {
		int n=10,size=100;
		Integer[] values=randomDifferent(n,size);
		System.out.print(n+"��Ԫ��0��"+size+"֮��Ļ������������: ");
		print(values);
	}
}
