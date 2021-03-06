package Chapter3;
import java.io.IOException;
import java.util.*;
import java.util.function.*;
import java.util.stream.Stream;

//class generic<?>{} DOES NOT COMPILE
//class Generic1<T extends B>{} DOES NOT COMPILE, since B is not a defined class
//class generic2<? extends Exception>{} // DOES NOT COMPILE, should be class generic2<B extends Exception>{}

class GenericTrick<IOException>{} //A EVIL TRICK!!!!!!!!!!!!!!!!a compiler warning is generated, saying generic name IOException hiding the type IOException.
class Generic<T extends B, B>{// the parameter you are passing in, a T must be a subclass of, or implements B	
	public Generic(T t, B b) {}} 

//class Generic2<X super Double, B>{}//DOES NOT COMPILE, class Generic2<X extends Double, B>{} // compiles 

class Generic5<X extends B, B>{}

/*****implement a generic interface*****/
interface Shippable <T>{ };
class Generic3 implements Shippable<Exception>{}//NO LONGER A GENERIC CLASS

class Generic4<U> implements Shippable<U> {}

class Generic6 implements Shippable{}// warning here
//class Generic6 implements Shippable<T>{}// does not compile, due to <T> after Shippable

/***Comparable and Comparator***/

class testComparator implements Comparator<testComparator> {

	@Override
	public int compare(testComparator o1, testComparator o2) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}

class testComparable implements Comparable<testComparable> {

	@Override
	public int compareTo(testComparable o) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}

interface myFunctionalInterface {
	Chapter3 getChapter3(String id, String name);
}

public class Chapter3<T> implements Comparable<Chapter3>{
	private String id;
	private String name;
	
	Chapter3(String id, String name){
		this.id = id;
		this.name = name;
	}
	/**DOES NOT COMPILE,**/
	//static <?> void myMethod() {}//does not support wild card before return type
	//static <? extends super> void myMethod() {}//does not support wild card before return type
	//static <T super Exception> void myMethod() {}//does not support Lower-bound 
	static <T> void myMethod(T t) {}
	void myInstanceMethod(T t) {}

	public static <T> void main(String args[]) {
		/*Collection*/
		testArrayDeque();
		testList();
		testMap();
		testSet();
		testReplaceAll();
		testStack();
		Chapter3.<Exception>myMethod(new Exception());//compiles
		myMethod(new Exception());//compiles
		//<Exception>myMethod(new Exception());//does not compile
		//<Exception>.myMethod(new Exception());//does not compile
		
		/*Generic*/
		testCreatingGenericInstance();	
		testUpperBound();
		tesetCallStaticGenericMethod();
		testGenericInStaticMethodSignature(new ArrayList<String>());
		new Chapter3("a", "b").testGenericInInstanceMenthodSignature("asd");
		testReceiveRawCollection();//pass a raw collection into a generic method may cause exception.
		ArrayList<String> as = new ArrayList<>();
		ArrayList<Integer> as_2 = new ArrayList<>();
		ArrayList<Object> as_3 = new ArrayList<>();
		testWildCardInFunctionParameter(as);
		//testWildCardInFunctionParameter(as_2);//does not compile
		testWildCardInFunctionParameter(as_3);
		
		/*Comparator Comparable MethodReference MergeMap*/
		testComparable();
		testComparator();
		testMethodReference();
		testMapMerge();		
	} 
	
/*****Java Collections*****/
	public static void testArrayDeque() {
		ArrayDeque<String> tc1 = new ArrayDeque<>();
		//tc1.element();Throw NoSuchElementException
		//tc1.remove();Throw NoSuchElementException
		//tc1.pop();Throw NoSuchElementException
		tc1.add("abc");
		//tc1.add(null);Throw NPE. since null is part of the return protocol for some methods like poll() peek() pop, adding null will break it. 
		
		Queue<Integer> tc16 = new ArrayDeque<>();
		tc16.add(1);

		
		Queue<Integer> tc17 = new ArrayDeque<>(0);
		tc17.offer(1);
		tc17.offer(2);
		tc17.offer(3);
		System.out.println("testArrayDeque: " + tc17);
	}
	
	public static void testList() {
		//ArrayList<int> tt; DOEST NOT COMPILE 
		List<String> tl1 = new LinkedList<>();
		tl1.add(null);//LinkedList allows to add null
		
		List<Exception> tc3 = new ArrayList<Exception>();
		tc3.add(new IOException("aaa"));
		tc3.get(0);
		
		List<Exception> tc9 = new ArrayList<>();
		tc9.add(new IOException("aaa"));//You can add an IOException since IOException "is-a" Exception.
		
	}
	
	public static void testMap() {
		Map<String, String> tc2 = new HashMap<>();
		tc2.put("a", "b");
		tc2.put(null, null);
		System.out.println(tc2);
		
		Map<String,String> tc15 = new TreeMap<>();
		tc15.put("b",null);
		tc15.put("a",null);// The null value on key will throw exception, null on value is fine
		System.out.println("The order of Map is" + tc15);
		System.out.println("The order of Map is" + tc15);
	}
	
	public static void testSet() {
		Set<String> tc4 = new HashSet<>();
		tc4.add("first");
		tc4.add("second");
		tc4.add("thrid");
		tc4.add(null);
		tc4.forEach(s -> System.out.println(s));
		
		String[] tc5 = {"a", "a", "b", "c"};
		Set<String> tc6 = new HashSet<>(Arrays.asList(tc5));
		System.out.println("convert list to set : " + tc6);
		
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
		
		tc14.removeIf(String :: isEmpty);
	}
	
	public static void testReplaceAll() {
		List<String> tc10 = new ArrayList<>();
		tc10.add("first");
		tc10.add("second");
		tc10.add("third");
		
		
		tc10.replaceAll(x -> x + 1);//String concatenation 
		
		System.out.println(" testReplaceAll " + tc10);
		tc10.forEach(s -> System.out.println(s));
		tc10.forEach(System.out::println);
	}
	
/*****Create Generic Instance */
	public static void testCreatingGenericInstance() {
		
		/* DOES NOT COMPILE
		 * List<T extends Exception> tc7 = new ArrayList<>();//Does NOT COMPILE, no upper-bound or lower-bound with generic parameter name when creating an Object,
		 * instead you can write List<? extends Exception> tc7 = new ArrayList<>();
		 * //List<B extends Number> tc10;//same as above
		 * static List<Exception> tc = new ArrayList<>(); //DOES NOT COMPILE if using generic on static variable  
		 * Generic3<Exception> tg3 = new Generic3<>();//DOES NOT COMPILE Generic3 is no longer a generic class
		 */
		
		List<? extends String> upperbound = new ArrayList<>();
		//upperbound.add("aa");//DOES NOT COMPILE, upperbound becomes immutable. 
		List<? super IOException> lowerbound = new ArrayList<>();
		lowerbound.add(new IOException());
		List<String> tc10 = new ArrayList<>();
		tc10.add("index0");
		List<? extends String> tc11 = new ArrayList<String>(tc10);
		Generic<NullPointerException, Exception> tg1 = new Generic<>(new NullPointerException("a"), new Exception("b")); // take a look at this !!!
		GenericTrick<String> tg2 = new GenericTrick<>();
		
		//tc11.add("100");//DOES NOT COMPILE, tc11 is immutable, but you can still remove it, 
		tc11.remove(0); //compiles and not throw exception if 0 element exist.
		//tc11.set(1, "setFirest");//DOES NOT COMPILE, tc11 is immutable,
		
		/*Collections does not know how to suport a Generic6*/
		List<Generic6> tc19 = new ArrayList<>();
		Set<String> tc20 = new TreeSet<>();
		List<String> tc21 = new ArrayList<>();
		//Collections.sort(tc19);//DOES NOT COMPILE, Generic6 did not implements Comparable
		//Collections.sort(tc20);//DOES NOT COMPILE. tc20 is not a List
		//Collections.binarySearch(tc19, new Generic6());//DOES NOT COMPILE, argument did not implements Comparable
		Collections.sort(tc21);
	}
	
/***** Generic in function signature. */
	public static <Exception> void printMessage1(Exception u) {
		  //System.out.println(u.getMessage());	!!!!!!!!!!!!!!!!!A Evil Trick, now Exception is no longer the class Exception, Exception becomes generic type parameter name
	}
	
	//public static void printMessage2(List<B extends Exception> u) { DOES NOT COMPILE
	
	public <T extends Exception> T printMessage2(T t) {//DOES NOT COMPILE IF use <T> as a return type
		return t;
	}
	
	//You can OMIT ONLY when T is defined in class definition, for instance method, you need the declaration for static method.
	public <T> void printMessage3(T t) {}
	public void printMessage4(T t) {}
	public static <T> void printMessage5(T t) {} 
	//public static void printMessage6(T t) {} //does not compile

	
	public static <A> A testStaticGenericMethod(A t) {//Does not compile if missing <A>
		return t;
	}
	
	<T extends Exception> void printMessage5(List<T> t) {}
	
	//<T super Exception> void printMessage6(List<T> t) {} //DOES NOT COMPILE if using super, lower-bound
	
	//public void printMessage4(U t) {//DOES NOT COMPILE IF changed to U since U is not defined in class defination.
	
	
	//<B extends Exception> void printMessage6(List<B extends Exception> t) {}//DOES NOT COMPILE IF NOT USING WILDCARD IN method parameter()
	
/*****generic compiler warning****/
	public static void testGenericCompilerWarning() {
		List<? super Exception> tc12 = new ArrayList<>();
		Iterator tc12iter = tc12.iterator();//warning here
		while(tc12iter.hasNext()) {
			//Exception iE = tc12iter.next();//DOES NOT COMPILE if no cast, due to type mismatch, since tc12iter.next() returns an Object
		}	
	} 
	
/*********generic upperbound*/
	public static <U extends Exception> void testUpperBound() {
		testUpperBoundPrintMessage(new Exception("exception"));
		testUpperBoundPrintMessage(new IOException("IOexception"));
		
		Chapter3.<NullPointerException>testUpperBoundPrintMessage(new NullPointerException("NullPointerException"));
		//<NullPointerException>testUpperBoundPrintMessage(new NullPointerException("IOexception")); //DOES NOT COMPILE,syntex error.
		//Chapter3.<NullPointerException>testUpperBoundPrintMessage(new Exception("IOexception"));//DOES NOT COMPILE, since you specify a NullPointerException but pass in a Exception
	}
	
	public static void tesetCallStaticGenericMethod() {
		testStaticGenericMethod(new Exception());// call a static generic method normally
		Chapter3.<Exception>testStaticGenericMethod(new Exception());// call it fuckabley
	}
	
	public static <U extends Exception> void testUpperBoundPrintMessage(U u) {
	  System.out.println(u.getMessage());	
	}
	
	public static <T> void testGenericInStaticMethodSignature(T t) {// you must declare <T> in static method no matter <T> defined in class declaration or not
		
	}
	
	public void testGenericInInstanceMenthodSignature(T t) { // you can omit <T> if T is already defined on class declaration
		
	}
	
	public static void testReceiveRawCollection() {
		List tc8 = new ArrayList<>();
		tc8.add("abc"); 
		//testReceiveRawCollection(tc8);//throw compile warning for raw type, throw ClassCastException,
		//since tc8 contains a String but testReceiveRawCollection trying to read Exception
	}
	
	public static void testWildCardInFunctionParameter(List<? super String> ls) {
		
	}
	
	public static void testReceiveRawCollection(List<Exception> le) {
		for(Exception e : le) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void testComparable() {
		ArrayList<Chapter3> tc17 = new ArrayList<>();
		tc17.add(new Chapter3("1", "Ella"));
		tc17.add(new Chapter3("2", "Lara"));
		tc17.add(new Chapter3("3", "Erica"));
		Collections.sort(tc17);
		System.out.println("the order of chapter3 Chapter7Util.OBJECT is: " + tc17);
		
		Comparator<Chapter3> tc18 = (s1, s2) -> s2.name.compareTo(s1.name); //sort on reverse order.
		
		Collections.sort(tc17, tc18);
		System.out.println("the order of chapter3 Chapter7Util.OBJECT based on Comparator is: " + tc17);
		ship(tc17);//A function with parameter List<?> can take any type implements List, like tc17 is ArrayList<Chapter3>
	}
	
	public static void testComparator() {
		ArrayList<testComparator> comparatorList = new ArrayList<>();
		ArrayList<testComparable> comparableList = new ArrayList<>();
		
		Collections.sort(comparableList);
		
		//Collections.sort(comparatorList); //DOES NOT COMPLILE since testComparator didn't implement Comparable
		
		TreeSet<testComparator> treeSetComparator = new TreeSet<>();
		//treeSetComparator.add(new testComparator());throw exception since testComparator didn't implement Comparable
		
		TreeSet<testComparable> treeSetComparable = new TreeSet<>();
		treeSetComparable.add(new testComparable());//no exception, since testComparable implemented Comparable.
	}
	
	public static void ship(List<?> ls) {}
	
	public static void testMethodReference() {
		Consumer<List<Integer>> tc20 = l -> Collections.sort(l); 
		Consumer<List<Integer>> tc21 = Collections::sort; //Method reference
		
		String str = "abc";
		Predicate<String> tc22 = ss -> ss.startsWith(str);
		Predicate<String> tc23 = str::startsWith;//calling method reference on a instance.
		Predicate<String> tc24 = String::isEmpty;//calling method reference on a instance without knowing the instancve in advance.

		Supplier<ArrayList> tc25 = () -> new ArrayList<>();
		Supplier<ArrayList<Integer>> tc26 = ArrayList :: new; // create an instance by using constructor
		
		myFunctionalInterface tc27 = Chapter3  :: new ;//create an custom instance with method reference.
		tc27.getChapter3("123", "456");// the getChapter3 method set the parameters used in the Constructor of Chapter3.
		
		System.out.println("testMethodReference: " + tc27.getChapter3("123", "456").id);
		
		BiConsumer<Set<String>, Set<String>> bo = Set::addAll; //(Set<String> s1, Set<String> s2) -> s1.addAll(s2);
		Stream.of("aaa", "aaa").filter(String::isEmpty).count();
		//Stream.of("aaa", "aaa").filter(!String::isEmpty).count();// "!String::isEmpty" does not compile
		
		Predicate<String> ps = String::isEmpty;
		BiFunction<String,String,Boolean> bs = String::startsWith;
		BiFunction<String,Character,Integer> bsi = String::indexOf;
		bs.apply("abc", "b");
		
		String sr = "asdf";
	}
	
	public static void testMapMerge() {
		
		Map<String, String> tc28 = new HashMap<String, String>();
		tc28.put("Jenny", "haha");
		tc28.put("Tom", "haha");
		tc28.put(null, "null");
		
		System.out.println("before executing Map merge() test1 : " + tc28);
		tc28.merge("Jenny", "shuangsiwole", (tc28_1, tc28_2) -> tc28_1.length() > tc28_2.length() ? tc28_1 : tc28_2);
		tc28.merge(null, "shuangsiwole", (tc28_1, tc28_2) -> tc28_1.length() > tc28_2.length() ? tc28_1 : tc28_2);
		
		System.out.println("Map merge() test1 : " + tc28);
	}	
	
	public static void testStack() {
		Stack<Integer> stack = new Stack<>();
		for(int i=0;i<100;i++) {
			stack.add(i);
		}
		
		Iterator<Integer> iter = stack.iterator();
		
		while(iter.hasNext()) {
			System.out.println("testStack: " + iter.next());
		}
	}
	
	@Override public int compareTo(Chapter3 c) {
		return 
				//this.toString().compareTo(c.id); //ascending order
		        c.toString().compareTo(this.id); //decending order
	}
	
	@Override public String toString() {
		return id + "-" + name;
	}

}