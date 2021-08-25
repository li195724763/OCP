package Chapter4;
import java.util.*;
import java.util.function.*;
import java.time.*;
import java.util.stream.*;
import java.util.stream.Collectors.*; 

public class Chapter4 {
	static int id = 0;
	public static void main(String[] args) {			
		testSupplier();
		testConsumer();
		testBiConsumer();
		testPredicate();
		testBiFunction();
		
		testStreamCreation();
		testStreamReduction();	
		testStreamCollect();
		testStreamCollectors();
		testStreamMap();
		testSteamFlatMap();
		testStreamMethod();
		testStreamPrimitiveStream();
		testStreamCollectorsJoining();
		testStreamCollectorsMapping();
		
		testOptional(); 	 
		testAsList();
	}
	
	static void testSupplier() {
		aSupplier(ArrayList<String> :: new);
		aSupplier(LocalDate :: now);
		aSupplier(() -> LocalDate.now());
		aSupplier(StringBuilder :: new);
		aSupplier(() -> new StringBuilder("abc"));
		
		Supplier<ArrayList<String>> tc1 = ArrayList<String> :: new;//DOES NOT COMPILE if missing <String> in ArrayList<String> on right-hand side
		System.out.println(tc1); //MyPackageChapter4.Chapter4$$Lambda$7/0x00000008011f2840@4eec7777
		
		Chapter4.bSupplier(Comparator::reverseOrder);
	}
	
	static void testConsumer() {
		ArrayList<String> tc2 = new ArrayList<>();//calling toString() on Lambda.
		tc2.add("first");
		aConsumer((s) -> System.out.println(tc2.get(0)), tc2);
		tc2.forEach(System.out::println);
		
		Chapter4.bConsumer(chapter4Obj -> System.out.println(Chapter4.id), Chapter4.id);
		 
		Consumer<String> tc63 = x -> new String();
		Consumer<String> tc64 = x -> {new String();};
		//Consumer<String> tc65 = x -> {return new String();};//Does not compile
	}
	
	static void testBiConsumer() {
		Map<String, String> tc3 = new HashMap<>();
		BiConsumer<String, String> tc4 = (k, v) -> tc3.put(k,v);
		BiConsumer<String, String> tc5 = tc3::put;
		tc4.accept("key1","value1");
		tc5.accept("key2","value2");
		System.out.println("put key value use BiFunction: " + tc3);
		
		aBIConsumer((k1, v1) -> {tc3.put(k1,v1);}, "good", "better");// no need to add keyword return if the functional interface method return type is void
		aBIConsumer(tc3::put, "good2", "better2");
		System.out.println("put key value use BiFunction: " + tc3);
	}
	
	static void testPredicate() {
		aBIPredicate((abi_1, abi_2) -> abi_1.startsWith(abi_2), "wahaha", "w");
		aBIPredicate(String::startsWith, "wahaha", "w");
	}
	
	static void testBiFunction() {
		aBiFunction(String::concat, "first " , "second");
		
		Function<Integer, Integer> tc64 = x -> x*x; //a Function type functional interface needs to define two generic parameters
		
		BiFunction<String, String, Boolean> tc66 = String::startsWith;
		
		BiFunction rawType = (s1,s2) -> true;
		//BiFunction rawType_2 = (s1,s2) -> s1.startsWith(s2);//Does not compile
		BiFunction<String, String, Boolean> notRawType = (s1,s2) -> s1.startsWith(s2);
	}
	
	static void testStreamCreation() {
		Stream<Double> tc6 = Stream.generate(Math::random);
		//tc6.count();//hang here
		//tc6.forEach(System.out::println); //infinit output
		Stream<Integer> tc7 = Stream.iterate(1, n -> n = n + 1);
		//tc7.forEach(System.out::println);//infinit output
		
		Stream<String> tc10 = Stream.of("first5", "first2", "first3");
		System.out.print("testStreamCreation: ");
		tc10.findAny().ifPresent(System.out::println);
		
		Stream<Double> tc11 = Stream.generate(Math::random);
		tc11.findFirst().ifPresent(System.out::println);
		
		Stream<String> tc12 = Stream.generate(() -> "tc12 case");
		//System.out.println(tc12.anyMatch(tc12_1 -> Character.isLetter(tc12_1.charAt(0))));
		System.out.println("NoneMatch test: " + tc12.noneMatch(tc12_1 -> Character.isLetter(tc12_1.charAt(0)))); //IllegalStateException, if calling terminate function twice.
		
		List<Integer> intList = new ArrayList<>();
		for(int i=0;i<10;i++) {
			intList.add(i);
		}
		
		Stream<Integer> consistentInt = intList.stream();

		intList.add(11);
		consistentInt.forEach(System.out::print);// output 012345678911, since Stream and list are consistent. 
		System.out.println("");
		
		Stream.iterate(1, x -> ++x).limit(5).forEach(System.out::print); //this print 123456.
		System.out.println("");
		
		Stream.iterate(1, x -> x++).limit(5).forEach(System.out::print); //this print 123456.
		System.out.println("");
		
	}
	
	static void testStreamReduction() {
		 //Single parameter reduce: Accumulator type is the same as Stream element type.
		System.out.println("Test single parameter reduce(): " + Stream.of(1,3,5).reduce((Integer i1, Integer i2) -> i1*i2).get());

		//Doube parameters reduce: Accumulator type is the same as Stream element type.
		System.out.println("double parameter reduce(): " + 
				Stream.of('F','O','D').reduce('A', (Character c1, Character c2) -> c1)); 

		//three parameter reduce:
		System.out.println("tree parameters reduce(): " + 
		Stream.of('W', 'O', 'L', 'F').reduce("", (String s, Character c) -> s + c, (String s1, String s2) -> s1 + s2 ));
		
		BinaryOperator<Integer> op = (op1, op2) -> op1 * op2;
		Stream<Integer> tc13 = Stream.of(1,3,5);
		System.out.println(tc13.reduce(1, op, op));
		
		Stream<String> tc14 = Stream.of("w", "o", "l", "f");
		Stream<String> tc15 = Stream.of("w", "o", "l", "f");
		Stream<String> tc16 = Stream.of("w", "o", "l", "f");
		Stream<String> tc17 = Stream.empty();
		Stream<String> tc18 = Stream.of("w");;
		
		System.out.println(tc14.reduce("", String::concat));
		System.out.println(tc15.reduce("", (tc15_1, tc15_2) -> tc15_1 + tc15_2));// Exception if using tc14, since the tc14 Stream is already terminate, can't run another terminate method
		tc16.reduce(String::concat).ifPresent(System.out::println);
		tc17.reduce(String::concat).ifPresent(System.out::println);
		tc18.reduce(String::concat).ifPresent(System.out::println);
		
		Stream<Integer> tc31 = Stream.of(1,3,4,5);
		if(tc31.reduce(0, (tc20_1, tc20_2) -> tc20_1 + tc20_2) instanceof Integer) {
			System.out.println("reduce with identifier return Object type"); //reduce with identifier
		};
		//tc31.reduce((tc20_1, tc20_2) -> tc20_1 + tc20_2).ifPresent(System.out::println); //reduce without identifier
		
	    System.out.println("test reduce: " + Stream.of(1,3,5).reduce(1, (Integer x, Integer y) -> x-y, (x,y) -> x/y));
	    //does not compile if using "int" instead of Integer. 
	    //the combiner is not making any different here on the result, since this is a serial stream. 
	    //this is not good, since - is not associative, and identity is not 0 for number element. 
	}
	
	static void testStreamCollect() {
		Stream<String> tc19 = Stream.of("a","b", "c", "d", "--f--");
		System.out.println("TreeSet with Lambda: " + tc19.collect(() -> new StringBuilder(), (StringBuilder a, String b) -> a.append(b),(StringBuilder a, StringBuilder b) -> a.append(b)));
		
		Stream<String> tc20 = Stream.of("f","g", "h", "i");
		System.out.println("TreeSet with Method reference: " + tc20.collect(TreeSet<String>::new, TreeSet<String>::add, TreeSet<String>::addAll));
		
		Stream<String> tc21 = Stream.of("g","k", "l", "m");
		System.out.println("ArrayList with Method reference: " + tc21.collect(ArrayList<String>::new, ArrayList<String>::add, ArrayList<String>::addAll));
		
		
	}
	
	static void testStreamCollectors() {
		Stream<String> tc22 = Stream.of("g","k", "l", "m", "m");
		//System.out.println("Using Java provided collector "  + tc22.collect(Collectors.toSet()));
		System.out.println("Using Java provided collector "  + tc22.collect(Collectors.toCollection(HashSet<String> ::new)));
		
	    Stream<String> tc39 = Stream.of("first", "second", "aaaaa", "asdfsaf");
	    //System.out.println(tc39.collect(Collectors.averagingDouble(String::length)).doubleValue());
	    TreeMap<Integer, String> tc40 = tc39.collect(Collectors.toMap(String::length, value -> value, (String s1, String s2) -> s1 + ", " + s2, TreeMap::new));
	    //Stream.of("first", "second", "55555", "55555").collect(Collectors.toMap(String::length, value->value));//exception due to missing merge function for duplicate key
	    //BinaryOperator define the merge function, Supplier defined the overall return type
	    System.out.println("to map() test: " + tc40);
	    
	    Stream<String> tc41= Stream.of("first", "second", "aaaaa", "asdfsaf");
	    Map<Integer, List<String>> tc42 = tc41.collect(Collectors.groupingBy(String::length));
	    Stream<String> tc43= Stream.of("first", "second", "aaaaa", "asdfsaf");
	    Map<Integer, Set<String>> tc44 = tc43.collect(Collectors.groupingBy(String::length, Collectors.toSet()));
	    Stream<String> tc45= Stream.of("first", "second", "aaaaa", "asdfsaf");
	    TreeMap<Integer, List<String>> tc46 = tc45.collect(Collectors.groupingBy(String::length, TreeMap::new, Collectors.toList()));
	    System.out.println("grouping by test: " + tc46);
	    
	    Stream<String> tc47 = Stream.of("first", "second", "aaaaa", "asdfsaf");
	    Map<Boolean, List<String>> tc48 = tc47.collect(Collectors.partitioningBy(tc48_1 -> tc48_1.length() <= 5, Collectors.toList())) ;
	    System.out.println("partitioning by test: " + tc48);
	    
	    Stream<String> tc49 = Stream.of("first", "second", "aaaaa", "asdfsaf");
	    Map<Integer, Optional<Character>> tc50 = tc49.collect(Collectors.groupingBy(String::length, Collectors.mapping((String s) -> s.charAt(0), 
	    		Collectors.minBy(Comparator.naturalOrder())))); 
	    System.out.println("idk: " + tc50);
	    
	    
	    Stream<Integer> tc59 = Stream.of(1,3,5);
	    Double tc60 = tc59.collect(Collectors.averagingDouble(tc59_1 -> tc59_1));
	    
	    Stream<String> tc61 = Stream.iterate("", (String s) -> s + "a");
	    Map<Integer,Long> tc62 = tc61.limit(5).collect(Collectors.groupingBy(String::length, Collectors.counting()));
	    System.out.println("grouping by : " + tc62);
	    
	    long count = Stream.of(1,3,5).collect(Collectors.counting());
	}
	
	static void testStreamMap() {
		Stream<String> tc23 = Stream.of("asghtjiogereigrl", "asdfcxz", "sdgthjfol");
		//tc23.map(String::length);
		tc23.map((String s) -> s.length()).forEach(System.out::println);//Exception !!! since using intermidate method twice.
		
		Stream<Integer> tc32 = Stream.of(413,3452,1235,12111);
		//tc32.mapToInt(tc32_1 -> tc32_1);
		System.out.println(tc32.mapToInt(tc32_1 -> tc32_1).average().getAsDouble());
		
		 DoubleStream tc54 = DoubleStream.of(1,3,5);
		 Stream<String> tc55 = tc54.mapToObj(tc54_1 -> "double value is : " + tc54_1);
		 //DoubleStream.of(1,3,5).mapToInt( i ->  i);// Does not compile DoubleStream.of(1,3,5).mapToInt( i ->	averagingDouble  i);
		 DoubleStream.of(1,3,5).mapToInt( i -> (int) i);// explicit cast needed
	}
	
	static void testSteamFlatMap() {
		//flatMap() map one Stream with two layers element to another Stream with one layer element. 
		List<String> tc24 = Arrays.asList("List1","asdfsa", "sdaferfe");
		List<String> tc25 = Arrays.asList("List2","asdfassssa", "sdafesdfsafrfe");
		List<String> tc27 = Arrays.asList();
		List<String> tc26 = Arrays.asList("List3","asdfsdfcsasa", "sdafdsfgrgergerfe");
		Stream<List<String>> tc28 = Stream.of(tc24,tc25,tc26,tc27);
		//Stream<String> tc28_1 = tc28.flatMap(List::stream); //Flat map the List<String> Stream to String Stream
		//IntStream tc28_2 = tc28.flatMap(List::stream).flatMapToInt(x -> IntStream.of(x.length()));//Flat map the List<String> Stream to String Stream, then flat map the Stream<Stream<Integer>> to Int Stream
		System.out.println("*****start: testSteamFlatMap_1*******");
		tc28.flatMap((List<String> ls) -> ls.stream()).forEach(System.out::println);//Lambda to method refenrece, List::stream
		
		Stream.of(tc27).flatMap(List::stream).findAny().ifPresentOrElse((String s) -> System.out.println("String is NOT empty"), () -> System.out.println("String is empty"));
		System.out.println("*****end: testSteamFlatMap_1*******");
		System.out.print("testSteamFlatMap_1: ");
		// .forEach(tc28_2 -> System.out.println("flat map: " + tc28_2));
		//Stream<?> tc28_3 = tc28.map(List::stream); //This is an Object type, which equels to Stream<Stream<String>> tc28_3 = tc28.map(List::stream);//since in map the List<String> converts to Stream<String>
		
		 List<String> tc57 = Arrays.asList("an","asdf", "sadf");
		 Stream<String> tc58 = tc57.stream();
		 //tc58.mapToInt(String::length);
		 //tc58.flatMapToInt(String::length);DOES NOT COMPILE, flatMapToInt takes a IntStream.
		 tc58.flatMapToInt(tc58_1 -> IntStream.of(tc58_1.length()));
		 
		 List<Integer> tc68 = Arrays.asList(1,2,3);
		 List<Integer> tc69 = Arrays.asList(1,2,3);
		 Stream<Integer> tc70 = Stream.of(tc68, tc69).flatMap(x -> x.stream());
		 
	     List<Integer> tc72 = new ArrayList<Integer>();
	     tc72.add(1);tc72.add(2);tc72.add(3);
	     //IntStream tc73 = tc72.stream().flatMapToInt(x -> IntStream.of(x));//IntStream.of(x) changes Integer x to Stream<Integer> x 
	     //tc72.stream().mapToInt(x -> x);
	     Stream<IntStream> streamInt = tc72.stream().map(x -> IntStream.of(x));
	}
	
	static void testStreamMethod() {	
		Stream<String> tc29 = Stream.of("black bear", "brown bear", "grizzly");
		//System.out.println(tc29.filter(tc29_1 -> tc29_1.startsWith("g")).count());
		tc29.filter(tc29_1 -> tc29_1.startsWith("g")).peek((String tc29_2) -> System.out.println(tc29_2)).count();
		
		Stream<String> tc30 = Stream.of("Toby", "Anna", "Leroy", "Alex");
		tc30.sorted().filter(tc30_1 -> tc30_1.length() <=4 ).limit(2).forEach(System.out::println);;
		//tc30.limit(2);//EXCEPTION !!!!!!!!!!! but you can chain!!!!
		
		intDigit(111);
		
	    Stream<Integer> tc53 = Stream.iterate(1, tc53_1 -> tc53_1+1);
	    tc53.peek(tc53_2 -> System.out.println("peek" + tc53_2)).limit(3).forEach(tc53_3 -> System.out.println("for each" + tc53_3));
	    
	    Stream<Integer> tc71 = Stream.iterate(1, n -> n+1);
	    System.out.println("findAny example:" + tc71.findAny());
	}
	
	static void testStreamPrimitiveStream() {
		//DoubleStream
		DoubleStream tc33 = DoubleStream.generate(Math::random);
		DoubleStream tc34 = DoubleStream.iterate(0.5, tc34_1 -> tc34_1 / 2);//in iterate, The first one does not divided by two
	    tc33.limit(3).forEach(System.out::println);
	    tc34.limit(3).forEach(System.out::println);
	    
	    //LongStream and Optional
		LongStream tc35 = LongStream.rangeClosed(100, 1000);
		OptionalDouble tc35_1 = tc35.average();
		tc35_1.ifPresent(System.out::println);
		
		Stream<Integer> s = Stream.of(1);
		IntStream s_int = s.mapToInt(i -> i);
		//DoubleStream s_double = s.mapToDouble(i -> i);throw illegalstateexception, since stream has already been operated
	}
	
	static void testStreamCollectorsJoining() {
		//Collectors.joining takes String Stream.
		
	    //Stream.iterate(1, x -> x++).limit(5).collect(Collectors.joining());//DOES NOT COMPILE, Collectors.joining() used for String only
		//System.out.println(Stream.iterate(1, x -> x++).limit(5).map(x -> x).collect(Collectors.joining()));//DOES NOT COMPILE, same reason as above
	    String tc65 = Stream.iterate(1, x -> ++x).limit(5).map(x -> "" + x).collect(Collectors.joining());//map(x -> "" + x) convert the Integer Stream to String Stream.
	    System.out.println("chapter4 question 11: " + tc65);
	    
	}
	
	static void testStreamCollectorsMapping() {
	    /**********************Collectors.mapping() example **************/
	    Stream<String> tc74 = Stream.of("one", "two", "three", "four", "five");
	    Map<Integer, Optional<Character>> tc75 = tc74.collect(Collectors.groupingBy(x -> x.length(),
	    		Collectors.mapping(s -> s.charAt(0), Collectors.minBy(Comparator.naturalOrder()))));
	    		
	    System.out.println("test 3 parameters groupingBy() :" + tc75);//{3=o,4=f, 5=t}
	    		
	    Consumer<String> tc76 = String::new;
	    
	    List<Integer> tc77 = Arrays.asList(1,2,3);
	    List<Integer> tc78 = Arrays.asList(4,5,6);
	    List<Integer> tc79 = Arrays.asList();
	    Stream<Integer> tc80 = Stream.of(tc77, tc78, tc79).map(x -> x.size());
	    
	    Stream<String> tc81 = Stream.of("abc", "bcd");
	    //Stream<Integer> tc82 = tc81.mapToInt(s -> s.length());//DOES NOT COMPILE, since mapToInt returns a IntStream, assign a IntStream Object to a Stream reference is not legal
	    
	    System.out.println("testStreamCollectorsMapping: " + Stream.of("abc", "bcd").collect(Collectors.mapping((String s) -> s.charAt(0) , Collectors.minBy(Comparator.naturalOrder()))));
	    
	    
	    //Map<Integer, Optional<Character>> tc50 = tc49.collect(Collectors.groupingBy(String::length, Collectors.mapping((String s) -> s.charAt(0), Collectors.minBy(Comparator.naturalOrder())))); 
	    
	    TreeMap<Integer, Optional<Character>> threeParaGroupingBy = Stream.of("first", "second", "third").collect(Collectors.groupingBy((String s) -> s.length() , TreeMap::new, Collectors.mapping((String s) -> s.charAt(0), Collectors.minBy(Comparator.naturalOrder()))));
	    
	    Optional<Character> optMapping = Stream.of("apple", "banada", "cinemon").collect(Collectors.mapping(s->s.charAt(0), Collectors.minBy(Comparator.naturalOrder())));
	}
	
	static void testOptional() {
		Optional<String> tc36 = Optional.of("Stinsdf");
		tc36.get();//throw NoSuchElementException, which is a unchecked Exception.
		
		Optional<String> ofNullable = Optional.ofNullable(null);
		Double notAnum = Double.NaN;
		System.out.println("not a number: " + notAnum);
		
		Optional<Optional<Integer>> op = tc36.map(Chapter4::calculator);
		Optional<Integer> tc37 = tc36.map(String::length);
		Optional<Integer> tc38 = tc36.flatMap(Chapter4::calculator);
	    tc38 = tc36.flatMap((String s) -> Chapter4.calculator(tc36.get()));
	    //Optional<Integer> tc39 = tc36.map(tc39_1 -> Chapter4.calculator(tc36.get()));//DOES NOT COMPILE, returns Optional<Optional<Integer>> not a Optional<Integer>
	    
	    Stream<String> tc8 = Stream.of("ape", "baby", "Flamigo");
		Optional<String> tc9 = tc8.min((tc8_1, tc8_2) -> tc8_1.length() - tc8_2.length());// min() takes a Comparator, which is also a functional interface
		tc9.ifPresent(System.out::println);
	}
	
	static void testAsList() {
		List<Integer> a = Arrays.asList(1,2,3);
		//a.add(4); //Exception in thread "main" java.lang.UnsupportedOperationException, array backed list is immutable.
	}
	
	static <T> void aSupplier(Supplier<T> s) {
		System.out.println(s.get());
	}
	
	static <T> void bSupplier(Supplier<T> s) {
		System.out.println(s.get());
	}

	
	static <T> void aConsumer(Consumer<T> c, T t) {
		c.accept(t);
	}
	
	static <T> void bConsumer(Consumer<T> c, T t) {
		c.accept(t);
	}
	
	static <T, R> void aBIConsumer(BiConsumer<T, R> bi, T t, R r)  {
		bi.accept(t, r);
	}
	
	static <T, R> void aBIPredicate(BiPredicate<T,R> bi, T t, R r) {
		System.out.println(bi.test(t, r));
	}
	
	static <T,U,R> void aBiFunction(BiFunction<T,U,R> bi, T t, U u) {
		System.out.println(bi.apply(t, u));
	}
	
	static int range(IntStream is) {
		IntSummaryStatistics intStatic = is.summaryStatistics();
		if(intStatic.getCount() == 0) {
			throw new IllegalArgumentException("fffff");
		} else {
			return intStatic.getMax() - intStatic.getMin();
		}
	}
	
	static void intDigit(int i) {
		Optional<Integer> oi = Optional.of(i);
		oi.map(x -> "" + i).filter(l -> l.length() == 3).ifPresent(System.out::println);;
	}
	

	static Optional<Integer> calculator(String s) { 
		return Optional.of(s.length()); 
	}
	/*
	 * static int calculator(String s) { return s.length(); }
	 */

}
