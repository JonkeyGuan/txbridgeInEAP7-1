package com.test.jaxws;

import javax.ejb.Remote;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import com.test.User;

@WebService(name = "LocalServiceAT", targetNamespace = "http://www.test.com")
@SOAPBinding(style = SOAPBinding.Style.RPC)
@Remote
public interface ServiceAT {

	@WebMethod
	public void addUser(User user);

	@WebMethod
	public void addUserWithException(User user);
	
	@WebMethod
	public User getUser(String id);

}
