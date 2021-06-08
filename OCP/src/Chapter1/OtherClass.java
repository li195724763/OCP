package Chapter1;

import Chapter1.Chapter1.MemberInnerClass;
import Chapter1.Chapter1.staticInner;

public class OtherClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Chapter1 object = new Chapter1();
		
		//MemberInnerClass memClass = new Chapter1.MemberInnerClass();DOES not compile, can't create a member inner class outside of the outter class
		
		staticInner staticInn = new Chapter1.staticInner();//You can create a static inner object type, but need to specificly import that static class, 
		//no need to import if simply use that static class to create another type object, e.g. Locale locale = new Locale.Builder.build();
	}

}
