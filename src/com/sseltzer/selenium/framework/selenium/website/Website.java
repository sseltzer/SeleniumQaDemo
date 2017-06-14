package com.sseltzer.selenium.framework.selenium.website;

import java.util.Set;

import com.sseltzer.selenium.framework.environment.EnvironmentHandler;
import com.sseltzer.selenium.framework.environment.enums.EnvironmentConfig;
import com.sseltzer.selenium.framework.selenium.browsers.Browser;
import com.sseltzer.selenium.framework.selenium.webpage.base.WebPage;

/**
 * The Website object represents a given domain and set of URLs for a website. There
 * is at least one of these object per project and provide a project specific mapping
 * to each target server URL for test. The Domain and EnvironmentConfig objects combine
 * to provide a concrete implementation of setBaseURL with enough information to differentiate
 * which Domain is desired first, then which EnvironmentEnum/ServerEnum combination should be 
 * direction to which URL. The Domain object currently represents the US or Canada which not all
 * websites need to implement. However, the setBaseURL function should switch on all possible
 * combinations of EnvironmentConfig as it represents all possible target environments for the
 * current server configuration. For the EnvironmentConfig, there should be a local config, dev, 
 * test, and production servers. The TeamCity servers are preconfigured to target each main target
 * environment providing a mechanism to automate testing for all environments. The default environment
 * will be US on prd without a specified server, which everyone's Eclipse will run unless they
 * set the environmental variables in the run configuration. The Website's secondary purpose is to
 * provide shortcuts to open the given website at the desired page for reduced code in TestScripts.
 * For example, many sites require an initial login or navigation to a website landing page to perform
 * each test. The concrete implementations of this class may provide a unique open method to abstract
 * that common behavior and reduce repetitive code necessary for each test method. The Website class
 * also serves as the container for the specific Browser instance to be used for the test after being
 * requested from the BrowserFactory.
 * 
 * @author Sean Seltzer
 *
 */
public abstract class Website {

	private Browser browser = null;

	public Browser getBrowser() {
		return browser;
	}

	public void setBrowser(Browser browser) {
		this.browser = browser;
	}

	public enum Domain {
		US, CA
	}

	private static final Domain DEFAULT_DOMAIN = Domain.US;
	private Domain domain = null;

	public Domain getDomain() {
		return domain;
	}
	
	public void setDomain(Domain domain){
		this.domain = domain;
	}

	private EnvironmentConfig environmentConfig = null;

	public EnvironmentConfig getEnvironmentConfig() {
		return environmentConfig;
	}

	private String baseURL = null;

	public String getBaseURL() {
		return baseURL;
	}

	public void setBaseURL(String baseURL) {
		this.baseURL = baseURL;
	}


	
	/**
	 * Website object using the default domain of US.
	 * @param browser
	 */
	public Website(Browser browser) {
		this(browser, DEFAULT_DOMAIN);
	}

	/**
	 * Website object using a provided domain.
	 * @param browser
	 * @param domain
	 */
	public Website(Browser browser, Domain domain) {
		this(browser, EnvironmentHandler.getEnvironmentConfig(), domain);
	}

	/**
	 * Website object using a provided domain and EnvironmentConfig target.
	 * @param browser
	 * @param environmentConfig
	 * @param domain
	 */
	public Website(Browser browser, EnvironmentConfig environmentConfig, Domain domain) {
		this.browser = browser;
		this.domain = domain;
		this.environmentConfig = environmentConfig;
		setBaseURL();
	}

	/**
	 * Sets the URLs for each target domain in the EnvironmentConfig.
	 */
	public abstract void setBaseURL();

	/**
	 * Open the website at the provided URL configured in setBaseURL. For example:
	 * "www.google.com/"
	 * @return a WebPage object pointing to the URL configured in setBaseURL.
	 */
	public WebPage open() {
		return open("");
	}

	/**
	 * Open the website at the provided URL plus the given URL suffix. For example
	 * "www.google.com/" with "mail/" suffix to direct the google website to gmail.
	 * @param extendedURL
	 * @return a WebPage object pointing to the URL configured in setBaseURL plus the extended URL String.
	 */
	public WebPage open(String extendedURL) {
		WebPage.close();
		getBrowser().getWebDriver().get(baseURL + removeBaseUrl(extendedURL));
		WebPage webPage = WebPage.getInstance(getBrowser(), this);
		getBrowser().loadPrepopulatedCookies();
		return webPage;
	}
	
	public String removeBaseUrl(String fullUrl){
		if (fullUrl.contains(baseURL)) { 	return fullUrl.replace(baseURL, "").toString();	};
		return fullUrl;
	}

	/**
	 * Focuses this website based on the base URL. If two pages are open with this
	 * base URL, then the first one found from Selenium is returned. 
	 * @param webPage
	 */
	public static void focusWebsite(WebPage webPage) {
		if (webPage.getBrowser().getCountOfActiveWindowHandles() > 1) {
			Set<String> windowHandles = webPage.getBrowser().getAllActiveWindowHandles();
			String windowURL;
			for (String handle : windowHandles) {
				windowURL = webPage.getBrowser().getWebDriver().switchTo().window(handle).getCurrentUrl();
				if (windowURL.contains(webPage.getWebsite().getBaseURL())) {
					webPage.getBrowser().switchFocusToSpecificWindow(handle);
					break;
				}
			}
		}
	}
}
