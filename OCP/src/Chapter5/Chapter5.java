package Chapter5;

import java.text.NumberFormat;
import java.text.ParseException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
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
	public static final DateTimeFormatter MMMM_DD_YYYY = DateTimeFormatter.ofPattern("MMMM dd yyyy", Locale.CANADA);
	public static final ZoneId Zone = ZoneId.of("US/Eastern");
	public static final ZoneId DEFAULT_ZONE = ZoneId.systemDefault();
	public static final Locale EN = new Locale("en", "US");
	public static final Locale FR = new Locale("fr", "FR");
	public static final NumberFormat NUM_FORMAT = NumberFormat.getInstance();
	public static final NumberFormat MONEY_FORMAT = NumberFormat.getCurrencyInstance();
	public static final Optional<String> ZONE_WITH_LIKE = ZoneId.getAvailableZoneIds().stream().filter(s -> s.contains("US") || s.contains("CA")).findFirst();
	
	public static void main(String[] args) {
		//ChronoUnit.HOURS.between(DATE, DATE);//EXCEPTION ! ! !
		testZonedTime();
		testInstant();
		testLocalTime_LocalDate();
		testPeriod();
		testDuration();
		testBuildLocale();
		
		testResourceBundle(EN);
		testResourceBundle(FR);

		testNumberFomatter(1_3_00_0_0);
		testDateFormat();
		
		testEpoch();
		
		testStringComparasion();
		testStringReplace();
	}
	
	public static void testZonedTime() {
		ZonedDateTime tc3 = ZonedDateTime.of(LocalDateTime.now(), Zone);
		System.out.println("ZonedDateTime is " + tc3);
		
		//ZoneId.getAvailableZoneIds().stream().filter(s->s.contains("US")).forEach(System.out::println);
		//ZonedDateTime tc12 = ZonedDateTime.of(2015, Month.APRIL, 15, 17,10,10,200, Zone); //DOES NOT COMPILE, ZonedDateTime does not support enum month
		
		System.out.println("Converting ZonedDateTime to instant " + ZONED_TIME.toInstant());
	}
	
	public static void testInstant() {
		ZonedDateTime tc8 = ZonedDateTime.of(DATE, TIME, Zone);
		Instant tc9 = tc8.toInstant();
		Instant tc10 = Instant.now();
		System.out.println("instant is " + tc10);
		System.out.print(tc8);System.out.print("******");System.out.println(tc9);
		System.out.println("Instant method Chain: " + ZONED_TIME.toInstant().minus(1,ChronoUnit.HOURS).minus(1, ChronoUnit.DAYS));
		
		//System.out.println(Instant.now().plus(1, ChronoUnit.WEEKS)); //EXCEPTION, instance does not support changing greater than DAYs
	}
	
	public static void testLocalTime_LocalDate () {
		//LocalTime tc1 = LocalTime.of(25, 61,61);//Throw Exception, Invalid value for HourOfDay (valid values 0 - 23): 25
		
		System.out.println(LocalDate.of(2021, Month.JANUARY, 29).plusMonths(1));
		System.out.println(LocalDate.of(2021, Month.MARCH, 29).minusMonths(1));
	}
	
	public static void testPeriod() {
		Period tc5 = Period.of(1,0,3);
		System.out.println("a Period: " + tc5);
		
		System.out.println(Duration.of(1, ChronoUnit.MILLIS));
		//System.out.println(Period.of(1, ChronoUnit.MILLIS));DOES NOT COMPILE
		System.out.println(Period.ofYears(0001));
	}
	
	public static void testDuration() {
		Duration tc6 = Duration.ofDays(365);
		System.out.println("a Duration: " + tc6);
		Duration tc7 = Duration.of(5, ChronoUnit.MILLIS);
		System.out.println("a Duration: " + tc7);
		LocalDateTime dateTime1 = LocalDateTime.now();
		LocalDateTime dateTime2 = LocalDateTime.now();
		
		LocalDate date1 = LocalDate.now();
		LocalDate date2 = LocalDate.now();
		
		ZonedDateTime zonedDateTime = ZonedDateTime.of(2021, 01, 13, 23, 59, 1, 1, Zone.of("US/Eastern"));
		
		//System.out.println("timing difference by using instant: " + Duration.between(INSTANT_NOW, date)); //throw DateTimeException, due to unable to obtain Instant. 
		//System.out.println("timing difference by using instant: " + Duration.between(INSTANT_NOW, dateTime)); //throw DateTimeException, due to unable to obtain Instant. 
		System.out.println("timing difference by using instant: " + Duration.between(INSTANT_NOW, zonedDateTime)); 
		System.out.println("timing difference by using instant: " + Duration.between(dateTime1, dateTime2)); 
		//System.out.println("timing difference by using instant: " + Duration.between(date1, date2)); //UnsupportedTemporalTypeException, Unsupported unit: Seconds
		
		System.out.println(Duration.of(1, ChronoUnit.MILLIS));
		//System.out.println(Period.of(1, ChronoUnit.MILLIS));DOES NOT COMPILE
		
		System.out.println(Duration.ofHours(1).ofSeconds(1));
	}
	
	public static void testBuildLocale() {
		Locale builder = new Locale.Builder().setLanguage("en").setRegion("US").build();	
		System.out.println("using builder: " + builder);
		
		Locale tc11 = Locale.getDefault();
		System.out.println("my first Locale of Java : " + tc11);
		
		System.out.println(new Locale("Ella", "Zhizhi"));
		//Locale.setDefault(new Locale("fr"));
		System.out.println("set default Locale: " + Locale.getDefault());
			
		Locale tc_invalid = new Locale("HI", "HI");
		Locale tc_builder = new Locale.Builder().setLanguage("en").build();
	}
	
	public static void testResourceBundle(Locale locale) {
		ResourceBundle rb = ResourceBundle.getBundle("Chapter5.Zoo", locale);//FULL PATH IS NEEDED!!!!
		Properties pp = new Properties();
		
		//System.out.println("under method printLocaleMessage() " + rb.getString("open"));
		//rb.keySet().stream().map(s -> s + " " + rb.getString(s)).forEach(System.out::println);
		rb.keySet().stream().forEach(s -> pp.put(s, rb.getString(s)));
		
		System.out.println(pp.getProperty("hellooo",  "this is a default properties"));//!!!!!!Throw ClassCastException if the value retrieved is NOT a String
		System.out.println("under method printLocaleMessage() " + pp.get("hello"));
		System.out.println("under method printLocaleMessage() " + pp.get("another"));
		System.out.println("under method printLocaleMessage() " + pp.get("anotheragain"));
		
		//Converting a ResourceBundle to a Properties:
		Properties p = new Properties();
		rb.keySet().stream().forEach(s -> p.put(s, rb.getString(s)));
		
		System.out.println("the converted properties is " + p);
	}
	
	public static void testNumberFomatter(int num) {
		int attendeePerMonth = num/12;
		Locale locale = new Locale("en", "US");
		System.out.println("no Locale: " + NumberFormat.getInstance().format(num));
		System.out.println("default Locale: " + NumberFormat.getInstance(Locale.CANADA_FRENCH).format(num));
		System.out.println("Specific Locale: " + NumberFormat.getInstance(Locale.getDefault()).format(num));
		System.out.println("money formatter: " + NumberFormat.getCurrencyInstance(Locale.US).format(num));
		
		String s1 = "40.5";
		String s2 = "$40.5";
		try {
			System.out.println(NUM_FORMAT.parse(s1)); //DOES NOT COMPILE, unhandled checked exception
			System.out.println(MONEY_FORMAT.parse(s2));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
			
	}
	
	public static void testDateFormat() {
		/*Throw UnsupportedTemporalTypeException 
		DATE.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
		DATE.format(DateTimeFormatter.ISO_LOCAL_TIME);
		DATE_TIME_FORMATTER.format(DATE);
		MMMM_DD_YYYY.format(TIME);
		LocalDate.parse(date); Trying to parse a String without passing a formatter: MMMM_DD_YYYY)
		
		DateTimeFormatter wrong = new DateTimeFormatter();
		System.out.println(DATE_TIME.format(DATE_TIME));DOES NOT COMPILE
		DateTimeFormatter ff = DateTimeFormatter.ofLocalizedDate(MMMM_DD_YYYY);// DOES NOT COMPILE, ofLocalizedDate can't use customized formatter.
		*/
		System.out.println(DATE_TIME_FORMATTER.format(DATE_TIME));
		System.out.println(DATE_TIME.format(DATE_TIME_FORMATTER));
		System.out.println(DATE_TIME.format(MMMM_DD_YYYY));
		System.out.println(MMMM_DD_YYYY.format(DATE_TIME));
		System.out.println(DATE_TIME.format(DateTimeFormatter.ISO_DATE_TIME));
		System.out.println(DateTimeFormatter.ISO_DATE_TIME.format(DATE_TIME));
			
		
		String date = "April 02 2015";//will throw exception if the date String does not match the passing in pattern 
		System.out.println(LocalDate.parse(date,MMMM_DD_YYYY));
		String dateDefault = "2015-04-03";//Default date format
		System.out.println(LocalDate.parse(dateDefault));
		System.out.println(LocalTime.parse("11:20")); //NO need to pass a formatter if LocalTime.parse
		//System.out.println(LocalDate.parse(date, MMMM_DD_YYYY));
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
		System.out.print("String comparasion: ");
		System.out.println(tc13 == tc16);// return "true" due to string literal pool
		System.out.print("String tc17 is: " + tc17 + ". Is tc17 equals to String Literal? ");
		System.out.println(tc17==tc16);//return "false" due to reference comparasion
	}
	
	public static void testStringReplace() {
		String tc17 = "aaaaaaa";
		System.out.println("replace test: " + tc17.replace('a', 'b'));
		System.out.println("replace test: " + tc17.replaceAll("aa", "bb"));
	}
	
}
