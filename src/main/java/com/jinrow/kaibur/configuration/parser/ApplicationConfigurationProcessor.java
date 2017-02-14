package com.jinrow.kaibur.configuration.parser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.configuration2.builder.ReloadingFileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.tree.xpath.XPathExpressionEngine;

/**
 * A help class used to kick off parsing and generation of configuration class file.
 * @author JYAU
 *
 */
public class ApplicationConfigurationProcessor {

	private File configurationFile = null;
	private String packageDestination = null;
	
	private static final String CONFIGURATION_CLASS_NAME = "ApplicationConfiguration";
	
	/**
	 * 
	 * @param filePathLocation - Path to the configuration file used for parsing. The format used is <code>XML</code> and should conform to the structure.
	 * @param packageDestination - Destination package where configuration java class will be generated.
	 * @throws FileNotFoundException
	 * @throws Exception
	 */
	public ApplicationConfigurationProcessor(String filePathLocation,String packageDestination) throws FileNotFoundException,Exception {
		try
		{
			this.configurationFile = new File(filePathLocation);
			if(!this.configurationFile.exists())
			{
				throw new FileNotFoundException();
			}
			else if(this.configurationFile.length()<=0)
			{
				throw new Exception("File is empty");
			}
			this.packageDestination = packageDestination;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

	}
	
	public void generateConfigurationClass()
	{
		try
		{
			Parameters params = new Parameters();

			ReloadingFileBasedConfigurationBuilder<XMLConfiguration> builder =
			     new ReloadingFileBasedConfigurationBuilder<XMLConfiguration>(XMLConfiguration.class)
			     .configure(params.fileBased()
			         .setFile(configurationFile));
			
			XMLConfiguration config = builder.getConfiguration();
			config.setExpressionEngine(new XPathExpressionEngine());
			
			ConfigurationParser parser = new ConfigurationParser(builder.getConfiguration().getNodeModel().getRootNode());
			writeToJavaFile(parser.parseConfiguration(packageDestination,configurationFile.getAbsolutePath()));		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void writeToJavaFile(String classDefinition)
	{
		Writer fileWriter = null;
		try
		{			
			fileWriter = new FileWriter("src/"+(packageDestination.replace(".", "/")+"/"+CONFIGURATION_CLASS_NAME+".java"));
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter,16384);
			
			bufferedWriter.write(classDefinition);
			bufferedWriter.flush();
			fileWriter.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try {
				fileWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		

	}
}
