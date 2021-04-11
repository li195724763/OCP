package Chapter6;

import java.io.Closeable;
import java.io.IOException;

public class Chicken implements Closeable{

	
	@Override
	public void close() throws  IOException{
		
		// TODO Auto-generated method stub
		count++;
		count++;
		System.out.println("implemented Closeable: " + count);
	}
	
	static int count = 0;

}
