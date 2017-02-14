package com.jinrow.kaibur.configuration.parser;

import java.io.File;

import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.configuration2.builder.ReloadingFileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.tree.xpath.XPathExpressionEngine;

/**
 * DO NOT MODIFY. AUTO-GENERATED CLASS.
 *
 */
public class ApplicationConfiguration {
	
	private File configurationFile = new File("C:/dev/config.xml");
	private ReloadingFileBasedConfigurationBuilder<XMLConfiguration> configurationBuilder;
	private XMLConfiguration configuration;
	
	public boolean initializeConfiguration()
	{
		try
		{
			Parameters params = new Parameters();
			configurationBuilder =
			     new ReloadingFileBasedConfigurationBuilder<XMLConfiguration>(XMLConfiguration.class)
			     .configure(params.fileBased()
			         .setFile(configurationFile));
			
			configuration = configurationBuilder.getConfiguration();
			configuration.setExpressionEngine(new XPathExpressionEngine());
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public boolean reloadConfigurationFile()
	{
		boolean isReloadSuccessful = configurationBuilder.getReloadingController().checkForReloading(null);
		
		if(isReloadSuccessful)
		{
			try
			{
				configuration = configurationBuilder.getConfiguration();
				configuration.setExpressionEngine(new XPathExpressionEngine());				
			}
			catch(Exception e)
			{
				e.printStackTrace();
				isReloadSuccessful = false;
			}
		}
		
		return isReloadSuccessful; 
	}
	
}
