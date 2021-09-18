package com.tecxis.resume.domain;

public class RegexConstants {

	/**
	 * @see <a href="https://regex101.com/">Visit Java regex editor Regular Expression 101</a>
	 * */
	private RegexConstants() {}
	
	/**Matches the start of a string without consuming any characters.*/
	final private static String START_OF_STRING = "^";

	/**Matches the end of a string without consuming any characters.*/ 
	final private static String END_OF_STRING = "$";
	

	final private static String DEFAULT_FULLY_QUALIFIED_ID_NAME = "com\\.tecxis\\.resume\\.domain\\.id\\.\\w+(?:Id\\@\\d+)";
	
	/**
	 * Matches default composite primary keys separated by comma and space ","
	 ^\[(\w+(?:Id=null))(,\s\w+(?:Id=null))*]$  matches exactly "[cityId=null, projectId=null, projectId=null, projectId=null, projectId=null]" or:
	"[projectId=null]" or "[cityId=null]" but does NOT match:
	"[cityId=null],"
	*/
	final private static String DEFAULT_COMPOSITE_ID_CONTENT = "\\[(\\w+(?:Id=null|Id=0))(,\\s\\w+(?:Id=null|Id=0))*]";	
	
	final public static String DEFAULT_ID = START_OF_STRING + DEFAULT_FULLY_QUALIFIED_ID_NAME + DEFAULT_COMPOSITE_ID_CONTENT + END_OF_STRING;

}
