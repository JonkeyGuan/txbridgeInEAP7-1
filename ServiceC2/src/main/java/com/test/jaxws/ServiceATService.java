package com.test.jaxws;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

@WebServiceClient(name = "ServiceATService", targetNamespace = "http://www.test.com")
public class ServiceATService extends Service {

	private final static URL SERVICE_WSDL_LOCATION;
	private final static Logger logger = Logger.getLogger(ServiceATService.class.getName());

	static {
		URL url = null;
		try {
			URL baseUrl;
			baseUrl = ServiceATService.class.getResource(".");
			url = new URL(baseUrl, "ServiceAT.wsdl");
		} catch (MalformedURLException e) {
			logger.warning("Failed to create URL for the wsdl Location: 'ServiceAT.wsdl', retrying as a local file");
			logger.warning(e.getMessage());
		}
		SERVICE_WSDL_LOCATION = url;
	}

	public ServiceATService(URL wsdlLocation, QName serviceName) {
		super(wsdlLocation, serviceName);
	}

	public ServiceATService() {
		super(SERVICE_WSDL_LOCATION, new QName("http://www.com.test", "ServiceATService"));
	}

	@WebEndpoint(name = "ServiceAT")
	public ServiceAT getLocalServiceAT() {
		return super.getPort(new QName("http://www.test.com", "ServiceAT"), ServiceAT.class);
	}

	@WebEndpoint(name = "LocalServiceAT")
	public ServiceAT getLocalServiceAT(WebServiceFeature... features) {
		return super.getPort(new QName("http://www.test.com", "ServiceAT"), ServiceAT.class, features);
	}

}
