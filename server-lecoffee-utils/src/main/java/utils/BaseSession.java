package utils;

import jakarta.el.ELContext;
import jakarta.el.ExpressionFactory;
import jakarta.faces.context.FacesContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import to.client.TOClient;

public class BaseSession {
	protected HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	protected HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
	
	protected HttpSession getSession() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		
		if(session == null) {
			session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		}
		
		return session;
	}
	
	protected TOClient getClient() {
		return (TOClient) getSession().getAttribute("client");
	}
	
	protected void finishSession() {
		this.getSession().setAttribute("client", null);
		this.getSession().invalidate();
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getSBean(String beanName) {
		ELContext elcontext = FacesContext.getCurrentInstance().getELContext();
		ExpressionFactory expressionFactory = FacesContext.getCurrentInstance().getApplication().getExpressionFactory();
		
		T bean = (T) expressionFactory.createValueExpression(elcontext, "#{" + beanName +"}", Object.class).getValue(elcontext);
		
		return bean;		
	}
	
	//Setters and Getters
	public HttpServletRequest getRequest() {
		return request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}
}
