package Chapter6;

import java.io.*;

class OutOfTuneException extends Exception {
	OutOfTuneException(String message){
		super(message);
	}
}

public class Piano {
	public void play() throws OutOfTuneException, FileNotFoundException{
		throw new OutOfTuneException("Sour Note");
	}
	
	public static void main(String ... keys) throws OutOfTuneException{
		final Piano piano = new Piano();
		try {
			piano.play();
		} catch (Exception e) {
			//throw e;// does not compile
		} finally {
			System.out.println("Song finished");
		}
	}
}