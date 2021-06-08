package Chapter5;
import java.util.ListResourceBundle;

public class Zoo_en_US extends ListResourceBundle{

	@Override protected Object[][] getContents() {
		
		return  new Object[][]{
			{"hello", "hello, from Zoo_en_US.java"},
			{"open" ,"The Zoo is Open,from Zoo_en_US.java"},
			{"another","another, from Zoo_en_US.java"},
			{"anotheragain", "anothersyntc, from Zoo_en_US.java"}
		};
		
		
	}

}
