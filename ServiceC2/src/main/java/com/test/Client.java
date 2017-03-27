package com.test;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import com.test.jaxws.ServiceAT;

public class Client {

	public static ServiceAT newInstance() throws Exception {
		URL wsdlLocation = new URL("http://localhost:8380/ServiceC2/ServiceATService/ServiceAT?wsdl");
		QName serviceName = new QName("http://www.test.com", "ServiceATService");
		QName portName = new QName("http://www.test.com", "ServiceAT");

		Service service = Service.create(wsdlLocation, serviceName);
		ServiceAT client = service.getPort(portName, ServiceAT.class);

		return client;
	}
}
