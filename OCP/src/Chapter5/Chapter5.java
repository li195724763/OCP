package Chapter5;

import java.text.NumberFormat;
import java.text.ParseException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;


public class Chapter5 {
	public static final LocalDate DATE = LocalDate.now();
	public static final LocalTime TIME = LocalTime.now();
	public static final Instant INSTANT_NOW = Instant.now();
	public static final ZonedDateTime ZONED_TIME = ZonedDateTime.now();
	public static final LocalDateTime DATE_TIME = LocalDateTime.now();
	public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT, FormatStyle.MEDIUM);
	public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM);
	public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);
	public static final DateTimeFormatter CUSTIMIZED_MMMM_DD_YYYY = DateTimeFormatter.ofPattern("MMMM dd yyyy", Locale.CANADA);
	public static final ZoneId Zone = ZoneId.of("US/Eastern");
	public static final ZoneId DEFAULT_ZONE = ZoneId.systemDefault();
	public static final Locale EN = new Locale("en", "US");
	public static final Locale FR = new Locale("fr", "FR");
	public static final NumberFormat NUM_FORMAT = NumberFormat.getInstance();
	public static final NumberFormat MONEY_FORMAT = NumberFormat.getCurrencyInstance();
	public static final Optional<String> ZONE_WITH_LIKE = ZoneId.getAvailableZoneIds().stream().filter(s -> s.contains("US") || s.contains("CA")).findFirst();
	
	public static void main(String[] args) {	
		testZonedTime();
		testInstant();
		testLocalTime_LocalDate();
		testPeriod();
		testDuration();
		testChronoUnit();
		testBuildLocale();
		
		testResourceBundleAndProperties(EN);
		testResourceBundleAndProperties(FR);

		testNumberFomatter(1_3_00_0_0);
		testDateFormat();
		
		testEpoch();
		
		testStringComparasion();
		testStringReplace();
		testMissingKeyInPropertiesFileAndTestgetContents();
		testInvalidResourceBundleFile();
	}
	
	public static void testZonedTime() {
		ZonedDateTime tc3 = ZonedDateTime.of(LocalDateTime.now(), Zone);
		System.out.println("ZonedDateTime is " + tc3);
		
		//ZoneId zone = new ZoneId("");//Does not compile
		//ZoneId.getAvailableZoneIds().stream().filter(s->s.contains("US")).forEach(System.out::println);
		//ZonedDateTime tc12 = ZonedDateTime.of(2015, Month.APRIL, 15, 17,10,10,200, Zone); //DOES NOT COMPILE, ZonedDateTime does not support enum month
		ZonedDateTime tc12 = ZonedDateTime.of(2015, 4, 15, 17,10,10,200, Zone); 
		
		System.out.println("Converting ZonedDateTime to instant " + ZONED_TIME.toInstant());
	}
	
	public static void testInstant() {
		ZonedDateTime tc8 = ZonedDateTime.of(DATE, TIME, Zone);
		Instant tc9 = tc8.toInstant();
		Instant tc10 = Instant.now();
		System.out.println("testInstant1: " + tc10);
		System.out.print(tc8);System.out.print("******");System.out.println(tc9);
		System.out.println("Instant method Chain: " + ZONED_TIME.toInstant().minus(1,ChronoUnit.HOURS).minus(1, ChronoUnit.DAYS));
		
		//Instant.now().plus(1, ChronoUnit.WEEKS); //EXCEPTION, instance does not support changing greater than DAYs
		Instant.now().plus(1, ChronoUnit.DAYS);//No Exception
		Instant.now().plus(1, ChronoUnit.HOURS);//No Exception
		//Instant.now().plus(1, ChronoUnit.YEARS);
		//Instant.now().plus(1, ChronoUnit.MONTHS); // UnsupportedTemporalTypeException: Unsupported unit: Months
	}
	
	public static void testLocalTime_LocalDate () {
		//LocalTime tc1 = LocalTime.of(25, 61,61);//Throw Exception, Invalid value for HourOfDay (valid values 0 - 23): 25
		
		System.out.println(LocalDate.of(2021, Month.JANUARY, 29).plusMonths(1));
		System.out.println(LocalDate.of(2021, Month.MARCH, 29).minusMonths(1));
	}
	
	public static void testPeriod() {
		Period tc5 = Period.of(0,1,3);
		//Period p1 = Period.of(1,3);
		System.out.println("a Period: " + tc5);
		
		System.out.println(Duration.of(1, ChronoUnit.MILLIS));
		//System.out.println(Period.of(1, ChronoUnit.MILLIS));DOES NOT COMPILE
		System.out.println("testPeriod: " + Period.ofYears(0001));
		System.out.println("testPeriod: " + Period.ofDays(5));
	}
	
	public static void testDuration() {
		Duration tc6 = Duration.ofDays(365);
		System.out.println("testDuration, a Duration: " + tc6);
		Duration tc7 = Duration.of(5, ChronoUnit.MILLIS);
		System.out.println("testDuration, a Duration: " + tc7);
		//tc7.plus(100, ChronoUnit.WEEKS);//EXCEPTION, greater than DAYS
		tc7.plusDays(100);
		
		LocalDateTime dateTime1 = LocalDateTime.now();
		LocalDateTime dateTime2 = LocalDateTime.now();
		LocalDate date1 = LocalDate.now();
		LocalDate date2 = LocalDate.now();
		LocalTime time1 = LocalTime.of(23, 24);
		LocalTime time2 = LocalTime.now();
		ZonedDateTime zonedDateTime = ZonedDateTime.of(2021, 01, 13, 23, 59, 1, 1, Zone.of("US/Eastern"));
		
		/******start Duration.between(para1, para2) test*****/
		//second paramter is a LocalDate
		//System.out.println("timing difference by using instant: " + Duration.between(date1, date2)); //UnsupportedTemporalTypeException, Unsupported unit: Seconds
		//System.out.println("timing difference by using instant: " + Duration.between(time1, date1)); //DateTimeException: Unable to obtain LocalTime from TemporalAccessor: 2021-06-04 of type java.time.LocalDate
		//System.out.println("timing difference by using instant: " + Duration.between(dateTime1, date1)); //DateTimeException: Unable to obtain LocalTime from TemporalAccessor: 2021-06-04 of type java.time.LocalDate
		//System.out.println("timing difference by using instant: " + Duration.between(zonedDateTime, date1));//DateTimeException: Unable to obtain ZonedTime from TemporalAccessor: 2021-06-04 of type java.time.LocalDate
		//System.out.println("timing difference by using instant: " + Duration.between(INSTANT_NOW, date1));//DateTimeException: Unable to obtain Instant from TemporalAccessor: 2021-06-04 of type java.time.LocalDate
		
		//Second parameter is a LocalTime
		//System.out.println("timing difference by using instant: " + Duration.between(date1, time2));//DateTimeException: Unable to obtain LocalDate from TemporalAccessor: 15:46:09.850819400 of type java.time.LocalTime
		System.out.println("timing difference by using instant: " + Duration.between(time1, time2));
		//System.out.println("timing difference by using instant: " + Duration.between(dateTime1, time2));//DateTimeException: Unable to obtain LocalDateTime from TemporalAccessor: 15:47:35.172720200 of type java.time.LocalTime
		//System.out.println("timing difference by using instant: " + Duration.between(zonedDateTime, time2));//DateTimeException: Unable to obtain ZonedDateTime from TemporalAccessor: 15:48:11.216295500 of type java.time.LocalTime
		//System.out.println("timing difference by using instant: " + Duration.between(INSTANT_NOW, time2));//DateTimeException: Unable to obtain Instant from TemporalAccessor: 15:48:44.619259300 of type java.time.LocalTime
		
		//Second parameter is a LocalDateTime
		//System.out.println("timing difference by using instant: " + Duration.between(date1, dateTime2)); UnsupportedTemporalTypeException: Unsupported unit: Seconds
		System.out.println("timing difference by using instant: " + Duration.between(time1, dateTime2)); 
		System.out.println("timing difference by using instant: " + Duration.between(dateTime1, dateTime2)); 
		//System.out.println("timing difference by using instant: " + Duration.between(zonedDateTime, dateTime2));//DateTimeException: Unable to obtain ZonedDateTime from TemporalAccessor: 2021-06-04T15:51:27.350768800 of type java.time.LocalDateTime
		//System.out.println("timing difference by using instant: " + Duration.between(INSTANT_NOW, dateTime2));//UnsupportedTemporalTypeException: Unsupported field: InstantSeconds
		
		//Second parameter is a ZonedDateTime
		//System.out.println("timing difference by using instant: " + Duration.between(date1, zonedDateTime)); //UnsupportedTemporalTypeException: Unsupported unit: Seconds
		System.out.println("timing difference by using time1 and zonedDateTime: " + Duration.between(time1, zonedDateTime));
		System.out.println("timing difference by using time1 and zonedDateTime: " + Duration.between(dateTime1, zonedDateTime));
		System.out.println("timing difference by using time1 and zonedDateTime: " + Duration.between(zonedDateTime, zonedDateTime));
		System.out.println("timing difference by using time1 and zonedDateTime: " + Duration.between(INSTANT_NOW, zonedDateTime));//no exception since java can get a instant from zoned date time
		//System.out.println("timing difference by using time1 and zonedDateTime: " + Duration.between(zonedDateTime, INSTANT_NOW));//DateTimeException: Unable to obtain ZonedDateTime from TemporalAccessor: 2021-06-04T20:23:13.143362700Z of type java.time.Instant
		
		//Second parameter is a Instant
		//System.out.println("timing difference by using time1 and zonedDateTime: " + Duration.between(date1, INSTANT_NOW));//DateTimeException: Unable to obtain LocalDate from TemporalAccessor: 2021-06-04T20:20:38.544472400Z of type java.time.Instant
		//System.out.println("timing difference by using time1 and zonedDateTime: " + Duration.between(time1, INSTANT_NOW));//DateTimeException: Unable to obtain LocalTime from TemporalAccessor: 2021-06-04T20:21:41.386924600Z of type java.time.Instant
		//System.out.println("timing difference by using time1 and zonedDateTime: " + Duration.between(dateTime1, INSTANT_NOW));//DateTimeException: Unable to obtain LocalDateTime from TemporalAccessor: 2021-06-04T20:22:17.152569300Z of type java.time.Instant
		//System.out.println("timing difference by using time1 and zonedDateTime: " + Duration.between(zonedDateTime, INSTANT_NOW));//DateTimeException: Unable to obtain ZonedDateTime from TemporalAccessor: 2021-06-04T20:23:13.143362700Z of type java.time.Instant
		System.out.println("timing difference by using time1 and zonedDateTime: " + Duration.between(INSTANT_NOW, INSTANT_NOW));
		/******end Duration.between(para1, para2) test*****/
		
		System.out.println(Duration.of(1, ChronoUnit.MILLIS));
		//System.out.println(Period.of(1, ChronoUnit.MILLIS));DOES NOT COMPILE
		
		System.out.println(Duration.ofHours(1).ofSeconds(1));
	}
	
	public static void testChronoUnit() {
		// ZONED_TIME, INSTANT_NOW
		LocalDateTime dateTime1 = LocalDateTime.now();
		LocalDateTime dateTime2 = LocalDateTime.now();
		
		LocalDate date1 = LocalDate.now();
		LocalDate date2 = LocalDate.now();
		
		LocalTime time1 = LocalTime.of(23, 24);
		LocalTime time2 = LocalTime.now();
		
		//second parameter is LocalDate
		ChronoUnit.DAYS.between(date1, date2);
		ChronoUnit.CENTURIES.between(date1, date2);
		//ChronoUnit.HALF_DAYS.between(date1, date2);//Exception
		//ChronoUnit.HOURS.between(date1, date1);//EXCEPTION ! ! !
		//ChronoUnit.DAYS.between(INSTANT_NOW, date2);
		
		//ChronoUnit.DAYS.between(date1, time1);//Exception
		//ChronoUnit.HALF_DAYS.between(date1, time1);//Exception
		
		//second parameter is a LocalTime, 
		//ChronoUnit.DAYS.between(time1, time2);//Exception
		//ChronoUnit.DAYS.between(INSTANT_NOW, time2);
		ChronoUnit.HALF_DAYS.between(time1, time2);//No Exception
		
		//second parameter is a LocalDateTime
		ChronoUnit.DAYS.between(date1, dateTime2);
		//ChronoUnit.HALF_DAYS.between(date1, dateTime2);//Exception due to HALF_DAYS on LocalDate
		ChronoUnit.HALF_DAYS.between(time1, dateTime2);
		//ChronoUnit.DAYS.between(time1, dateTime2);
		ChronoUnit.MINUTES.between(dateTime2, dateTime2);
		//ChronoUnit.MINUTES.between(ZONED_TIME, dateTime2);//Exception, Unable to obtain ZonedDateTime
		//ChronoUnit.MINUTES.between(ZONED_TIME, INSTANT_NOW);//Exception, Unable to obtain Instance
		
		//second parameter is a ZonedDateTime
		//ChronoUnit.HALF_DAYS.between(date1, ZONED_TIME);//exception due to HALF_DAYS on LocalDate
		//ChronoUnit.DAYS.between(time1, ZONED_TIME);//exception due to DAYS on LocalTime
		ChronoUnit.MILLIS.between(dateTime1, ZONED_TIME);
		ChronoUnit.DECADES.between(ZONED_TIME, ZONED_TIME);
		ChronoUnit.DAYS.between(INSTANT_NOW, ZONED_TIME);
		//ChronoUnit.WEEKS.between(INSTANT_NOW, ZONED_TIME);// Exception, due to instance does not support week unit
		
		//second parameter is an instant
		//ChronoUnit.DAYS.between(ZONED_TIME, INSTANT_NOW);//date1, time1,dateTime1, ZONED_TIME Exception
		ChronoUnit.DAYS.between(INSTANT_NOW, INSTANT_NOW);
		//ChronoUnit.WEEKS.between(INSTANT_NOW, INSTANT_NOW);//exception, instant does not support weeks
	}
	
	public static void testBuildLocale() {
		Locale locale = new Locale.Builder().setLanguage("en").setRegion("US").build();// Builder is a inner static class.
		System.out.println("using builder: " + locale);
		
		System.out.println("testBuildLocale bad local: " + new Locale.Builder().setLanguage("asdfsdf").setRegion("sd").build());
		
		Locale tc11 = Locale.getDefault();
		System.out.println("my first Locale of Java : " + tc11);
		
		System.out.println(new Locale("Ella", "Zhizhi"));
		//Locale.setDefault(new Locale("fr"));
		System.out.println("set default Locale: " + Locale.getDefault());
			
		Locale tc_invalid = new Locale("HI", "HI");
		Locale tc_builder = new Locale.Builder().setLanguage("en").build();
		
	}
	
	public static void testResourceBundleAndProperties(Locale locale) {
		//ResourceBundle rnrn = new ResourceBundle();//ResourceBundle is abstract class
		ResourceBundle rb = ResourceBundle.getBundle("Chapter5/Zoo", locale);//FULL PATH IS NEEDED!!!!, or using "Chapter5/Zoo" to force the system pick .properties files.
		//ResourceBundle rb = ResourceBundle.getBundle("Chapter5.Zoo", locale);//this is using .java class, package format is mandentory.
		Properties property1 = new Properties();
		
		System.out.println(rb.getBaseBundleName());
		
		//System.out.println("under method printLocaleMessage() " + rb.getString("open"));
		//rb.keySet().stream().map(s -> s + " " + rb.getString(s)).forEach(System.out::println);
		rb.keySet().stream().forEach(s -> property1.put(s, rb.getObject(s)));
		
		System.out.println("testResourceBundle(), passing in Locale is: " + locale);
		System.out.println("testResourceBundle() resource boundle keyset: " + rb.keySet());
		System.out.println("testResourceBundle() resource boundle: " + rb.getObject("open"));

		//System.out.println("testResourceBundle(), selected resource bundle: " + rb.getBaseBundleName()); 
		System.out.println("testResourceBundle() a property not exist: " + property1.getProperty("asfdsafdasf",  "this is a not exist properties"));//!!!!!!Throw ClassCastException if the value retrieved is NOT a String
		System.out.println("testResourceBundle() property1: " + property1.getProperty("hello"));//throw ClassCastException if the returned property from java class is not a String
		System.out.println("testResourceBundle() property1: " + property1.get("another"));
		System.out.println("testResourceBundle() property1: " + property1.get("anotheragain"));
		
		//Converting a ResourceBundle to a Properties:
		Properties property2 = new Properties();
		rb.keySet().stream().forEach(s -> property2.put(s, rb.getString(s)));
		
		System.out.println("the new property2 is " + property2);
		//System.out.println("111get missingKeyInProperties: " + rb.getString("KeyInPropertiesOnly"));
		//rb.getString("KeyInJavaFileOnly");//throw exception, if choosing Zoo.properties, since Zoo.java is not the parent of Zoo.properties
		//rb.getString("KeyInPropertiesOnly");//throw exception, if choosing Zoo.java, since Zoo.properties is not the parent of Zoo.java
		
	}
	
	public static void testNumberFomatter(int num) {
		int attendeePerMonth = num/12;
		Locale locale = new Locale("en", "US");
		System.out.println("testNumberFomatter, no Locale: " + NumberFormat.getInstance().format(num));
		System.out.println("testNumberFomatter, default Locale: " + NumberFormat.getInstance(Locale.CANADA_FRENCH).format(num));
		System.out.println("testNumberFomatter, Specific Locale: " + NumberFormat.getInstance(Locale.getDefault()).format(num));
		System.out.println("testNumberFomatter, money formatter: " + NumberFormat.getCurrencyInstance(Locale.US).format(num));
		
		String s1 = "40.5";
		String s2 = "$40.5";
		try {
			System.out.println(NUM_FORMAT.parse(s1)); 
			System.out.println(NUM_FORMAT.parse("40.5fasnfkefn"));// 40.5
			System.out.println(NUM_FORMAT.parse("4fasnfkefn0.5"));// 4
			System.out.println(NUM_FORMAT.parse("40.$5"));// 40
			//System.out.println(NUM_FORMAT.parse("fasnfkefn40.5"));//ParseException: Unparseable number: "fasnfkefn40.5"
			System.out.println(MONEY_FORMAT.parse(s2)); //the "string" needs to have a "$", otherwise throw ParseException
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
			
		//Number nm = new Number();Number is an abstract class
	}
	
	public static void testDateFormat() {
		/*Compiles but throw UnsupportedTemporalTypeException;//missing time
		 * DATE.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);//missing time
		 * DATE.format(DateTimeFormatter.ISO_LOCAL_TIME);//missing time
		 * DATE_TIME_FORMATTER.format(DATE);//missing time
		 * CUSTIMIZED_MMMM_DD_YYYY.format(TIME);//missing date
		
		
		//DOES NOT COMPILE
		 * LocalDate.parse(date); //Trying to parse a String without passing a formatter: MMMM_DD_YYYY)
		 * DateTimeFormatter wrong = new DateTimeFormatter();//must use factory method, can't use keyword "new"
		 * DATE_TIME.format(DATE_TIME);//DOES NOT COMPILE, LocalDateTime needs to take a DateTimeFormater as input parameter.
		 * DateTimeFormatter ff = DateTimeFormatter.ofLocalizedDate(MMMM_DD_YYYY);// DOES NOT COMPILE, ofLocalizedDate can't use customized formatter.
		 * DateTimeFormatter f2 = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT, FormatStyle.MEDIUM);//DOES NOT COMPILE, can only take one FormatStyle for ofLocalizedDate
		 * DateTimeFormatter f3 = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT, FormatStyle.MEDIUM);//DOES NOT COMPILE, can only take one FormatStyle for ofLocalizedTime
		 * DateTimeFormatter f4 = DateTimeFormatter.ofLocalizedTime();//DOES NOT COMPILE, must take one FormatStyle
		*/
		
		DateTimeFormatter f1 = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT, FormatStyle.MEDIUM);
		LocalDate.parse("2015-01-01");

		ZONED_TIME.format(DateTimeFormatter.ISO_DATE);
		System.out.println(DATE_TIME_FORMATTER.format(DATE_TIME));
		System.out.println(DATE_TIME.format(DATE_TIME_FORMATTER));
		System.out.println(DATE_TIME.format(CUSTIMIZED_MMMM_DD_YYYY));
		System.out.println(CUSTIMIZED_MMMM_DD_YYYY.format(DATE_TIME));
		System.out.println(DATE_TIME.format(DateTimeFormatter.ISO_DATE_TIME));
		System.out.println(DateTimeFormatter.ISO_DATE_TIME.format(DATE_TIME));
		//DATE.format(DateTimeFormatter.ISO_DATE_TIME);//throw exception
			
		
		String date = "April 02 2015";//will throw exception if the date String does not match the passing in pattern 
		System.out.println(LocalDate.parse(date,CUSTIMIZED_MMMM_DD_YYYY)); 
		//System.out.println(ZonedDateTime.parse("April 02 2015",CUSTIMIZED_MMMM_DD_YYYY)); // parse exception, no Zone provided.
		String dateDefault = "2015-04-03";//Default date format
		System.out.println(LocalDate.parse(dateDefault));
		System.out.println(LocalTime.parse("11:20")); //NO need to pass a formatter if LocalTime.parse
		//System.out.println(LocalDate.parse(date, MMMM_DD_YYYY));
		
		DateTimeFormatter f2 = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL);
		DateTimeFormatter f3 = DateTimeFormatter.ISO_DATE;
		DateTimeFormatter f4 = DateTimeFormatter.ISO_TIME;
		ZonedDateTime z1 = ZonedDateTime.now();
		f2.format(z1);
		z1.format(f2);
		
		f3.format(z1);
		z1.format(f3);
		
		f4.format(z1);
		z1.format(f4);
	}
	
	public static void testEpoch() {
		System.out.println(ZONED_TIME.toEpochSecond());
		System.out.println("ofEpochSecond: " + Instant.ofEpochSecond(0));
	}
	
	public static void testStringComparasion() {
		String tc13 = "ab";
		String tc14 = "a";
		String tc15 = "b";
		String tc16 = "a" + "b";
		String tc17 = tc14+tc15;
		System.out.println("String comparasion: ");
		System.out.println(tc13 == tc16);// return "true" due to string literal pool
		System.out.print("String tc17 is: " + tc17 + ". Is tc17 equals to String Literal? ");
		System.out.println(tc17==tc16);//return "false" due to reference comparasion
		System.out.println(tc17==tc13);// false
		System.out.println("a"+"b" == "ab");// true
		System.out.println("a"+"b" == tc13);// true
	
		String obj = new String("a");
		String literal_a = "a";
		String literal_a2 = "a";
		String literal_b = "b";
		
		String lit_lit = literal_a + literal_b;
		String mixed = obj + literal_b;
		String pureLit = "ab";
		System.out.println();
		System.out.print("1: "); System.out.println(literal_a == literal_a2);//true
		System.out.print("2: ");System.out.println(lit_lit == pureLit);//false
		System.out.print("3: ");System.out.println(lit_lit == "ab");//false
		System.out.print("4: ");System.out.println(pureLit == "ab");//true
		System.out.print("5: ");System.out.println(lit_lit.equals(pureLit));//true
		System.out.print("6: ");System.out.println(lit_lit == mixed);//false
	}
	
	public static void testStringReplace() {
		String tc17 = "aaaaaaa";
		System.out.println("testStringReplace: " + tc17.replace('a', 'b'));
		System.out.println("testStringReplace: " + tc17.replaceAll("aa", "bb"));
	}
	
	public static void testMissingKeyInPropertiesFileAndTestgetContents() {
		ResourceBundle rb = ResourceBundle.getBundle("Chapter5.Zoo_fr_FR", new Locale("fr", "FR"));
		System.out.println(rb.getString("anotheragain"));
		
		//System.out.println(rb.getString("asdfdasfsdaf")); throw MissResourceException if not able to find the key
		
		//test page 534, chapter22, question 22. does getContent() being executed when trying to get the property's value? No
		ResourceBundle rb_java = ResourceBundle.getBundle("Chapter5.Zoo_en_US", new Locale("en", "US"));
		List<String> list = (List<String>)rb_java.getObject("anArrayList");
		list.add("a");
		list.add("b");
		list.add("c");
		list.add("d");
		list.add("e");
		list.add("f");
		System.out.println("list size() is " + list.size());
		
		List<String> list2 = (List<String>)rb_java.getObject("anArrayList");
		System.out.println("get the same property again, not the list size() is " + list2.size());
		
		int count =  (Integer)rb_java.getObject("aCounter");
		System.out.println("first time getting count is " + count);
		
		int count2 =  (Integer)rb_java.getObject("aCounter");
		System.out.println("secoud time getting count is " + count2);
		
	}
	
	public static void testInvalidResourceBundleFile() {
		//ResourceBundle rb = ResourceBundle.getBundle("Chapter5.InvalidLocal", new Locale("en", "US"));
		//MissingResourceException. since there is no valid resource bundle, change "InvalidLocal_US.properties" to "InvalidLocal_en_US.properties" fix the problem
	}
	
}
