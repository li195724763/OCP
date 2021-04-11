package Chapter1;
enum Season {WINTER, SPRING, SUMMER, FALL;} // must not be local(in a method), can be declared in Chapter1 or outside of Chapter1.

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

public class Chapter1{
  private int id;
  private int nestedVariable = 0;

  public static class staticInner{ //abstract doesn't compile
    private String i = "static inner i";
    private final String sFinal = "static inner i";
    private void printi(){
      System.out.println(i);
    }
    private void printOuterID(){
      System.out.println(new Chapter1().id); //DOES NOT COMPILE if using Chapter1.this.id
    }
  } 

  public abstract static class staticInner2{}//abstract allowed

  public final static class staticInner3{}//final allowed

		int asdfjsk = 1_00;
  
  abstract static class abstractMemberInner{//can be abstract static inner class
    abstract void testAbstract(); //allow abstract method.
    final void testFinal(){} //allow final method.
  } 

  protected class MemberInnerClass{ //Cannot mark as static
  private int nestedVariable = 1;
  //private static int nestedVariableStatic = 1;// static variable is not allowed in member inner class

    public void printHiThreeTimes(){
      System.out.print("Member inner class:  ");
      for(int i=0;i<3;i++){
	System.out.print("Hi ");
      }
      System.out.println();
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
  		
  public static void main(String args[]){
    Chapter1 myClass = new Chapter1();
    myClass.setID(1000);
    boolean nullInstanceOf = null instanceof  Chapter1 ; // null instanceof compiles
    System.out.println("null is instance of Charpter1Example ? " + nullInstanceOf);

    Runnable r = new implementRunnable();
    boolean interfaceInstanceOf = r instanceof Chapter1; //this Compiles, since Runnable is a interface, someone could implement Runnable and also a subclass of Chapter1.
    System.out.println("r is instance of Charpter1Example ? " + interfaceInstanceOf );

    //myClass.id = 1;
    System.out.println("Hello world!");
    System.out.println("the hashCode is " + myClass.hashCode());
    System.out.println("the hashCode of inner class object is " + myClass.returnInner().hashCode());
    //System.out.println("compile " + myClass.returnInner().localInner);//DOES NOT COMPILE, cannot access local inner class member
	
    for(Season s : Season.values()) {
       System.out.println("the enum season is " + s.name() + " " + s.ordinal());
    }   

    Season ss = Season.SUMMER;
    switch (ss) {
      case WINTER : System.out.println("enum WINTER"); break;
      case SUMMER: System.out.println("enum SUMMER"); break; //DOES NOT COMPILE if using Season.SUMMER.
      default : System.out.println("enum default"); 
    }

    //SeasonConstractor.WINTER.print();
    for(SeasonConstractor s : SeasonConstractor.values()) {
       s.print();
    }
    MemberInnerClass mInner = myClass.new MemberInnerClass();
    System.out.println("access inner class private variable " + mInner.nestedVariable);
    //MemberInnerClass mInner2 = Chapter1.new MemberInnerClass();//DOES NOT COMPILE, need an instance then .new
    mInner.printHiThreeTimes();
    myClass.calculate();

    Chapter1.MemberInnerClass.NestClass nestClass = mInner.new NestClass(); //this synatx is needed , since the second nested class is too deep for Java to find.
    nestClass.printInstanceVariable();

    staticInner si = new staticInner();
    si.printi();
   
    myClass.anonymousClass();
    myClass.anonymousInArguement();
  }

  public int hashCode(){ // return type has to be int.
    return id; //an improper implementation can still compiles.	E.g. the id can be changed by a public setID() method.
  }

  public void setID(int i){
    this.id = i;
  }

  public void calculate() {
    class Inner { //DOES NOT COMPILE if adding access modifier in local inner class
     public void multiply(int x, int y) {
       System.out.println("ID from outter class is " + Chapter1.this.id);
       System.out.println(x * y);
     }
    }
    Inner inner = new Inner();
    inner.multiply(20,200);
  }

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

  public void anonymousInArguement(){
    System.out.println(this.result(100, new annTest() { //DOES NOT COMPILE if missing the () after the class name
                                  public int result(){return 1;}
                                })
                      );
  }

  public int result(int i, annTest at){
    return i + at.result();
  }
}

class implementRunnable extends Chapter1 implements Runnable{
  public void run(){}
}