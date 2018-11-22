package com.Reports;

	import java.text.SimpleDateFormat;
	import java.util.Date;
	import org.apache.log4j.Logger;
	import org.apache.log4j.xml.DOMConfigurator;

public class Log {
	static Logger log = Logger.getLogger(Log.class.getName());
	
	static {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy-hh-mm-ss");
		System.setProperty("current.date.time", dateFormat.format(new Date()));
		System.setProperty("logPath", "//Demo");
		DOMConfigurator.configure("log4j.xml");
		}
	
	 /*public void configLogger()
	    {
	        URL u = getClass().getClassLoader().getResource("//Demo//log4j.xml");
	        DOMConfigurator.configure(u);
	    }*/
	
//This is to print log for the Start of the test case		
		public static void startTestCase(String sTestCaseName){
				
			 	log.info("***********************************************************");
			 
				log.info("$$$$$$$$$$$$$$$$$$$ "+sTestCaseName+ " $$$$$$$$$$$$$$$$$$$$");
			 
				log.info("***********************************************************");
			 			 
		}
 
//This is to print log for the ending of the test case
		 
		 public static void endTestCase(){
		 
			log.info("XXXXXXXXXXXXXXXXXXXXXXX "+"-E---N---D-"+" XXXXXXXXXXXXXXXXXXXXXX");
			
			}
 
// Need to create these methods, so that they can be called  
 
		 public static void info(String message) {
			log.info(message);
		 
			}
 
		 public static void warn(String message) {
		    log.warn(message);
		 
			}
 
		 public static void error(String message) {
		    log.error(message);
		 
			}
 
		 public static void fatal(String message) {
		    log.fatal(message);
		 
			}
 
		 public static void debug(String message) {
			log.debug(message);
	 
		 	}
 
}
