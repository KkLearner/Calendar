package com.gxl.im.base;


public class RestAPIFactory {
	
	public static final String TOKEN_CLASS = "EasemobAuthToken";
	
	public static final String USER_CLASS = "EasemobIMUsers";
	
	public static final String FILE_CLASS = "EasemobFile";
	
	public static final String MESSAGE_CLASS = "EasemobChatMessage";
	
	public static final String SEND_MESSAGE_CLASS = "EasemobSendMessage";
	
	public static final String CHATGROUP_CLASS = "EasemobChatGroup";
	
	public static final String CHATROOM_CLASS = "EasemobChatRoom";
	
	private static final String BASE_PACKAGE = "com.easemob.server.example.api.impl";
	
	private static final String METHOD_SET_CONTEXT = "setContext";
	
	private static final String METHOD_SET_INVOKER = "setInvoker";
		
	private static RestAPIFactory factory;
	
	private ClientContext context;
	
	private RestAPIInvoker httpclient = new HttpClientRestAPIInvoker();

	private RestAPIFactory(ClientContext context) {
		this.context = context;
	}
	
	public static RestAPIFactory getInstance(ClientContext context) {
		if( null == factory ) {
			if( null == context || !context.isInitialized() ) {
				context = ClientContext.getInstance().init(ClientContext.INIT_FROM_PROPERTIES);
				
				if( !context.isInitialized() ) {
					throw new RuntimeException("INVAILID_CONTEXT_MSG");
				}
			}
			
			factory = new RestAPIFactory(context);
		}
		
		return factory;
	}
	
	private Object getClassInstance(String className) {
		Class<?> targetClass = null;
		Object newObj = null;
		
		try {
			targetClass = Class.forName(BASE_PACKAGE + "." + className);
			newObj = targetClass.newInstance();
		} catch (Exception e) {
			throw new RuntimeException();
		}
		
		// Inject the context and invoker, they are defined in EasemobRestAPI
		try {
			targetClass.getMethod(METHOD_SET_CONTEXT, ClientContext.class).invoke(newObj, this.context);
		} catch (Exception e) {
			throw new RuntimeException();
		}
		
		RestAPIInvoker invoker = null;
		if( ClientContext.HTTPCLIENT_API.equals(context.getImpLib()) ) {
			invoker = httpclient;
		}
		try {
			targetClass.getMethod(METHOD_SET_INVOKER, RestAPIInvoker.class).invoke(newObj, invoker);
		} catch (Exception e) {
			throw new RuntimeException();
		}
		
		return newObj;
	}

	public ClientContext getContext() {
		return context;
	}
	
}
