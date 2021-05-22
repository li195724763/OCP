package Chapter2;

public class HayStorage{ 
  private int quantity = 0;

  private static final HayStorage instance = new HayStorage();//singleton in declaration.

  private HayStorage(){}//private constructor

  public static HayStorage getHayStorage(){
    return instance;
  }  	

  public synchronized void addHay(int hay){
    quantity = quantity + hay;
  }

  public synchronized boolean removeHay(int removeAmount) {
    if(quantity < removeAmount) {
      return false;
    } else {
        quantity = quantity - removeAmount;
        return true;
      }
  }	

  public synchronized int getHayQuantity() {
    return quantity;
  }
  
}