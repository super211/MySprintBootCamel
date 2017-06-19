package springboot.camel.router.ftpupload;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.properties.PropertiesComponent;
import org.springframework.stereotype.Component;

//需要把该class与FtpServerExampleSpringboot放到另一个包，且需要将addComponent中改为properties2
//不然，会报错：Cannot add component as its already previously added: properties
@Component
public class FtpClientExampleSpringboot extends RouteBuilder {

	@Override
	public void configure() throws Exception {

		/*
		 * METHOD2: get the properties from the file With this method, it reads
		 * the value from "ftp.properties"
		 */
		PropertiesComponent pc = new PropertiesComponent();
		pc.setLocation("classpath:ftp.properties");
		getContext().addComponent("properties2", pc);

		/*from("file:target/upload?moveFailed=../error").log("Uploading file ${file:name}").to("{{ftp.client}}")
				.log("Uploaded file ${file:name} complete.");*/
		
		/*from("file:target/upload").log("Uploading file ${file:name}").to("{{ftp.client}}")
		.log("Uploaded file ${file:name} complete.");*/
		
		from("file:target/upload?moveFailed=.").log("Uploading file ${file:name}").to("{{ftp.client}}")
		.log("Uploaded file ${file:name} complete.");

		 // use system out so it stand out
        System.out.println("*********************************************************************************");
        System.out.println("Camel will route files from target/upload directory to the FTP server: "
                + getContext().resolvePropertyPlaceholders("{{ftp.server}}"));
        System.out.println("You can configure the location of the ftp server in the src/main/resources/ftp.properties file.");
        System.out.println("If the file upload fails, then the file is moved to the target/error directory.");
        System.out.println("Use ctrl + c to stop this application.");
        System.out.println("*********************************************************************************");
	
	}

}
