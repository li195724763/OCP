package Chapter9;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.*;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.UserPrincipal;
import java.util.List;
import java.util.stream.Stream;
import static java.nio.file.StandardCopyOption.*;//COPY_ATTRIBUTE, REPLACE_EXISTING
import static java.nio.file.LinkOption.*;//NOFOLLOW_LINKS
import static java.nio.file.FileVisitOption.*;//FOLLOW_LINKS

import javax.naming.directory.BasicAttributes;

public class Chapter9 {

	public static void main(String[] args) {
		//Path
		testPathAndPaths();
		testPathMethods();
		testGetParentRootName();
		subPath();
		testToAbsolutePath();
		testRelativize();
		testResolveNormalize();
		testToRealPath();
		//Files
		testIsSameFilesCreateDirectory();
		testCopy();
		testMove();
		testNewBufferedReaderWriter();
		testIsReadableExecutableSizeLastModifiedTimeOwner();
		testAttribute();
		testFunctional();
	}
	
	public static void testPathAndPaths() {
		Path p1 = Paths.get("C:", "Java", "Chapter9", "TestPath.txt");
		Path p3 = FileSystems.getDefault().getPath("C:", "Java", "Chapter9", "TestPath.txt");
		//Path relative = Paths.get("RelativePath.txt");
		Path relative = Paths.get("src/Chapter9/RelativePath.txt");
		
		try {
			Path uri1 = Paths.get(new URI("file:///C:/Java/Chapter9/TestPath.txt"));
			Path uri2 = Paths.get(new URI("file:///Chapter9"));
			System.out.println("is regular file, relative: " + Files.isRegularFile(relative));
			System.out.println("toAbsolute " + relative.toAbsolutePath());
			System.out.println("current working directory " + Paths.get(".").toRealPath());
			Files.lines(relative).forEach(System.out::println);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void testPathMethods() {
		Path p1 = Paths.get("C:","Java", "Chapter9", "test");
		//Path p1 = Paths.get("C:","Java");
		//Path p1 = Paths.get("C:");
		//Path p1 = Paths.get("C:","Java", "Chapter9", "TestPath1.txt");
		System.out.println("The p1 is " + p1.toString());
		for(int i=0;i<p1.getNameCount();i++) {
			System.out.println("This is the " + i + "th element. It is a " + p1.getName(i).toString());
		}
		
	}
	
	public static void testGetParentRootName() {
		Path absolute = Paths.get("C:","Java", "Chapter9", "TestPath1.txttt");//works for both file and directory, no exception
		//Path absolute = Paths.get("C:","Java", "Chapter9");
		System.out.println("get file for absolute path: " + absolute.getFileName());
		testParent(absolute);
		
		System.out.println();
		Path relative = Paths.get("src/Chapter9/RelativePath.txt");
		File file = new File("src/Chapter9/RelativePath.txt");
		System.out.println("is file exist? " + file.exists());
		System.out.println("get file for relative path: " + relative.getFileName() + ". The relative path is " + relative);
		System.out.println("get parsent of relative path: " + relative + ", parent is: " + relative.getParent());
		System.out.println("get parsent of relative path: " + Paths.get("scr") + ", parent is: " + Paths.get("scr").getParent());
		testParent(relative);
	}
	
	public static void testParent(Path p) {
		while((p = p.getParent())!=null) {
			//p = p.getParent();
			System.out.println("The parent path is: " + p);
		}
	}
	
	public static void subPath() {
		Path absolute = Paths.get("C:","Java", "Chapter9", "TestPath1.txt");
		Path sub1 = absolute.subpath(0, 2);
		Path sub2 = absolute.subpath(0, 2);
		System.out.println("Path is: " + absolute);
		System.out.println("sub path 0,2 is " + sub1);
		System.out.println("sub path 0,3 is " + sub2);
	}
	
	public static void testToAbsolutePath() {
		Path relative = Paths.get("testToAbsolute.txt");
		System.out.println("The relative path " + relative);
		System.out.println("The relative path after calling toAbsolutePath() " + relative.toAbsolutePath());//incorprate the current working directory with the passing in absolute path
	}
	
	public static void testRelativize() {
		Path absolute1 = Paths.get("C:","Java", "Chapter9", "TestPath1.txt");
		Path absolute2 = Paths.get("C:","Java", "Chapter9", "test", "test","test11");
		Path root = Paths.get("C:");
		Path relative1 = Paths.get("src", "Chapter9", "RelativePath.txt"); 
		Path relativize1t2 = absolute1.relativize(absolute2);// The beginning is always ..\  , the end is the last path element in the parameter.
		Path relativize2t1 = absolute2.relativize(absolute1);
		//Path mix = absolute1.relativize(relative1);//IllegalArgumentException: 'other' is different type of Path
		//Path mix = relative1.relativize(absolute1);//IllegalArgumentException: 'other' is different type of Path
		//Path withRoot = relative1.relativize(root);//IllegalArgumentException: 'other' is different type of Path, since only root
		System.out.println("Path1 is: " + absolute1);
		System.out.println("Path2 is: " + absolute2);
		System.out.println("relativize1t2 is " + relativize1t2);
		System.out.println("relativize2t1 is " + relativize2t1);
	}
	
	public static void testResolveNormalize() {
		Path absolute1 = Paths.get("C:","Java", "Chapter9", "TestPath1.txt");
		Path absolute2 = Paths.get("C:", "Java","fake1"); 
		Path fake = Paths.get("fake.txt");
		Path relative2 = absolute1.relativize(absolute2);//relative2 is "../../fake1"
		System.out.println("relative2 is : " + relative2);
		Path resolved1 = absolute2.resolve(fake);
		System.out.println("test resolve with absolte + relative: " + resolved1);
		Path resolved2 = absolute1.resolve(absolute2);
		System.out.println("test resolve with absolte + absolte: " + resolved2);
		System.out.println("test absolute2.resolve(relative2) " + absolute2.resolve(relative2));
		System.out.println("test absolute2.resolve(relative2).normalize() " + absolute2.resolve(relative2).normalize());// absolute2.resolve(relative2) is C:/Java/fake1/../../fake1
		System.out.println("test relative.resolve(absolte): " + fake.resolve(absolute1));// C:\Java\Chapter9\TestPath1.txt
	}
	
	public static void testToRealPath() {
		Path absolute1 = Paths.get("C:","Java", "Chapter9", "TestPath1.txt");
		Path relative1 = Paths.get("../");//this cancels out the OCP directory. 
		try {
			System.out.println("testToRealPath, the current working directory is : " + Paths.get(".").toRealPath());//current directory.
			System.out.println("testToRealPath, the real path is : " + absolute1.toRealPath());
			System.out.println("testToRealPath, for relative1, the real path is : " + relative1.toRealPath());
			//Paths.get("dsaf").toRealPath();//throw no such file exception
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("testToRealPath, this is not a real path");
		}
	}
	public static void testIsSameFilesCreateDirectory() {
		Path ab1 = Paths.get("C:","Java", "Chapter9", "TestPath1.txt");
		Path ab2 = Paths.get("C:","Java", "Chapter9", "TestPath1.txt");
		System.out.println("is file exitst? " + Files.exists(Paths.get("C:","Java", "Chapter9", "TestPath1.txt")));
		try {
			System.out.println("is same file? " + Files.isSameFile(ab1, ab2));
			//Files.createDirectory(Paths.get("C:","Java", "Chapter9", "TestPath2.txt"));
		} catch (IOException e) {
			System.out.println("Error");
			e.printStackTrace();
		}
	}
	
	public static void testCopy() {
		Path source = Paths.get("C:","Java", "Chapter9","TestPath.txt");//The source data type defines the destination data type.
		Path target = Paths.get("C:","Java", "Chapter9", "testCopy", "TestPath2.txt");
		System.out.println("before copy : " + target);
		try(InputStream inputStream1 = new BufferedInputStream(new FileInputStream("C:\\Java\\TestFile.txt"));
				OutputStream output = new BufferedOutputStream(new FileOutputStream("C:\\Java\\TestOutputByte.txt"))) {
			//Files.copy(source, target, NOFOLLOW_LINKS);// import static java.nio.file.LinkOption.*;		
			//Files.copy(source, target, COPY_ATTRIBUTES);
			Files.copy(Paths.get("C:/Java/Chapter9/testCopyDirectory1"), Paths.get("C:/Java/Chapter9/testCopyDirectory2"), REPLACE_EXISTING);
			Files.copy(source, target, REPLACE_EXISTING);// import static java.nio.file.StandardCopyOption.*;
			System.out.println("after copy : " + target);
			Files.copy(source, output);// source must exist and must a file
			//Files.copy(inputStream1, target);// target will be created if not exist, and also must be a file, and must not exist.
			Files.copy(inputStream1, Paths.get("C:/Java/Chapter9/testCopyDirectory3"), REPLACE_EXISTING);// will create a file not directory
			//Files.copy(inputStream1, Paths.get("C:/Java/Chapter911111/testCopyDirectory3"));// Throw NoSuchFileException, will not create parsent directory if not exist.
		} catch (IOException e) {
			System.out.println("Copy falied");
			e.printStackTrace();
		}
	}
	
	public static void testMove() {
		Path source = Paths.get("C:","Java", "Chapter9","TestMove.txt");
		Path target = Paths.get("C:","Java", "Chapter9", "tests", "Moved.txt");
		
		Path sourcedir = Paths.get("C:","Java", "Chapter9","testscr");
		Path targetdir = Paths.get("C:","aJava","Chapter9","tardir");
		
		try {
			//Files.move(source, target, ATOMIC_MOVE);//import static java.nio.file.StandardCopyOption.*;
			//Files.move(source, target);
			//Files.move(sourcedir, targetdir);
			//Files.move(Paths.get("C:/Java/Chapter9/testMove"), Paths.get("C:/Java/Chapter9/testMoveTargetParent/testMoveTarget"), NOFOLLOW_LINKS);//NO Exception, will cut all the files/sub-directories to the target
			throw new IOException();
		} catch (IOException e) {
			System.out.println("Move falied");
			e.printStackTrace();
		}
	}
	public static void testNewBufferedReaderWriter() {
		Path source = Paths.get("C:","Java", "Chapter9","TestReadAllLines.txt");
		String s;
		try (BufferedReader read = Files.newBufferedReader(source, Charset.defaultCharset())) {
			while((s = read.readLine()) != null) {
				//System.out.println(s);
			}
			List<String> ls = Files.readAllLines(source);
			System.out.println("test readAllLines");
			for(String s1 : ls) {
				System.out.println(s1);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void testIsReadableExecutableSizeLastModifiedTimeOwner() {
		Path file = Paths.get("C:","Java", "Chapter9","TestPath.txt");
		Path directory = Paths.get("C:","Java", "Chapter9");
		
		//System.out.println("is file readable? " + Files.isReadable(file));
		System.out.println("is file(not exist) readable? " + Files.isReadable(Paths.get("C:","Java", "Chapter9","TesasdftPath.txt")));
		System.out.println("is file executable? " + Files.isExecutable(file));
		System.out.println("is file(not exist) executable? " + Files.isReadable(Paths.get("C:","Java", "Chapter9","TesasdftPath.txt")));
		
		System.out.println("is directory readable? " + Files.isReadable(directory));
		System.out.println("is directory executable? " + Files.isExecutable(directory));
		
		try {
			System.out.println("file size? " + Files.size(file));
			System.out.println("directory size? " + Files.size(directory));
			System.out.println("last modified time, file? " + Files.getLastModifiedTime(file));
			System.out.println("last modified time, directory? " + Files.getLastModifiedTime(directory));
			System.out.println("test file owner: " + Files.getOwner(file).getName());
			System.out.println("test directory owner: " + Files.getOwner(directory).getName());
			UserPrincipal owner = FileSystems.getDefault().getUserPrincipalLookupService().lookupPrincipalByName(Files.getOwner(file).getName());// get FileSystem object from FileSystems
			UserPrincipal owner2 = file.getFileSystem().getUserPrincipalLookupService().lookupPrincipalByName("Weizheng");//get FileSystem object from path object.
			System.out.println("owner is " + owner);
			//Files.setOwner(file, owner); //throws AccessDeniedException
			//Files.setOwner(Paths.get("C:","Java", "Chapter9","TestPath.txt"), owner); //throws AccessDeniedException
			//System.out.println("test file owner: " + Files.getOwner(file).getName());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void testAttribute() {
		Path file = Paths.get("C:","Java", "Chapter9","TestPath.txt");
		Path directory = Paths.get("C:","Java", "Chapter9");
		
		try {
			BasicFileAttributes basic = Files.readAttributes(file, BasicFileAttributes.class);
			System.out.println("file last update time " + basic.lastModifiedTime());
			FileTime lastModifiledTime = FileTime.fromMillis(Files.getLastModifiedTime(file).toMillis() + 10_000);
			
			BasicFileAttributeView view = Files.getFileAttributeView(file, BasicFileAttributeView.class);
			//Files.getFileAttributeView(Paths.get("C:","Java", "Chapter9","TestPathsss.txt"), BasicFileAttributeView.class).setTimes(lastModifiledTime, null, null);;//setTime() throw IOException
			BasicFileAttributes readOnly = view.readAttributes();
			System.out.println("last modified time before update: " + readOnly.lastModifiedTime());
			
			view.setTimes(lastModifiledTime, null, null);
			System.out.println("last modified time after update: " + Files.getLastModifiedTime(file));// readOnly.lastModifiedTime());
			// The lastModified time will be the same(before and after executing setTimes() method) if reading the last modified time on the same BasicFileAttributes object.
			System.out.println("Integer max value is: " + Integer.MAX_VALUE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void testFunctional() {
		Path file = Paths.get("C:/Java");
		Path file2 = Paths.get("C:/Java", "Chapter9","question16.csv");
		file.equals(file2);
		try {
			final long modifiedTime = Files.getLastModifiedTime(file).toMillis() - 10000;// throw IOException
			Files.walk(file, FOLLOW_LINKS);
			Files.walk(file).filter(s -> s.toString().contains("File")).forEach(System.out::println);
			Files.find(file, 3, (path , attribute) -> path.toString().contains("Test") && attribute.lastModifiedTime().toMillis() > modifiedTime).forEach(System.out::println);
			Files.list(file).forEach(System.out::println);
			Files.lines(file2).flatMap(p -> Stream.of(p.split(","))).map(s -> s.toUpperCase()).forEach(System.out::println);//Q16
			Files.lines(file2).map(s -> s.toUpperCase()).forEach(System.out::println);//Q16
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
