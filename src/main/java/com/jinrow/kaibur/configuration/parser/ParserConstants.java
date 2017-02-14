package com.jinrow.kaibur.configuration.parser;

public final class ParserConstants {

	protected static final String METHOD_MODIFIERS = "public static String";
	protected static final String METHOD_NAME_PREFIX = "get";
	protected static final String NO_PARAMETER = "()";
	protected static final String STANDARD_XPATH_TEMPLATE = "return configuration.getString(\"//configuration[@name='propertyName']/value\");";
	protected static final String CLASS_HEADER_FILE_PATH = "ConfigurationClassTemplate.template";
	protected static final String PACKAGE_NAME_PLACEHOLDER = "packageName";
	protected static final String CONFIGURATION_FILE_PATH_PLACEHOLDER = "configurationFilePath";
}
