package Chapter8;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class Chapter8 {
	public static void main(String args[]) {
		testFileSeparator();
		testIfFileMethod();
		testMarkAndReset();
		testSkip();
		//testInputOutputFileStream();
		testBufferedInputOutputStream();
		testBufferedReaderWriter();
	}
	
	public static void testFileSeparator() {
		System.out.println(System.getProperty("file.separator"));//the "file.separator" is case-sensitive, return null if not match.
		System.out.println(File.separator);
	}
	
	public static void testIfFileMethod() {
		File file = new File("C:/AMD/test");
		System.out.println(file.exists());
	}
	
	public static void testMarkAndReset() {
		try(InputStream inputStream1 = new BufferedInputStream(new FileInputStream("C:\\Java\\TestFile.txt"))){
			if(inputStream1.markSupported()) {	
				System.out.println((char)inputStream1.read());
				inputStream1.mark(100);
				System.out.print("testInputOutputStream test mark() and reset(), before calling reset(): ");
				System.out.print((char)inputStream1.read());
				System.out.print((char)inputStream1.read());
				//System.out.print((char)inputStream1.read());
				System.out.println("");
				inputStream1.reset();
			}
			System.out.print("testInputOutputStream test mark() and reset(), after calling reset(): ");
			System.out.print((char)inputStream1.read());
			System.out.print((char)inputStream1.read());
			System.out.print((char)inputStream1.read());
			System.out.println("");
		} catch(IOException e) {
			
		}

	}
	
	public static void testSkip() {
		try(InputStream is = new  FileInputStream("C:\\Java\\TestFile.txt")){
			System.out.println((char)is.read());
			is.skip(2);
			System.out.println((char)is.read());
		} catch (FileNotFoundException e) {
	
		} catch (IOException e) {
		}
	}
	
	public static void testInputOutputFileStream() {
		try(InputStream input = new FileInputStream("C:\\Java\\TestFile.txt") ; OutputStream output = new FileOutputStream("C:\\Java\\TestOutput.txt")){
			int i;
			while((i = input.read()) != -1) {
				output.write(i);
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void testBufferedInputOutputStream() {
		try(InputStream input = new BufferedInputStream(new FileInputStream("C:\\Java\\TestFile.txt"));
				OutputStream output = new BufferedOutputStream(new FileOutputStream("C:\\Java\\TestOutput.txt"))){
			
			byte[] buffer = new byte[1024];
			while(input.read(buffer) > 0 ) {
				output.write(buffer);
				output.flush();
			}
			
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
	
	public static void testBufferedReaderWriter() {
		List<String> readResult = new ArrayList<>();
		String data = "";
		try(BufferedReader read = new BufferedReader(new FileReader("C:\\Java\\TestBufferReader.txt"))){
			while((data = read.readLine()) != null) {
				readResult.add(data);
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try(BufferedWriter write = new BufferedWriter(new FileWriter("C:\\Java\\TestBufferWriter.txt"))){
			for(String string : readResult) {
				write.write(string);
				write.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
