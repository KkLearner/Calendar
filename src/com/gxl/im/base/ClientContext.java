package com.gxl.im.base;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.gxl.im.utils.IMApiUtils;
import com.gxl.im.wrapper.ResponseWrapper;

public class ClientContext {

	/*
	 * Configuration Source Type
	 */
	public static final String INIT_FROM_PROPERTIES = "FILE";

	public static final String INIT_FROM_CLASS = "CLASS";

	/*
	 * Implementation List
	 */
	public static final String JERSEY_API = "jersey";

	public static final String HTTPCLIENT_API = "httpclient";

	/*
	 * Properties
	 */
	private static final String API_PROTOCAL_KEY = "API_PROTOCAL";

	private static final String API_HOST_KEY = "API_HOST";

	private static final String API_ORG_KEY = "API_ORG";

	private static final String API_APP_KEY = "API_APP";

	private static final String APP_CLIENT_ID_KEY = "APP_CLIENT_ID";

	private static final String APP_CLIENT_SECRET_KEY = "APP_CLIENT_SECRET";

	private static final String APP_IMP_LIB_KEY = "APP_IMP_LIB";

	private static final String CACERT_FILE_PATH_KEY = "CACERT_FILE_PATH";

	private static final String CACERT_FILE_PASSWORD_KEY = "CACERT_FILE_PASSWORD";

	private static ClientContext context;

	private Boolean initialized = Boolean.FALSE;

	private String protocal;

	private String host;

	private String org;

	private String app;

	private String clientId;

	private String clientSecret;

	private String impLib;

	private String cacertFilePath;

	private String cacertFilePassword;

	private TokenGenerator token; // Wrap the token generator

	private ClientContext() {};

	public static ClientContext getInstance() {
		if( null == context ) {
			context = new ClientContext();
		}

		return context;
	}

	public ClientContext init(String type) {
		if( initialized ) {
			return context;
		}

		if(type==null||type.equals("")) {
			type = INIT_FROM_PROPERTIES;
		}

		if( INIT_FROM_PROPERTIES.equalsIgnoreCase(type) ) {
			initFromPropertiesFile();
			initialized = Boolean.TRUE;
		}
		else {
			
			return context; // Context not initialized
		}

		// Initialize the token generator by default
		if( context.initialized ) {
			token = new TokenGenerator(context);
		}

		return context;
	}

	public String getSeriveURL() {
		if (null == context || !context.isInitialized()) {
			throw new RuntimeException("INVAILID_CONTEXT_MSG");
		}

		String serviceURL = context.getProtocal() + "://" + context.getHost() + "/" + context.getOrg() + "/" + context.getApp();

		return serviceURL;
	}

	public String getAuthToken() {
		if( null == token ) {
			throw new RuntimeException("INVAILID_TOKEN_MSG");
		}

		return token.request(Boolean.FALSE);
	}

	private void initFromPropertiesFile() {
		Properties p = new Properties();

		try {
			InputStream inputStream = ClientContext.class.getClassLoader().getResourceAsStream("resources/im.properties");
			p.load(inputStream);
		} catch (IOException e) {
			return; // Context not initialized
		}

		String protocal = p.getProperty(API_PROTOCAL_KEY);
		String host = p.getProperty(API_HOST_KEY);
		String org = p.getProperty(API_ORG_KEY);
		String app = p.getProperty(API_APP_KEY);
		String clientId = p.getProperty(APP_CLIENT_ID_KEY);
		String clientSecret = p.getProperty(APP_CLIENT_SECRET_KEY);
		String impLib = p.getProperty(APP_IMP_LIB_KEY);
		String cacertFilePath = p.getProperty(CACERT_FILE_PATH_KEY);
		String cacertFilePassword = p.getProperty(CACERT_FILE_PASSWORD_KEY);

		if(protocal.equals("")||host.equals("") ||org.equals("") ||app.equals("") || clientId.equals("") ||clientSecret.equals("") ||impLib.equals("") ) {
			return; // Context not initialized
		}

		context.protocal = protocal;
		context.host = host;
		context.org = org;
		context.app = app;
		context.clientId = clientId;
		context.clientSecret = clientSecret;
		context.impLib = impLib;
		context.cacertFilePath = cacertFilePath;
		context.cacertFilePassword = cacertFilePassword;
	}

	public String getProtocal() {
		return protocal;
	}

	public String getHost() {
		return host;
	}

	public String getOrg() {
		return org;
	}

	public String getApp() {
		return app;
	}

	public String getClientId() {
		return clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public Boolean isInitialized() {
		return initialized;
	}

	public String getImpLib() {
		return impLib;
	}

	public String getCacertFilePath() { return cacertFilePath; }

	public String getCacertFilePassword() { return cacertFilePassword; }

	public static void main(String[] args) {
		ClientContext context=ClientContext.getInstance().init(null);
		IMApiUtils utils=IMApiUtils.getInstance();
		
	}

}