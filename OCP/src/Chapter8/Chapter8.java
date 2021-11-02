package Chapter8;
import java.io.*;
import java.util.*;

public class Chapter8 {
	public static void main(String args[]) {
		testFileSeparator();
		testIfFileMethod();
		testMarkAndReset();
		testSkip();
		testInputOutputFileStream();
		testFileEnd();
		
		testBufferedInputOutputStream();
		testWriter();
		testBufferedReaderWriter();
		testPrintWriter();
		testSystemInAndConsole();//this message cause the program to hang. since it ask for an input
		testGetParent();
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
		
		File file2 = new File("src"+System.getProperty("file.separator")+"Chapter8"+System.getProperty("file.separator")+"RelativePath.txt");
		System.out.println("relative path test " + file2.exists());
		
		File testMkdir = new File("C:/AMD/testmkdirdd/testmkdir.txt");
		testMkdir.mkdir();
		
		File testMkdirs = new File("C:/AMD/testmkdirdd/testmkdir.txt");
		testMkdirs.mkdirs();
		
		for(File file1 : file.listFiles()) {
			
		}
		
		int [] a = new int[10];
		
		for(int i : a) {
			
		}
	}
	
	public static void testMarkAndReset() {
		try(InputStream inputStream1 = new BufferedInputStream(new FileInputStream("C:\\Java\\TestFile.txt")); 
				InputStream fileInput = new  FileInputStream("C:\\Java\\TestFile.txt")){
			boolean markSupported = fileInput.markSupported();
			System.out.println("is mark supported for FileInputStream? " + markSupported);
			System.out.println("is mark supported for BufferedInputStream? " + inputStream1.markSupported());
			fileInput.mark(3);
			//fileInput.reset();
			if(inputStream1.markSupported()) {	
				System.out.println("Begin testing mark() and reset"); 
				System.out.println((char)inputStream1.read());
				inputStream1.mark(-1);
				System.out.println("testInputOutputStream test mark() and reset(), before calling reset(): ");
				System.out.println((char)inputStream1.read());
				System.out.println((char)inputStream1.read());
				//System.out.print((char)inputStream1.read());
				inputStream1.reset();
			}
			System.out.println("testInputOutputStream test mark() and reset(), after calling reset(): ");
			System.out.println((char)inputStream1.read());
			System.out.println((char)inputStream1.read());
			System.out.println((char)inputStream1.read());
			System.out.println("End testing mark() and reset"); 
		} catch(IOException e) {
			e.printStackTrace();
		}

	}
	
	
	public static void testSkip() {
		try(InputStream is = new  FileInputStream("C:\\Java\\TestFile.txt")){
			System.out.println("testSkip: " + (char)is.read());
			long wantTOSkip = 2;
			long actualSkip = is.skip(wantTOSkip);
			System.out.println("testSkip after calling skip(): " + (char)is.read());
			System.out.println("Want to skip: " + wantTOSkip + ". Actual skip is : " + actualSkip);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void testInputOutputFileStream() {
		try(InputStream input = new FileInputStream("C:\\Java\\TestFile.txt") ; OutputStream output = new FileOutputStream("C:\\Java\\TestOutputByte.txt")){
			int i;
			byte[] byteArr = new byte[10];
			while((i = input.read(byteArr)) > 0) {// return the number of byte, 0 means the end of the input stream
				output.write(byteArr);
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void testFileEnd() {
		try(InputStream input = new FileInputStream("C:\\Java\\TestFileEnd.txt")){
			System.out.println("TestFileEnd: " + (char)input.read());//if there is a -1 in file, it will read() "-" first, then read 1
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
	
	public static void testWriter() {
		try(BufferedWriter w = new BufferedWriter(new FileWriter("C:\\Java\\Chapter18"))){
			//w.flush();
			
			new File("C:\\Java\\Chapter18mkdir.txt").mkdirs();
		}catch(IOException e) {
			
		}
	}
	
	public static void testBufferedReaderWriter() throws NullPointerException{
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
		try(PrintWriter out = new PrintWriter("C:\\Java\\TestPrintWriter.txt"); PrintWriter sysout = new PrintWriter(System.out)) {			
			out.write("hellow world1");
			out.println();
			out.format("calling format s, ", "YYYYDDMM");// first part is the format, second part and so on falls to the varargs. 
			out.printf("calling printf s", "YYYYDDMM");// first part is the format, second part and so on falls to the varargs. 
			out.println();
			out.print("Hellow world2");
			out.println();
			out.println("Hellow world3");
			Animal a = new Animal("wahaha", 5, 'W');
			//out.println();
			out.print(a);// write the object by using toString() method, this is not serialization.
			
			sysout.println("testPrintWriter, this is a message print from a PrintWriter wrapped a System.out");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void testSystemInAndConsole() {
		try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
			
			System.out.println("Enter the input !!!!!!!!!");
			String input = br.readLine();// this is where the system stop and wait for an user's input
			System.out.println("Thee old way of reading input: " + input);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Console c = System.console();
		if(c != null) {
			String input2 = c.readLine();
			//c.writer().println("The new way of reading input" + input2);
			
		}
	}
	
	public static void testGetParent() {
		//root
		File root = new File("C");
		System.out.print("test getParent() by passing root: ");
		System.out.println(root.getParent());
	}
	
	public static void question23() {
		final StringBuilder sb = new StringBuilder();
		try(InputStream is = new BufferedInputStream(new FileInputStream("C:\\Java\\TestQuestion23.txt"))){
			System.out.println("is mark() supported for BufferedInputStream? " + is.markSupported());
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
