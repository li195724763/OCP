package Chapter8;

import java.io.*;
import java.util.*;

public class TestSerialization {

	public static void main(String[] args) {
		File testData = new File("C:/Java/TestSerialization.txt");
		//System.out.println(testData.isFile());
		
		List<Animal> animals = new ArrayList<>();
		animals.add(new Animal("Tommy Tiger", 5, 'T'));
		animals.add(new Animal("Peter Penguin", 8, 'P'));
		Animal.testType = 'O';// if no overwrite, then use the default initialization, since testType is static. 
		System.out.println("start serializating ");
		createAnimalsFile(animals, testData);
		System.out.println("end serializating ");
		
		System.out.println("start deserializing");
		List<Animal> results = deserializationAnimal(testData);
		for(Animal result : results) {
			System.out.println(result.toString());
		}
		System.out.println("end deserializing");
	}
	
	public static void createAnimalsFile(List<Animal> animals, File testData) {
		try(ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(testData));){
			for(Animal animal : animals) {
				output.writeObject(animal);
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static List<Animal> deserializationAnimal(File file) {
		List<Animal> animals = new ArrayList<>();
		try(ObjectInputStream input = new ObjectInputStream(new FileInputStream(file))){
			while(true) {
				Object o = input.readObject();
				if(o instanceof Animal) {
					animals.add((Animal)o);
				}
			}
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (EOFException e) {
			//e.printStackTrace();//file end, swollow this exception as it's not an exception needs to handle
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return animals;
	}

}
