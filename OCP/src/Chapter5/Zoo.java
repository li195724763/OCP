package Chapter5;
import java.util.ListResourceBundle;

public class Zoo extends ListResourceBundle{

	@Override protected Object[][] getContents() {
		
		return  new Object[][]{
			{"hello", "hello, from Zoo.java"},
			{"open" ,"The Zoo is Open,from Zoo.java"},
			{"another","another, from Zoo.java"},
			{"anotheragain", "anothersyntc, from Zoo.java"}
		};
		
		
	}

}
