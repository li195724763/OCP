package Chapter4;
import java.util.*;
import java.util.function.*;
import java.time.*;
import java.util.stream.*;
import java.util.stream.Collectors.*; 

public class Chapter4 {
	int tc51 = 0;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		aSupplier(LocalDate :: now);
		aSupplier(() -> LocalDate.now());
		aSupplier(StringBuilder :: new);
		aSupplier(() -> new StringBuilder("abc")); 
		Supplier<ArrayList<String>> tc1 = ArrayList<String> :: new;//DOES NOT COMPILE if missing <String> in ArrayList<String>
		System.out.println(tc1); //MyPackageChapter4.Chapter4$$Lambda$7/0x00000008011f2840@4eec7777
		aSupplier(ArrayList<String> :: new);
		
		ArrayList<String> tc2 = new ArrayList<>();//calling toString() on Lambda.
		tc2.add("first");
		aConsumer((s) -> System.out.println(tc2.get(0)), tc2);
		tc2.forEach(System.out::println);
		
		Map<String, String> tc3 = new HashMap<>();
		BiConsumer<String, String> tc4 = (k, v) -> tc3.put(k,v);
		BiConsumer<String, String> tc5 = tc3::put;
		tc4.accept("key1","value1");
		tc5.accept("key2","value2");
		System.out.println("put key value use BiFunction: " + tc3);
		
		aBIConsumer((k1, v1) -> {tc3.put(k1,v1);}, "good", "better");// no need to add keyword return if the functional interface method return type is void
		aBIConsumer(tc3::put, "good2", "better2");
		System.out.println("put key value use BiFunction: " + tc3);
		
		aBIPredicate((abi_1, abi_2) -> abi_1.startsWith(abi_2), "wahaha", "w");
		aBIPredicate(String::startsWith, "wahaha", "w");
		
		aBIFunction(String::concat, "first " , "second");
		
		Stream<Double> tc6 = Stream.generate(Math::random);
		//tc6.count();//hang here
		//tc6.forEach(System.out::println); //infinat stream
		Stream<Integer> tc7 = Stream.iterate(1, n -> n = n + 1);
		//tc7.forEach(System.out::println);
		
		Stream<String> tc8 = Stream.of("ape", "baby", "Flamigo");
		Optional<String> tc9 = tc8.min((tc8_1, tc8_2) -> tc8_1.length() - tc8_2.length());
		tc9.ifPresent(System.out::println);
		
		Stream<String> tc10 = Stream.of("first5", "first2", "first3");
		tc10.findAny().ifPresent(System.out::println);
		
		Stream<Double> tc11 = Stream.generate(Math::random);
		tc11.findFirst().ifPresent(System.out::println);
		
		Stream<String> tc12 = Stream.generate(() -> "tc12 case");
		//System.out.println(tc12.anyMatch(tc12_1 -> Character.isLetter(tc12_1.charAt(0))));
		System.out.println("NoneMatch test: " + tc12.noneMatch(tc12_1 -> Character.isLetter(tc12_1.charAt(0)))); //Exception!!!!
		
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
		
		Stream<String> tc19 = Stream.of("a","b", "c", "d", "--f--");
		System.out.println("TreeSet with Lambda: " + tc19.collect(() -> new StringBuilder(), (StringBuilder a, String b) -> a.append(b),(StringBuilder a, StringBuilder b) -> a.append(b)));
		
		Stream<String> tc20 = Stream.of("f","g", "h", "i");
		System.out.println("TreeSet with Method reference: " + tc20.collect(TreeSet<String>::new, TreeSet<String>::add, TreeSet<String>::addAll));
		
		Stream<String> tc21 = Stream.of("g","k", "l", "m");
		System.out.println("ArrayList with Method reference: " + tc21.collect(ArrayList<String>::new, ArrayList<String>::add, ArrayList<String>::addAll));
		
		Stream<String> tc22 = Stream.of("g","k", "l", "m", "m");
		//System.out.println("Using Java provided collector "  + tc22.collect(Collectors.toSet()));
		System.out.println("Using Java provided collector "  + tc22.collect(Collectors.toCollection(HashSet<String> ::new)));
		
		Stream<String> tc23 = Stream.of("asghtjiogereigrl", "asdfcxz", "sdgthjfol");
		tc23.map(String::length);
		//tc23.map(tc23_1 -> tc23_1.length()).forEach(System.out::println);//Exception !!! since using intermidate method twice.
		
		List<String> tc24 = Arrays.asList("List1","asdfsa", "sdaferfe");
		List<String> tc25 = Arrays.asList("List2","asdfassssa", "sdafesdfsafrfe");
		List<String> tc27 = Arrays.asList();
		List<String> tc26 = Arrays.asList("List3","asdfsdfcsasa", "sdafdsfgrgergerfe");
		Stream<List<String>> tc28 = Stream.of(tc24,tc25,tc26,tc27);
		//Stream<String> tc28_1 = tc28.flatMap(List::stream); //Flat map the List to String
		//IntStream tc28_2 = tc28.flatMap(List::stream).flatMapToInt(x -> IntStream.of(x.length()));
		// .forEach(tc28_2 -> System.out.println("flat map: " + tc28_2));
		//Stream<Object> tc28_3 = tc28.map(List::stream); This is an Object type
		
		Stream<String> tc29 = Stream.of("black bear", "brown bear", "grizzly");
		//System.out.println(tc29.filter(tc29_1 -> tc29_1.startsWith("g")).count());
		tc29.filter(tc29_1 -> tc29_1.startsWith("g")).peek((String tc29_2) -> System.out.println(tc29_2)).count();
		
		Stream<String> tc30 = Stream.of("Toby", "Anna", "Leroy", "Alex");
		tc30.sorted().filter(tc30_1 -> tc30_1.length() <=4 ).limit(2).forEach(System.out::println);;
		//tc30.limit(2);//EXCEPTION !!!!!!!!!!! but you can chain!!!!
		
		Stream<Integer> tc31 = Stream.of(1,3,4,5);
		if(tc31.reduce(0, (tc20_1, tc20_2) -> tc20_1 + tc20_2) instanceof Integer) {
			System.out.println("reduce with identifier return Object type"); //reduce with identifier
		};
		//tc31.reduce((tc20_1, tc20_2) -> tc20_1 + tc20_2).ifPresent(System.out::println); //reduce without identifier
		
		Stream<Integer> tc32 = Stream.of(413,3452,1235,12111);
		//tc32.mapToInt(tc32_1 -> tc32_1);
		System.out.println(tc32.mapToInt(tc32_1 -> tc32_1).average().getAsDouble());
		
		DoubleStream tc33 = DoubleStream.generate(Math::random);
		DoubleStream tc34 = DoubleStream.iterate(0.5, tc34_1 -> tc34_1 / 2);//in iterate, The first one does not divided by two
	    tc33.limit(3).forEach(System.out::println);
	    tc34.limit(3).forEach(System.out::println);
	    
		LongStream tc35 = LongStream.rangeClosed(100, 1000);
		OptionalDouble tc35_1 = tc35.average();
		tc35_1.ifPresent(System.out::println);
		
		intDigit(333);
		Optional<String> tc36 = Optional.of("Stinsdf");
		Optional<Integer> tc37 = tc36.map(String::length);
		Optional<Integer> tc38 = tc36.flatMap(Chapter4::calculator);
	    tc38 = tc36.flatMap(tc38_1 -> Chapter4.calculator(tc36.get()));
	    //Optional<Integer> tc39 = tc36.map(tc39_1 -> Chapter4.calculator(tc36.get()));//DOES NOT COMPILE, returns Optional<Optional<Integer>> not a Optional<Integer>
	    
	    Stream<String> tc39 = Stream.of("first", "second", "aaaaa", "asdfsaf");
	    //System.out.println(tc39.collect(Collectors.averagingDouble(String::length)).doubleValue());
	    TreeMap<Integer, String> tc40 = tc39.collect(Collectors.toMap(String::length, tc39_1->tc39_1, (tc39_2, tc39_3) -> tc39_2 + ", " + tc39_3, TreeMap::new));
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
	    Map<Integer, Optional<Character>> tc50 = tc49.collect(Collectors.groupingBy(String::length, Collectors.mapping(tc49_2 -> tc49_2.charAt(0), 
	    		Collectors.minBy(Comparator.naturalOrder())))); 
	    System.out.println("idk: " + tc50);
	    
	    
	    Chapter4 tc52 = new Chapter4();
	    tc52.tc51 = 1;	
	    tc52.bConsumer(tc51_1 -> System.out.println(tc52.tc51), tc52.tc51);
	    
	    tc52.bSupplier(Comparator::reverseOrder);
	    
	    Stream<Integer> tc53 = Stream.iterate(1, tc53_1 -> tc53_1+1);
	    tc53.peek(tc53_2 -> System.out.println("peek" + tc53_2))
	    .limit(3)
	    .forEach(tc53_3 -> System.out.println("for each" + tc53_3));
	    ;
	    
	    DoubleStream tc54 = DoubleStream.of(1,3,5);
	    Stream<String> tc55 = tc54.mapToObj(tc54_1 -> "double value is : " + tc54_1);
	    Stream tc56 = Stream.of(1,3,5);
	    
	    List<String> tc57 = Arrays.asList("an","asdf", "sadf");
	    Stream<String> tc58 = tc57.stream();
	    //tc58.mapToInt(String::length);
	    //tc58.flatMapToInt(String::length);DOES NOT COMPILE, flatMapToInt takes a IntStream.
	    tc58.flatMapToInt(tc58_1 -> IntStream.of(tc58_1.length()));
	    
	    Stream<Integer> tc59 = Stream.of(1,3,5);
	    Double tc60 = tc59.collect(Collectors.averagingDouble(tc59_1 -> tc59_1));
	    
	    Stream<String> tc61 = Stream.iterate("", tc61_1 -> tc61_1 + "a");
	    Map<Integer,Long> tc62 = tc61.limit(5).collect(Collectors.groupingBy(String::length, Collectors.counting()));
	    System.out.println("grouping by : " + tc62);
	    
	    //Stream.iterate(1, x -> x++).limit(5).map(x -> x).collect(Collectors.joining());DOES NOT COMPILE, need a String
	    String tc65 = Stream.iterate(1, x -> ++x).limit(5).map(x -> "" + x).collect(Collectors.joining());
	    System.out.println("chapter4 question 11: " + tc65);
	    //System.out.println(Stream.iterate(1, x -> x++).limit(5).map(x -> x).collect(Collectors.joining()));//DOES NOT COMPILE
	    Consumer<String> tc63 = x -> {new String();};
	    //Function<Integer> tc64 = x -> x*x; //DOES NOT COMPILE
	    
	    BiFunction<String, String, Boolean> tc66 = String::startsWith;
	    
	    int tc64[] = new int[] {1,2,3};
	    int tc67[] = new int[] {4,5,6};
	    List<Integer> tc68 = Arrays.asList(1,2,3);
	    List<Integer> tc69 = Arrays.asList(1,2,3);
	    Stream<Integer> tc70 = Stream.of(tc68, tc69).flatMap(x -> x.stream());
	    
	    Stream<Integer> tc71 = Stream.iterate(1, n -> n+1);
	    System.out.println("findAny example:" + tc71.findAny());
	    
	    System.out.println(Stream.of(1,3,5).reduce(1, (x,y) -> x-y, (x,y) -> x+y));
	    
	    List<Integer> tc72 = new ArrayList<Integer>();
	    tc72.add(1);tc72.add(2);tc72.add(3);
	    IntStream tc73 = tc72.stream().flatMapToInt(x -> IntStream.of(x));
	    
	    /**********************Collectors.mapping() example **************/
	    Stream<String> tc74 = Stream.of("first", "length", "asdfsaf", "ffsasfe", "sdfefeeee");
	    Map<Integer, Optional<Character>> tc75 = tc74.collect(Collectors.groupingBy(x -> x.length(),
	    		Collectors.mapping(s -> s.charAt(0), Collectors.minBy(Comparator.naturalOrder()))));;
	    		
	    Consumer<String> tc76 = String::new;
	    
	    List<Integer> tc77 = Arrays.asList(1,2,3);
	    List<Integer> tc78 = Arrays.asList(4,5,6);
	    List<Integer> tc79 = Arrays.asList();
	    Stream<Integer> tc80 = Stream.of(tc77, tc78, tc79).map(x -> x.size());
	    
	    Stream<String> tc81 = Stream.of("abc", "bcd");
	    //Stream<Integer> tc82 = tc81.mapToInt(s -> s.length());DOES NOT COMPILE
	}
	
	static <T> void aSupplier(Supplier<T> s) {
		System.out.println(s.get());
	}
	
	<T> void bSupplier(Supplier<T> s) {
		System.out.println(s.get());
	}

	
	static <T> void aConsumer(Consumer<T> c, T t) {
		c.accept(t);
	}
	
	<T> void bConsumer(Consumer<T> c, T t) {
		c.accept(t);
	}
	
	static <T, R> void aBIConsumer(BiConsumer<T, R> bi, T t, R r)  {
		bi.accept(t, r);
	}
	
	static <T, R> void aBIPredicate(BiPredicate<T,R> bi, T t, R r) {
		System.out.println(bi.test(t, r));
	}
	
	static <T,U,R> void aBIFunction(BiFunction<T,U,R> bi, T t, U u) {
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
		  return
				  Optional.of(s.length()); 
		  }

	
	/*
	 * static int calculator(String s) { return s.length(); }
	 */

}
