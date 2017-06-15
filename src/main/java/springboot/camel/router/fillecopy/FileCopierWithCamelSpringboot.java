package springboot.camel.router.fillecopy;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class FileCopierWithCamelSpringboot extends RouteBuilder {

	@Override
	public void configure() {
		from("file:data/inbox?noop=true").to("file:data/outbox");
	}
}
