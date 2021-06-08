package Chapter5;

import java.util.ListResourceBundle;

public class Zoo_en extends ListResourceBundle{
	@Override protected Object[][] getContents() {
		//return  new Chapter7Util.OBJECT[][]{{"hello", new StringBuilder("hello from java class")}};!!!!!EXCEPTION with getProperty()
		
		return  new Object[][]{{"hello", "hello from Zoo_en.java"}};
		
		
	}
}
