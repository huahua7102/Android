package datastructure;

public class HashMap_charCount {

	public static Map<String,Integer> charCount(String text){
		System.out.println(text);
		HashMap<String,Integer> map = new HashMap<String,Integer>(10);
		
		for(int i=0;i<text.length();i++){
			String key =text.substring(i,i+1);
			Integer value = map.get(key);
			int count = value==null?0:value.intValue();
			map.put(key, new Integer(count+1));
		}
		return map;
	}
	
	public static void main(String[] args) {
		String text="AAAABBBBCCCC";
		System.out.println(charCount(text).toString());
	}
}
