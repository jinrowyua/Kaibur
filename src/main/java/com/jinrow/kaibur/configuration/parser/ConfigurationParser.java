package com.jinrow.kaibur.configuration.parser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

import org.apache.commons.configuration2.tree.ImmutableNode;

public class ConfigurationParser {

	private ImmutableNode node;
	
	public ConfigurationParser(ImmutableNode node) {
		this.node = node;
	}
	
	public String parseConfiguration(String packageName, String configurationFilePath)
	{
		StringBuilder classDefinition = new StringBuilder();
		String classHeader = generateClassBody(packageName, configurationFilePath);
		classDefinition.append(classHeader);
		for(ImmutableNode configurationNode : node.getChildren())
		{
			classDefinition.append(convertToMethod(configurationNode));
		}
		
		classDefinition.append("\n}");
		return classDefinition.toString();
	}
	
	private String generateClassBody(String packageName, String configurationFilePath)
	{
		StringBuilder classHeader = new StringBuilder();
		
		Scanner fileReader = null;
		try
		{
			fileReader = new Scanner(new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/"+ParserConstants.CLASS_HEADER_FILE_PATH))));
			
			while(fileReader.hasNextLine())
			{
				String line = fileReader.nextLine();
				if(line.contains(ParserConstants.PACKAGE_NAME_PLACEHOLDER))
				{
					line = line.replace(ParserConstants.PACKAGE_NAME_PLACEHOLDER, packageName);
				}
				else if(line.contains(ParserConstants.CONFIGURATION_FILE_PATH_PLACEHOLDER))
				{
					line = line.replace(ParserConstants.CONFIGURATION_FILE_PATH_PLACEHOLDER, configurationFilePath.replace("\\","/"));
				}

				classHeader.append(line);
				classHeader.append("\n");
			}
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				fileReader.close();				
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
		return classHeader.toString();

	}
	
	
	/**
	 * 
	 * @param methodNode
	 * @return
	 */
	private String convertToMethod(ImmutableNode methodNode)
	{
		StringBuilder methodDefinition = new StringBuilder();
		methodDefinition.append("\n\t/**\n");
		methodDefinition.append("\t*\n");
		methodDefinition.append("\t*");
		methodDefinition.append(methodNode.getAttributes().get("description"));
		methodDefinition.append("\n\t*/\n\t");
		methodDefinition.append(ParserConstants.METHOD_MODIFIERS);
		methodDefinition.append(" ");
		methodDefinition.append(ParserConstants.METHOD_NAME_PREFIX);
		methodDefinition.append(methodNode.getAttributes().get("name"));
		methodDefinition.append(ParserConstants.NO_PARAMETER);
		methodDefinition.append("\n\t{\n\t\t");
		methodDefinition.append(createMethodBody(methodNode));
		methodDefinition.append("\n\t}\n");
		
		return methodDefinition.toString();
	}
	
	private String createMethodBody(ImmutableNode methodNode)
	{	
		return ParserConstants.STANDARD_XPATH_TEMPLATE.replace("'propertyName'", methodNode.getAttributes().get("name").toString());
	}
	
}
