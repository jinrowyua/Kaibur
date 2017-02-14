package com.jinrow.kaibur;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;

import com.jinrow.kaibur.configuration.parser.ApplicationConfigurationProcessor;

/**
 * Mojo handling initial execution of dynamic configuration class generation.
 * @author JYAU
 *
 */
@Mojo( name = "generate-configuration", defaultPhase = LifecyclePhase.COMPILE,  requiresDependencyResolution = ResolutionScope.COMPILE_PLUS_RUNTIME) 
public class KaiburMojo extends AbstractMojo{

	/**
	 * @parameter package name where java class will be generated
	 */
	@Parameter( defaultValue = "${kaibur.package}", property = "packageName", required = true )
	private String packageName;
	
	/**
	 * 
	 * @parameter path to configuration file for generation
	 */
	@Parameter( defaultValue = "${kaibur.path}", property = "configurationFilePath", required = true )
	private String configurationFilePath;
	
	
	public void execute() throws MojoExecutionException, MojoFailureException{
		ApplicationConfigurationProcessor processor;
		
		try {
			processor = new ApplicationConfigurationProcessor(configurationFilePath,packageName);
			processor.generateConfigurationClass();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
