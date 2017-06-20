package springboot.camel.router.ftpserver;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.properties.PropertiesComponent;
import org.springframework.stereotype.Component;

@Component
public class FtpServerExampleSpringboot extends RouteBuilder {

	@Override
	public void configure() throws Exception {

		/*
		 * METHOD1: set the properties directly
		 */
		// from("ftp://localhost:21/?username=xlight&password=xlight").to("file:data/outbox2");

		/*
		 * METHOD2: get the properties from the file With this method, it still
		 * reads the value from "application.properties"
		 */
		/*
		 * PropertiesComponent pc = getContext().getComponent("properties",
		 * PropertiesComponent.class);
		 * pc.setLocation("classpath:ftp.properties");
		 * 
		 * // lets shutdown faster in case of in-flight messages stack up
		 * //getContext().getShutdownStrategy().setTimeout(10);
		 * 
		 * from("{{ftp.client}}").to("file:target/download").
		 * log("Downloaded file ${file:name} complete.");
		 */

		/*
		 * METHOD2: get the properties from the file With this method, it reads
		 * the value from "ftp.properties"
		 */
		PropertiesComponent pc = new PropertiesComponent();
		pc.setLocation("classpath:ftp.properties");
		getContext().addComponent("properties2", pc);
		
		
		from("{{ftp.client}}").to("file:target/download").log("Downloaded file ${file:name} complete.");
		// use system out so it stand out
		System.out.println("*********************************************************************************");
		System.out.println("Camel will route files from the FTP server: "
				+ getContext().resolvePropertyPlaceholders("{{ftp.server}}") + " to the target/download directory.");
		System.out.println(
				"You can configure the location of the ftp server in the src/main/resources/ftp.properties file.");
		System.out.println("Use ctrl + c to stop this application.");
		System.out.println("*********************************************************************************");
	}

}
