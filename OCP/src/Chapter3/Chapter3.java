package Chapter3;
import java.io.IOException;
import java.util.*;
import java.util.function.*;

//class generic<?>{} DOES NOT COMPILE
//class Generic1<T extends B>{} DOES NOT COMPILE, since B is not a defined class
//class generic2<? extends Exception>{}  DOES NOT COMPILE

class GenericTrick<IOException>{} //A EVIL TRICK!!!!!!!!!!!!!!!!
class Generic<T extends B, B>{// the parameter you are passing in, a T must be a subclass of, or implements B	
	public Generic(T t, B b) {}} 

//class Generic2<X super B, B>{}//DOES NOT COMPILE

class Generic5<X extends B, B>{}

interface Shippable <T>{ };

class Generic3 implements Shippable<Exception>{}//NO LONGER A GENERIC CLASS

class Generic4<U> implements Shippable<U> {}

class Generic6 implements Shippable{}// warning here

interface myFunctionalInterface {Chapter3 getChapter3(String id, String name);}

public class Chapter3 implements Comparable<Chapter3>{
	private String id;
	private String name;
	
	Chapter3(String id, String name){
		this.id = id;
		this.name = name;
	}
	//static  <?> void myMethod() {} // DOES NOT COMPILE
	public static <T> void main(String args[]) {
		Generic<NullPointerException, Exception> tg1 = new Generic<>(new NullPointerException("a"), new Exception("b")); // take a look on this !!!
		//Generic3<Exception> tg3 = new Generic3<>();//DOES NOT COMPILE Generic3 is no longer a generic class
		GenericTrick<String> tg2 = new GenericTrick<>();
		ArrayDeque<String> tc1 = new ArrayDeque<>();
		//tc1.element();Throw NoSuchElementException
		//tc1.remove();Throw NoSuchElementException
		//tc1.pop();Throw NoSuchElementException
		
		List<String> tl1 = new LinkedList<>();
		tl1.add(null);//LinkedList allows to add null
		
		Map<String, String> tc2 = new HashMap<>();
		tc2.put("a", "b");
		tc2.put(null, null);
		System.out.println(tc2);
		List<Exception> tc3 = new ArrayList<Exception>();
		tc3.add(new IOException("aaa"));
		
		printMessage(new Exception("exception"));
		printMessage(new IOException("IOexception"));
		Chapter3.<NullPointerException>printMessage(new NullPointerException("IOexception"));
		//<NullPointerException>printMessage(new NullPointerException("IOexception")); DOES NOT COMPILE
		//Chapter3.<RuntimeException>printMessage(new Exception("IOexception"));DOES NOT COMPILE
		
		Set<String> tc4 = new HashSet<>();
		tc4.add("first");
		tc4.add("second");
		tc4.add("thrid");
		tc4.forEach(s -> System.out.println(s));
		
		//ArrayList<int> tt; DOEST NOT COMPILE 
		
		String[] tc5 = {"a", "a", "b", "c"};
		Set<String> tc6 = new HashSet<>(Arrays.asList(tc5));
		System.out.println("convert list to set : " + tc6);
		printMessage4(new Exception());// call a static generic method normally
		Chapter3.<Exception>printMessage4(new Exception());// call it fuckabley
		
		//List<B extends Exception> tc7 = new ArrayList<>();//Does NOT COMPILE
		
		//static Generic4<Exception> tc7 = new Generic4<>(); //DOES NOT COMPILE if using generic on static varilable 
		List tc8 = new ArrayList<>();
		tc8.add("abc"); 
		//receiveRawCollection(tc8);/pass a raw collection into a generic method may cause exception.
		
		List<Exception> tc9 = new ArrayList<>();
		tc9.add(new IOException("aaa"));//You can add an IOException since IOException "is-a" Exception.
		//List<B extends Number> tc10;//DOES NOT COMPILE
		
		List<String> tc10 = new ArrayList<>();
		tc10.add("first");
		tc10.add("second");
		tc10.add("third");
		List<? extends String> tc11 = new ArrayList<String>(tc10);
		//tc11.add("100");DOES NOT COMPILE, tc11 is immutable, but you can still remove it, tc11.remove(1); compiles
		//tc11.set(1, "setFirest");DOES NOT COMPILE, tc11 is immutable,
		
		List<? super Exception> tc12 = new ArrayList<>();
		Iterator tc12iter = tc12.iterator();//warning here
		while(tc12iter.hasNext()) {
			//Exception iE = tc12iter.next();//DOES NOT COMPILE if no cast.
		}
		
		Set<Integer> tc13 = new HashSet<>();
		tc13.add(1);
		
		Set<String> tc14 = new TreeSet<>();
		tc14.add("123");
		tc14.add("ONE");
		tc14.add("ONe");
		tc14.add("ON");
		tc14.add("On");
		tc14.add("one");
		//tc14.add(null);//THROW Exception if it's TreeSet
		System.out.println("The order of TreeSet is" + tc14);
		
		Map<String,String> tc15 = new TreeMap<>();
		tc15.put("b",null);
		tc15.put("a",null);// The null value on key will throw exception, null on value is fine
		System.out.println("The order of Map is" + tc15);
		
		Queue<Integer> tc16 = new ArrayDeque<>();
		tc16.add(1);
		//tc16.add(null);//THROW EXCEPTION due to null in ArrayDeque.
		System.out.println("The order of Map is" + tc15);

		ArrayList<Chapter3> tc17 = new ArrayList<Chapter3>();
		tc17.add(new Chapter3("1", "Ella"));
		tc17.add(new Chapter3("2", "Lara"));
		tc17.add(new Chapter3("3", "Erica"));
		Collections.sort(tc17);
		System.out.println("the order of chapter3 object is: " + tc17);
		
		Comparator<Chapter3> tc18 = (s1, s2) -> s2.name.compareTo(s1.name); //sort on reverse order.
		
		Collections.sort(tc17, tc18);
		System.out.println("the order of chapter3 object based on Comparator is: " + tc17);
		
		ArrayList<Generic6> tc19 = new ArrayList<>();
		//Collections.sort(tc19);//DOES NOT COMPILE
		//Collections.binarySearch(tc19, new Generic6());//DOES NOT COMPILE
		
	/*
	 * Method reference*/
		Consumer<List<Integer>> tc20 = l -> Collections.sort(l); 
		Consumer<List<Integer>> tc21 = Collections::sort; //Method reference
		
		String str = "abc";
		Predicate<String> tc22 = ss -> ss.startsWith(str);
		Predicate<String> tc23 = str::startsWith;//calling method reference on a instance.
		Predicate<String> tc24 = String::isEmpty;//calling method reference on a instance without knowing the instancve in advance.

		Supplier<ArrayList> tc25 = () -> new ArrayList<>();
		Supplier<ArrayList<Integer>> tc26 = ArrayList :: new; // create an instance by using constructor
		myFunctionalInterface tc27 = Chapter3  :: new ;
		tc27.getChapter3("123", "456");
		
		tc14.removeIf(String :: isEmpty);
		tc10.replaceAll(x -> x + 1);
		
		System.out.println(tc10);
		tc10.forEach(s -> System.out.println(s));
		tc10.forEach(System.out::println);
		
		Map<String, String> tc28 = new HashMap<String, String>();
		tc28.put("Jenny", "haha");
		tc28.put("Tom", "haha");
		tc28.put(null, "null");
		
		System.out.println("before executing Map merge() test1 : " + tc28);
		tc28.merge("Jenny", "shuangsiwole", (tc28_1, tc28_2) -> tc28_1.length() > tc28_2.length() ? tc28_1 : tc28_2);
		tc28.merge(null, "shuangsiwole", (tc28_1, tc28_2) -> tc28_1.length() > tc28_2.length() ? tc28_1 : tc28_2);
		
		System.out.println("Map merge() test1 : " + tc28);
		
		ship(tc17);//A function with parameter List<?> can take any type implements List, like tc17 is ArrayList<Chapter3>
	} 
	
	
	public static <U extends Exception> void printMessage(U u) {
	  System.out.println(u.getMessage());	
	}
	
	public static <Exception> void printMessage1(Exception u) {
		  //System.out.println(u.getMessage());	!!!!!!!!!!!!!!!!!A Evil Trick, now Exception is no longer the class Exception, Exception becomes generic type parameter name
	}
	
	//public static void printMessage2(List<B extends Exception> u) { DOES NOT COMPILE
	
	public <T extends Exception> T printMessage2(T t) {//DOES NOT COMPILE IF use <T> as a return type
		return t;
	}
	
	//public void printMessage3(T t) {}//DOES NOT COMIPLE, you can OMIT ONLY when  T is defined in class defination

	
	public static <A> A printMessage4(A t) {//Does not compile if missing <A>
		return t;
	}
	
	<T extends Exception> void printMessage5(List<T> t) {}
	
	//<T super Exception> void printMessage6(List<T> t) {} //DOES NOT COMPILE if using super, lower-bound
	
	//public void printMessage4(U t) {//DOES NOT COMPILE IF changed to U since U is not defined in class defination.
	
	
	//<B extends Exception> void printMessage6(List<B extends Exception> t) {}//DOES NOT COMPILE IF NOT USING WILDCARD IN method parameter()
	
	public static void receiveRawCollection(List<Exception> le) {
		for(Exception e : le) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void ship(List<?> ls) {}
	
	@Override public int compareTo(Chapter3 c) {
		return 
				//this.toString().compareTo(c.id); //ascending order
		        c.toString().compareTo(this.id); //decending order
	}
	
	@Override public String toString() {
		return id + "-" + name;
	}

}




