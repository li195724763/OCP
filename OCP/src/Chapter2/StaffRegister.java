package Chapter2;

public class StaffRegister{
  public static final StaffRegister instance;
  static {
    instance = new StaffRegister();
  }  

  private StaffRegister(){}

  public static StaffRegister getInstance(){ //static get method
    return instance;
  }

  @Override public String toString(){
    return "Singleton : static initializer";
  }		
}


