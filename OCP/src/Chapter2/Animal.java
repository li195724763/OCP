package Chapter2;
import java.util.*;

public final class Animal{ // prevent the method being overridden
  private final int age; //member should all marked as final
  private final int weight;
  private final List<String> food;

  public Animal(int age, int weight, List<String> food){
    this.age = age;
    this.weight = weight;
    //this.food = food;//should not assign directly!!! this is breaking immutability
    this.food = new ArrayList<String>(food);
  }

  public int getAge(){
    return age;
  }

  public int getWeight(){
    return weight;
  }

  public List<String> getFood(){
    List<String> returnedFood = new ArrayList<String>(food); //Should not return non-private instance member directly, otherwise breaks the immutability.
    return returnedFood;
  }

  @Override public String toString(){
    return "Immutable create pattern";
  }		
}


