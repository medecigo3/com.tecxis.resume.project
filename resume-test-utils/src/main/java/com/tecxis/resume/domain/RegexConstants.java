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
	
	final private static String DEFAULT_COMPOSITE_ID = "Id=null";
	
	final private static String DEFAULT_SIMPLE_ID = "Id=0"; 
	
	final private static String DEFAULT_NAME = "name=null";	

	final private static String DEFAULT_FULLY_QUALIFIED_ID_NAME = "com\\.tecxis\\.resume\\.domain\\.id\\.\\w+(?:Id\\@\\d+)";
	
	final private static String DEFAULT_FULLY_QUALIFIED_ENTITY_NAME = "com\\.tecxis\\.resume\\.domain\\.\\w+";
	
	/**
	 * Matches default composite primary keys separated by comma and space ","
	 ^\[(\w+(?:Id=null))(,\s\w+(?:Id=null))*]$  matches exactly "[cityId=null, projectId=null, projectId=null, projectId=null, projectId=null]" or:
	"[projectId=null]" or "[cityId=null]" but does NOT match:
	"[cityId=null],"
	*/
	final private static String DEFAULT_COMPOSITE_ID_CONTENT = "\\[(\\w+(?:"+ DEFAULT_COMPOSITE_ID + "|" + DEFAULT_SIMPLE_ID + "))(,\\s\\w+(?:" + DEFAULT_COMPOSITE_ID + "|" + DEFAULT_SIMPLE_ID + "))*]";	
	
	final private static String DEFAULT_NESTED_COMPOSITE_ID_CONTENT = "\\[(\\w+(?:" + DEFAULT_COMPOSITE_ID + "|" + DEFAULT_SIMPLE_ID +")|" + DEFAULT_FULLY_QUALIFIED_ID_NAME + DEFAULT_COMPOSITE_ID_CONTENT +")(,\\s\\w+(?:" + DEFAULT_COMPOSITE_ID + "|" + DEFAULT_SIMPLE_ID + "))*]";
	
	final private static String DEFAULT_ID =  DEFAULT_FULLY_QUALIFIED_ID_NAME + DEFAULT_COMPOSITE_ID_CONTENT;
	
	final public static String DEFAULT_ID_REGEX = START_OF_STRING + DEFAULT_ID + END_OF_STRING;
	
	final public static String DEFAULT_NESTED_ID_REGEX = START_OF_STRING + DEFAULT_FULLY_QUALIFIED_ID_NAME + DEFAULT_NESTED_COMPOSITE_ID_CONTENT + END_OF_STRING;
	
	final public static String DEFAULT_ENTITY_SIMPLE_ID_REGEX = START_OF_STRING + DEFAULT_FULLY_QUALIFIED_ENTITY_NAME + "\\@\\d+\\[\\w+(?:" + DEFAULT_SIMPLE_ID + ")" + "]" + END_OF_STRING;
	
	final public static String DEFAULT_COMPOSITE_ENTITY_SIMPLE_ID_REGEX = START_OF_STRING + DEFAULT_FULLY_QUALIFIED_ENTITY_NAME + "\\[" + DEFAULT_NAME  + "\\[" + DEFAULT_ID + "]]" + END_OF_STRING;

}
