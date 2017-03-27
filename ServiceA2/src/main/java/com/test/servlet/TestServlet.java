package com.test.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.test.Client;
import com.test.User;
import com.test.jaxws.ServiceAT;

public class TestServlet extends HttpServlet {

	private static final long serialVersionUID = -7003525307704686462L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {

			String action = request.getParameter("action");
			if (action == null) {
				action = "add";
			}

			if (action.equalsIgnoreCase("add")) {
				addUser();
			} else if (action.equalsIgnoreCase("exception")) {
				addUserWithException();
			} else {
				get();
			}

		} catch (

		Exception e) {
			e.printStackTrace();
		}
	}

	private void addUser() throws Exception {
		ServiceAT firstClient = Client.newInstance();
		firstClient.addUser(new User("user1", "name" + System.currentTimeMillis()));
	}

	private void addUserWithException() throws Exception {
		ServiceAT firstClient = Client.newInstance();
		firstClient.addUserWithException(new User("user1", "name" + System.currentTimeMillis()));
	}

	private void get() throws Exception {
		ServiceAT firstClient = Client.newInstance();
		firstClient.getUser("user1");

	}

	// Context initialContext = new InitialContext();
	// Object obj = initialContext.lookup("java:jboss/UserTransaction");
	// UserTransaction ut = (UserTransaction) obj;
	//
	// Service service = Service.create(new
	// URL("http://localhost:8280/ServiceB3/FirstServiceATService?wsdl"),
	// new QName("http://www.test.com", "FirstServiceATService"));
	// FirstServiceAT client = service.getPort(FirstServiceAT.class);
	// ut.begin();
	// client.incrementCounter(1);
	// ut.commit();
	// Context initialContext = new InitialContext();
	// Object obj = initialContext.lookup("java:jboss/UserTransaction");
	// UserTransaction ut = (UserTransaction) obj;
	// FirstServiceAT firstClient = FirstClient.newInstance();
	//
	// System.out.println("[CLIENT] Beginning the first JTA transaction
	// XXX");
	// ut.begin();
	// System.out.println(
	// "[CLIENT] Calling incrementCounter on the WS firstClient stub.
	// The registered interceptor will bridge rom JTA to WS-AT");
	// firstClient.incrementCounter(1);
	// System.out.println(
	// "[CLIENT] Update successful, about to commit the JTA transaction.
	// This will also cause the bridged WS-AT transaction to commit");
	// ut.commit();
}
