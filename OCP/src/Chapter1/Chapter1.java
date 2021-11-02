package Chapter1;

import java.util.ArrayList;

/*****Enum type of static inner class */
enum Season {WINTER, SPRING, SUMMER, FALL, unknow;} // must not be local(in a method), can be declared in Chapter1 or outside of Chapter1.
enum SeasonConstractor{
  WINTER("Cold"),
  SUMMER("Hot"),
  FALL("Cool"),
  SPRING("Calm");
  private String desc; // must after enum
  SeasonConstractor(String desc) {// constractor must be default or private 
    this.desc = desc;
  } 

  public void print(){
    System.out.println("in enum the desc is " + desc);
  }
}


/*****Main class*/
public class Chapter1{
  private static int identifier = 100;
  private int id;
  private int nestedVariable = 0;
  int asdfjsk = 1_00;
  public static Chapter1 myClass = new Chapter1();
  //System.out.println("fast"); DOES NOT COMPILE
	
/*****different type of static inner class */
  public static class StaticInner{
	    private String i = "static inner i";
	    private final String sFinal = "static inner i";
	    private void printi(){
	      System.out.println(i);
	    }
	    private void printOuterID(){
	      System.out.println(new Chapter1().id); //DOES NOT COMPILE if using Chapter1.this.id
	    }
  } 
  abstract static class abstractMemberInner{//can be abstract static inner class
    abstract void testAbstract(); //allow abstract method.
    final void testFinal(){} //allow final method.
  } 
  public final static class staticInner3{}//final allowed
  public abstract static class staticInner2{}//abstract allowed
  private final class innerPri1{}//can be private 
  private abstract class innerPri2{}//can be private 
  
  private static final class innerStaticPri1{}
  protected static abstract class innerStaticPri2{}
  private static interface innerStaticPri3{}// innter static interface
  

  /********Member inner class */
  protected class MemberInnerClass{ //Cannot mark as static
  private int nestedVariable = 1;
  //private static int nestedVariableStatic = 1;// static variable is not allowed in member inner class

    public void printHiThreeTimes(){
      System.out.print("Member inner class:  ");
      for(int i=0;i<3;i++){
	System.out.print("Hi ");
      }
      System.out.println();
      boolean accessOuterClassVariable = Chapter1.this.id > 1;
    }

    class NestClass{
      private int nestedVariable = 2;
      public void printInstanceVariable(){
        System.out.println("nestedVariable " + nestedVariable );
        System.out.println("this.nestedVariable " + this.nestedVariable );
        System.out.println("MemberInnerClass.this.nestedVariable " + MemberInnerClass.this.nestedVariable);
        System.out.println("Chapter1.this.nestedVariable " + Chapter1.this.nestedVariable);
      }		
    }
  }
  		
  /*******Main method*/
  public static void main(String args[]){
    
    myClass.setID(1000);
    //myClass.id = 1;
    //System.out.println("compile " + myClass.returnInner().localInner);//DOES NOT COMPILE, cannot access local inner class member
    Chapter1 classInMainMethod = new Chapter1();
    MemberInnerClass mInner = classInMainMethod.new MemberInnerClass();//can't use classname(Chapter1) here. 
    System.out.println("access inner class private variable " + mInner.nestedVariable);
    //MemberInnerClass mInner2 = Chapter1.new MemberInnerClass();//DOES NOT COMPILE, need an instance then .new
    mInner.printHiThreeTimes();
    System.out.println("accessing member inner class private variable: " + mInner.nestedVariable);

    Chapter1.MemberInnerClass.NestClass nestClass = mInner.new NestClass(); //this synatx is needed , since the second nested class is too deep for Java to find.
    Chapter1.MemberInnerClass.NestClass nestClass_2 = new Chapter1().new MemberInnerClass().new NestClass();
    nestClass.printInstanceVariable();

    Chapter1.StaticInner si = new StaticInner();
    si.printi();
   
    testHashCode();
    myClass.anonymousClass();
    myClass.anonymousInArguement();
    testEnum();
    testInstanceOf();
    myClass.testInnerClass();
    System.out.println("test local inner class under static method: " + testLocalInnerClassUnderStaticMethod());
    myClass.testStaticInnerClassUnderNonStaticLocalMethod();
    testStaticInnerClassUnderStaticLocalMethod();
    testExtendMemberInnerClassUnderStaticMethod();
  }
  
  public static void testHashCode() {
	    System.out.println("Hello world!");
	    System.out.println("the hashCode is " + myClass.hashCode());
	    System.out.println("the hashCode of inner class Chapter7Util.OBJECT is " + myClass.returnInner().hashCode());
  }
  
  public static void testInstanceOf() {
      boolean nullInstanceOf = null instanceof  Chapter1 ; // null instanceof compiles
      System.out.println("null is instance of Charpter1Example ? " + nullInstanceOf);

      Runnable r = new implementRunnable();
      boolean interfaceInstanceOf = r instanceof Chapter1; //this Compiles, since Runnable is a interface, someone could implement Runnable and also a subclass of Chapter1.
      System.out.println("r is instance of Charpter1Example ? " + interfaceInstanceOf );
	  
	  Runnable rr = new Thread();
	  
	  boolean result = rr instanceof ArrayList;
	  
	  System.out.println("result of testInstanceOf is: " + result);
	  
	  String[] sArray = new String[5];
	  System.out.print("test instanceof on Array type: ");
	  System.out.println(sArray instanceof Object[]);
  }
  
  public static void testEnum() {
	  
	  for(Season s : Season.values()) {
		  System.out.println("the enum season is " + s.name() + " " + s.ordinal() + " valueof() " + s.valueOf("WINTER"));
	  }   
	
	    Season ss = Season.SUMMER;
	    //Season ss_1 = Season.summer;//does not compile;
	    Season ss_2 = Season.unknow;
	    //Season ss_3 = Season.UNKNOW;//does not compile; value is case sensitive
	    switch (ss) {
	      case WINTER : System.out.println("enum WINTER"); break;
	      case SUMMER: System.out.println("enum SUMMER"); break; //DOES NOT COMPILE if using Season.SUMMER.
	      default : System.out.println("enum default"); 
	    }
	
	    SeasonConstractor.WINTER.print();
	    for(SeasonConstractor s : SeasonConstractor.values()) {
	    	System.out.println(SeasonConstractor.valueOf("WINTER"));//valueOf() works on enum class too
	        s.print();
	        SeasonConstractor.valueOf("WINTER").print();
	    }
	    
  }
  
  @Override
  public int hashCode(){ // return type has to be int.
    return id; //an improper implementation can still compiles.	E.g. the id can be changed by a public setID() method.
  }

  public void setID(int i){
    this.id = i;
  }
  
  /****local inner class */
  public void testInnerClass() {
    class Inner { //DOES NOT COMPILE if adding access modifier in local inner class, other than abstract
     public void multiply(int x, int y) {
       System.out.println("ID from outter class is " + Chapter1.this.id);
       System.out.println(x * y);
     }
    }
    Inner inner = new Inner();
    inner.multiply(20,200);
  }
  
  /****local inner class under static method*/
  public static int testLocalInnerClassUnderStaticMethod() {
	    class Inner { //DOES NOT COMPILE if adding access modifier in local inner class, other than abstract or final
	     public int multiply(int x, int y) {
	       System.out.println("ID from outter class is " + Chapter1.identifier);
	       System.out.println(x * y);
	       return x * y;
	     }
	    }
	    Inner inner = new Inner();
	    
	    
	    class a extends Inner {}
	    return inner.multiply(20,200);    
	  }

  /****local inner class */
  public Object returnInner() {
    abstract class abstractInner{}
    final class Inner { //DOES NOT COMPILE if adding access modifier in local inner class
     public final String localInner = "local inner class";//DOES NOT COMPILE if add static to memeber
     public void multiply(int x, int y) {
       System.out.println(x * y);
     }

     @Override public int hashCode(){
       return 999;
     }
    }
    Inner inner = new Inner();
    return inner;
  }

  /****anonymous class */
  public void anonymousClass(){
    int notFinal = 0;
    //notFinal = 1; //DOES NOT COMPILE if set to not effectively final.
    abstract class innerAnonymous{
      abstract void printMess();
    }

    innerAnonymous ia = new innerAnonymous()
                              {                    
                                //static String staticStr = "";//DOES NOT COMPILE to include static instance variable
                                public void printMess(){
                                  System.out.println("other class private in anonymous inner class " + Chapter1.this.id);
                                  System.out.println("local method variable in anonymous inner class " + notFinal);
                                  System.out.println("anonymous class implementation");
                                }
                                final void finalMethod(){
                                  System.out.println("anonymous class implementation, final method");
                                }
                                //abstract void abMethod(); //DOES NOT COMPILE if adding abstract method in anonymous class
                              };
    ia.printMess();
  }

  abstract class annTest{
    abstract int result();  

  }

  /****anonymous class passed as parameter */
  public void anonymousInArguement(){//
    System.out.println(anonymousInArguement(100, new annTest() { //DOES NOT COMPILE if missing the () after the class name
                                  public int result(){return 1;}
                                })
                      );
  }

  public int anonymousInArguement(int i, annTest at){
    return i + at.result();
  }
  
  /****static inner class under non-static local method */
  public void testStaticInnerClassUnderNonStaticLocalMethod() {
	  StaticInner sInner = new StaticInner();
	  Chapter1.StaticInner sInner_2 = new StaticInner();
	  Chapter1.StaticInner sInner_3 = new Chapter1.StaticInner();
  }
  
  public static void testStaticInnerClassUnderStaticLocalMethod() {
	  StaticInner sInner = new StaticInner();
	  Chapter1.StaticInner sInner_2 = new StaticInner();
  }
  
  public static void testExtendMemberInnerClassUnderStaticMethod() {
	//class a extends MemberInnerClass{}  //does not compile, can't extend a member inner class under a static method
	  MemberInnerClass inn = new Chapter1().new MemberInnerClass();
	  Chapter1 c = new Chapter1();

	  //class b extends StaticInner{}
  }
  
  public void testExtendMemberInnerClassUnderInstanceMethod() {
	  class a extends MemberInnerClass{}   
	  class b extends StaticInner{}
  }
}


class implementRunnable extends Chapter1 implements Runnable{
	  public void run(){}
	}
