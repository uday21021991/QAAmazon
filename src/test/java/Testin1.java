package test.java;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


public class Testin1 {
	
private static Logger logger=LogManager.getLogger(Testin1.class);
	
	@BeforeTest
	public void checkTestSkip() throws Exception {
		logger.debug("scenario is started");
		
	}
	@Test
	public void main() {
		
		String a ="hello";
		String b="u";
		System.out.println(b.substring(0,1));
		
		for(int i=a.length();i>0;i--) {
			System.out.println("========"+i);
			int count=0;
			for(int j=a.length();j>0;j--) {
				if(a.substring(0,1).equals(a.substring(j-1,j))){
					count=count+1;
				}
			}
			System.out.println(a.substring(0,1)+" "+count);
			a=a.replaceAll(a.substring(0,1), "");
	
		}
	}
	

}
