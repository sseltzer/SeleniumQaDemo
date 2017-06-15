package com.sseltzer.selenium.framework.environment.enums;


/**
 * The EnvironmentConfig enum provides a mapping of EnvironmentEnum to ServerEnum to reflect
 * all possible target server environments that currently exist for testing. Each of these
 * enumerations refers to a possible target address to perform the test. The server environmental
 * variables configured in TeamCity as well as the variables passed in through Eclipse determine
 * which environment to target. ENVIRONMENT determines EnvironmentEnum and SERVER determines ServerEnum.
 * Upon request, EnvironmentalHandler will grab the environmental variables and perform a search using
 * the respective .find methods. Once both of these have been found, the .find method of EnvironmentConfig
 * is called to retrieve an enumeration representing the target environment. The Website object then
 * provides a method to set the base URL for a given website where a switch may be performed over this enum
 * to set the individual URLs for the website.
 * 
 * @author Sean Seltzer
 *
 */
public enum EnvironmentConfig {
	NONE (EnvironmentEnum.NONE,  ServerEnum.NONE),
	LOCAL(EnvironmentEnum.LOCAL, ServerEnum.NONE),
	DEV  (EnvironmentEnum.DEV,   ServerEnum.NONE),
	DEV1 (EnvironmentEnum.DEV1,   ServerEnum.SERVER1),
	DEV2 (EnvironmentEnum.DEV2,   ServerEnum.SERVER2),
	TST  (EnvironmentEnum.TST,   ServerEnum.NONE),
	TST1 (EnvironmentEnum.TST,   ServerEnum.SERVER1),
	TST2 (EnvironmentEnum.TST,   ServerEnum.SERVER2),
	PRD  (EnvironmentEnum.PRD,   ServerEnum.NONE),
	PRD1 (EnvironmentEnum.PRD1,   ServerEnum.SERVER1),
	PRD2 (EnvironmentEnum.PRD2,   ServerEnum.SERVER2),
	PRD3 (EnvironmentEnum.PRD3,   ServerEnum.SERVER3),
	PRD4(EnvironmentEnum.PRD4, ServerEnum.SERVER4),
	PRDG1 (EnvironmentEnum.PRDG1,   ServerEnum.SERVER1),
	PRDG2 (EnvironmentEnum.PRDG2,   ServerEnum.SERVER2),
	PRDG3 (EnvironmentEnum.PRDG3,   ServerEnum.SERVER3),
	PRDG4 (EnvironmentEnum.PRDG4, ServerEnum.SERVER4);
	
	private EnvironmentEnum env = null;
	private ServerEnum srv = null;

	private EnvironmentConfig(EnvironmentEnum env, ServerEnum srv) {
		this.env = env;
		this.srv = srv;
	}

	public EnvironmentEnum getEnvironment() {
		return env;
	}
	
	public ServerEnum getServer() {
		return srv;
	}

	public static EnvironmentConfig find(String envStr, String srvStr) {
		return find(EnvironmentEnum.find(envStr), ServerEnum.find(srvStr));
	}

	public static EnvironmentConfig find(EnvironmentEnum env, ServerEnum srv) {
		for (EnvironmentConfig conf : EnvironmentConfig.values())
			if (conf.getEnvironment() == env && conf.getServer() == srv) return conf;
		// The system variables are not part of a predefined config so return the default.
		switch (env) {
			case LOCAL:
				return LOCAL;
			case DEV:
				return DEV;
			case TST:
				return TST;
			case PRD:
				return PRD;
			default:
				return NONE;
		}
	}
}
