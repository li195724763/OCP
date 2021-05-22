package Chapter2;
import java.util.*;

public class Chapter2{
  		
  public static void main(String args[]){
    HayStorage hs = HayStorage.getHayStorage();
    hs.addHay(100);
    System.out.println(hs.removeHay(1));

    StaffRegister sr = StaffRegister.getInstance();
    System.out.println(sr.toString());
    
    LazyInstantiation li = LazyInstantiation.getInstance();
    System.out.println(li.toString());

    List<String> food = new ArrayList<>();
    food.add("vaga");
    food.add("ice");
    food.add("cream");
    Animal animal = new Animal(100,100, food);
    food.add("changed!!!!!!1");// if assiging directly in constructor, this will break the immutability.
    System.out.println(animal.toString() + animal.getFood());
  }
}

