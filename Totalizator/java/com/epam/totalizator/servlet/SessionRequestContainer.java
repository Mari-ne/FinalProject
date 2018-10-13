package com.epam.totalizator.servlet;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.naming.directory.InvalidAttributesException;
import javax.servlet.http.HttpServletRequest;

/**
 * Class-container. Contains Http request's parameter, attributes
 * and attributes of opened session.
 *
 */
public class SessionRequestContainer {

	private HashMap<String, String[]> parametrs;
	private HashMap<String, Object> attributes;
	private HashMap<String, Object> sessionAttributes;
	private boolean isValidSession = true;
	
	public SessionRequestContainer(HttpServletRequest req) {
		parametrs = new HashMap<>(req.getParameterMap());
		attributes = new HashMap<>();
		Enumeration<String> atribNames = req.getAttributeNames();
		while(atribNames.hasMoreElements()) {
			String name = atribNames.nextElement();
			attributes.put(name, req.getAttribute(name));
		}
		sessionAttributes = new HashMap<>();
		Enumeration<String> sesAtribNames = req.getSession().getAttributeNames();
		while(sesAtribNames.hasMoreElements()) {
			String name = sesAtribNames.nextElement();
			sessionAttributes.put(name, req.getSession().getAttribute(name));
		}
	}

	public HashMap<String, String[]> getParametrs() {
		return parametrs;
	}

	public String[] getParametr(String name) throws InvalidAttributesException {
		if(!parametrs.containsKey(name) ||  parametrs.get(name).length == 0) {
			throw new InvalidAttributesException("There is no parametr '" + name + "' in request");
		}
		return parametrs.get(name);
	}
	
	public void setParametrs(HashMap<String, String[]> parametrs) {
		this.parametrs = parametrs;
	}

	public void addParametrs(HashMap<String, String[]> parametrs) {
		this.parametrs.putAll(parametrs);
	}
	
	public void addParametr(String name, String[] value) {
		parametrs.put(name, value);
	}
	
	public HashMap<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(HashMap<String, Object> attributes) {
		this.attributes = attributes;
	}
	
	public Object getAttribute(String name) throws InvalidAttributesException {
		if(!attributes.containsKey(name)) {
			throw new InvalidAttributesException("There is no attribute '" + name + "' in request");
		}
		return attributes.get(name);
	}
	
	public void addAttribute(String name, Object value) {
		attributes.put(name, value);
	}
	
	public void addAttributes(HashMap<String, Object> attributes) {
		this.attributes.putAll(attributes);
	}
	
	public void createRequest(HttpServletRequest req) {
		for(Map.Entry<String, Object> elem : attributes.entrySet()) {
			req.setAttribute(elem.getKey(), elem.getValue());
		}
		for(Map.Entry<String, Object> elem : sessionAttributes.entrySet()) {
			req.getSession().setAttribute(elem.getKey(), elem.getValue());
		}
		if(!isValidSession) {
			req.getSession().invalidate();
		}
	}

	public HashMap<String, Object> getSessionAttributes() {
		return sessionAttributes;
	}

	public void setSessionAttributes(HashMap<String, Object> sessionAttributes) {
		this.sessionAttributes = sessionAttributes;
	}
	
	public void setSessionAttribute(String name, Object value) {
		sessionAttributes.put(name, value);
	}
	
	public Object getSessionAttribute(String name) throws InvalidAttributesException {
		if(!sessionAttributes.containsKey(name)) {
			throw new InvalidAttributesException("There is no session attribute '" + name + "' in request");
		}
		return sessionAttributes.get(name);
	}
	
	public void invalidateSession() {
		isValidSession = false;
	}
}
