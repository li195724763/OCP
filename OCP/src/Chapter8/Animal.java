package Chapter8;

import java.io.Serializable;
import java.util.stream.Stream;

public class Animal implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private  String name;
	private transient int age = 10; // the value will be ignored during deserialization process, since the initialization will be ignored during deserialization.
	private char type;
	public static char testType = 'a';
	private Object tail;
	
	public Animal(String name, int age, char type) {
		this.name = name;
		this.age = age;
		this.type = type;
		this.tail = tail;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getAge() {
		return this.age;
	}
	
	public char getType() {
		return this.type;
	}
	
	public void setTail(Object tail) {
		this.tail = tail;
	}
	
	@Override
	public String toString() {
		return "Animal [name=" + name + ", age=" + age + ", type=" + type + ", testType=" + testType + "]";
	}

}
