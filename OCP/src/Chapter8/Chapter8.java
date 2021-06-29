package Chapter8;
import java.io.*;
import java.util.*;

public class Chapter8 {
	public static void main(String args[]) {
		testFileSeparator();
		testIfFileMethod();
		testMarkAndReset();
		testSkip();
		//testInputOutputFileStream();
		testBufferedInputOutputStream();
		testBufferedReaderWriter();
		testPrintWriter();
		testSystemInAndConsole();
		question23();
	}
	
	public static void testFileSeparator() {
		System.out.println(System.getProperty("file.separator"));//the "file.separator" is case-sensitive, return null if not match.
		System.out.println(File.separator);
		System.out.print(System.getProperty("line.separator"));
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
		//List<String> readResult = new ArrayList<>();
		String data;
		try(BufferedReader read = new BufferedReader(new FileReader("C:\\Java\\TestBufferReader.txt")); 
				BufferedWriter write = new BufferedWriter(new FileWriter("C:\\Java\\TestBufferWriter.txt"))){
			while((data = read.readLine()) != null) {
				write.write(data);
				write.newLine();
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		/*try(BufferedWriter write = new BufferedWriter(new FileWriter("C:\\Java\\TestBufferWriter.txt"))){
			for(String string : readResult) {
				write.write(string);
				write.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		
	}
	
	public static void testPrintWriter() {
		try(PrintWriter out = new PrintWriter("C:\\Java\\TestPrintWriter.txt")) {			
			out.write("hellow world1");
			out.println();
			out.format("calling format s", "YYYYDDMM");// first part is the format, second part and so on falls to the varargs. 
			out.println();
			out.print("Hellow world2");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("here");
			e.printStackTrace();
		}
	}
	
	
	public static void testSystemInAndConsole() {
		
		try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
			String input = br.readLine();
			System.out.println("The old way of reading input: " + input);
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Console c = System.console();
		if(c != null) {
			String input2 = c.readLine();
			//c.writer().println("The new way of reading input" + input2);
			
		}
	}
	
	public static void question23() {
		final StringBuilder sb = new StringBuilder();
		try(InputStream is = new BufferedInputStream(new FileInputStream("C:\\Java\\TestQuestion23.txt"))){
			if(is.markSupported()) {	
				int count = 3;
				is.mark(count);			
				for(int i=0;i<count;i++) {
					sb.append((char)is.read());
					is.reset();
					is.skip(1);
					sb.append((char)is.read());
				}
			}

		} catch(IOException e) {
			
		}
		
		System.out.println(sb);
	}
}
