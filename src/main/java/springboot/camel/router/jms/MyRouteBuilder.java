/**
### Introduction

This example shows how to work with the Camel-JMX component.

The example creates a simple MBean, registers a route to listen for
notification events on that bean and creates another route that calls
the MBean
 */

package springboot.camel.router.jms;

import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

/**
 * A simple example router demonstrating the camel-jmx component.
 */
@Component
public class MyRouteBuilder extends RouteBuilder {

	private SimpleBean bean;
	private MBeanServer server;

	public MyRouteBuilder() throws Exception {
		server = ManagementFactory.getPlatformMBeanServer();
		bean = new SimpleBean();
		// START SNIPPET: e2
		server.registerMBean(bean, new ObjectName("jmxExample", "name", "simpleBean"));
		// END SNIPPET: e2
	}

	@Override
	public void configure() {

		// registers a route to listen for notification events on that bean
		// START SNIPPET: e1
		from("jmx:platform?objectDomain=jmxExample&key.name=simpleBean").to("log:jmxEvent");
		// END SNIPPET: e1

		//creates another route that calls the MBean
		from("timer:foo?period=6000").bean(bean, "tick");
	}
}
