package com.test;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.test.jaxws.ServiceAT;
import com.test.jaxws.ServiceATService;

@Stateless
@Remote(ServiceAT.class)
@WebService(serviceName = "ServiceATService", portName = "ServiceAT", name = "ServiceAT", targetNamespace = "http://www.test.com")
@SOAPBinding(style = SOAPBinding.Style.RPC)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ServiceATImpl implements ServiceAT {

	private static final int ENTITY_ID = 1;

	@PersistenceContext
	protected EntityManager em;

	private ServiceAT remoteClient;

	@WebMethod
	public void addUser(User user) {

		System.out.println("[SERVICE] service invoked to add user by '" + user + "'");

		System.out.println("[SERVICE] Using the JPA Entity Manager to add user within a JTA transaction");
		User entity = lookupUser(user.getId());
		entity.setName(user.getName());
		em.merge(entity);

		System.out.println(
				"[SERVICE] Calling addUser on the WS remoteClient stub. The registered interceptor will bridge rom JTA to WS-AT");
		getRemoteClient().addUser(user);
	}

	@WebMethod
	public void addUserWithException(User user) {

		System.out.println("[SERVICE] service invoked to addUserWithException by '" + user + "'");

		System.out.println("[SERVICE] Using the JPA Entity Manager to add user within a JTA transaction");
		User entity = lookupUser(user.getId());
		entity.setName(user.getName());
		em.merge(entity);

		System.out.println(
				"[SERVICE] Calling addUserWithException on the WS remoteClient stub. The registered interceptor will bridge rom JTA to WS-AT");
		getRemoteClient().addUserWithException(user);

	}

	@WebMethod
	public User getUser(String id) {
		System.out.println("[SERVICE] getUser() invoked");
		User user = lookupUser(id);
		System.out.println("[SERVICE] User is '" + user + "'");
		return user;
	}

	private User lookupUser(String id) {
		User user = em.find(User.class, id);
		if (user == null) {
			user = new User(id, "");
			em.persist(user);
		}
		return user;
	}

	private ServiceAT getRemoteClient() {

		if (remoteClient == null) {
			try {
				remoteClient = RemoteClient.newInstance();
			} catch (Exception e) {
				throw new RuntimeException("Error creating RemoteClient instance", e);
			}
		}
		return remoteClient;
	}

}
