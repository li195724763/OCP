package Chapter2;

public class LazyInstantiation{
  private static volatile LazyInstantiation instance; //must be private static

  private LazyInstantiation(){}

  public static LazyInstantiation getInstance(){ //static get method
    if(instance == null) {
      synchronized(LazyInstantiation.class){ //smaller case class
        if(instance == null) {
          instance = new LazyInstantiation();
        }
      }
    }
    return instance;
  }

  @Override public String toString(){
    return "Singleton : Lazy Instantiation";
  }		
}


