<div style="text-align: right; font-size: 8pt; line-height: 1.5em;">
Authored by Sean Seltzer<br/>
sean.seltzer@tribridge.com<br/>
06/16/17<br/>
</div>

# <center>Welcome to QA Automation</center>

---

# What is Selenium?

Selenium is an automated testing tool for end to end testing. [^selenium]

What does this mean?  
* You can automate repetitive activities.
* You can instruct a browser to do almost anything that you can do manually.
* You can exercise a system from an integration perspective.
* You can blackbox test that a system meets technical and user acceptance criteria.
* You can test multiple browsers using the same code to ensure cross platform compliance.
* You can integrate with Appium to test native mobile apps using the same code.

What Selenium is not:
* Selenium is not a replacement for manual testing. 
* Selenium is not a replacement for unit testing.
* Selenium is not a replacement for integration testing.
* Selenium can test APIs, but is not always the right tool for the job.
* Selenium should not be used to automate important tasks.
* Selenium will never give you 100% certainty that code meets requirements.

---

<br/>
<div style="page-break-after: always;"></div>

# Quick Notes about Selenium
Selenium is one of the most popular standard testing tools for this style of testing. Due to it's popularity and comprehensive spec, it was used as the basis of the W3C WebDriver protocol for browser automation. [^webdriver]

It is based on Java and most languages require you to use the Java Selenium Server to interact with remote browsers.

Selenium does not communicate with browsers directly, this is done through drivers such as chromedriver[^chromedriver] and geckodriver[^geckodriver].

---

<div style="page-break-after: always;"></div>
<br/>

# Test Scenarios

### What should I be using Selenium for?

| Scenario                                                           | Can I use Selenium to test this?|
| -------------------------------------------------------------------| --------------------------------|
| I want to test that I can log in as a user.                        | ✔                               |
| I want to test that I can perform a search.                        | ✔                               |
| I want to test that I can click a search item and view the content.| ✔                               |
| I want to test that the content I clicked is the correct content.  | ✔/✘                             |
| I want to test that I'm applying a filter correctly.               | ✔/✘                             |
| I want to test that I can create and delete content.               | ✔/✘                             |
| I want to test that I can print a document.                        | ✘                               |
| I want to test that I am recording analytics via api calls.        | ✘                               |
| I want to test automatically generated HTML and CSS.               | ✔/✘                             |

<div style="page-break-after: always;"></div>
<br/>

### How does this fit into the overall test plan?

* All developers should be testing their code and they must be a part of the automated testing process.
* Unit tests can test code at a level that no other form of testing can reach.
* Integration testing and mocking must still be used to test business logic surrounding volatile operations.
* QAs and Devs must communicate with each other about test coverage.
* No form of testing is perfect, and this is a team effort. Not everything can be automated and not everything should be automated.
* Selenium can test at a level that unit and integration testing can't. End to end tests are the only way to catch certain errors.
* Automated testing is a development activity. Automated tests are code, and suffer the same issues as the code they are testing.
 
<div style="page-break-after: always;"></div>
<br/><br/><br/>

 # Demo
 
 I have put together a demo[^democode] using Selenium and a custom framework[^framework] that makes testing easy for novice programmers. Selenium is easy to learn and difficult to master. The framework takes care of a lot of the complexity and allows for easier enterprise testing.
 <br/><br/><br/>
 
 ```Java
	@Test
	public void testThatSearchReturnsResultsForTerm_ngl_click_search() {
		WebPage webPage = PuxWebsite.openPuxAsNGL1(getBrowser());
		JUnitTester tester = new JUnitTester(webPage);
		
		webPage.interactWithElement().formFill(HomePageMap.SEARCH_BAR, "ngl");
		webPage.interactWithElement().click(HomePageMap.SEARCH_BUTTON);
		tester.verifyElementTextContains(ResultsPageMap.RESULTS_COUNT_PERSONALIZED, "( 15 results )");
		tester.verifyElementTextContains(ResultsPageMap.RESULTS_COUNT_RELATED, "( 92 results )");
	}
 ```
 
<div style="page-break-after: always;"></div>
 <br/><br/><br/>
 
 # Framework
 
  `WebPage` gives you control to interact with the browser.  
  
|                                   |                                   |
|-----------------------------------|-----------------------------------|
|![](http://i.imgur.com/VIwPnEf.png)|![](http://i.imgur.com/YEY5Rqw.png)|
|![](http://i.imgur.com/IeMFRb5.png)|![](http://i.imgur.com/UWU0v4r.png)|
 
<div style="page-break-after: always;"></div>
 <br/><br/><br/>
 
 `JUnitTester` gives you dozens of premade tests.  
 
|                                   |                                   |
|-----------------------------------|-----------------------------------|
|![](http://i.imgur.com/1H1O60P.png)|![](http://i.imgur.com/NUOyDCn.png)|
 
<br/><br/><br/>
 <div style="page-break-after: always;"></div>

# Finding What You Want To Test

You can use firebug debugger or chrome debug tools to identify objects on the page to use in your test.[^cssref] You can use jQuery or to help you narrow down your results and write a good selector.

![http://i.imgur.com/jpgELjF.png](http://i.imgur.com/jpgELjF.png)

<br/>

After you've identified your test targets, then you map your test to the test items. This is very flexible and allows you to update your tests quickly. If you write good selectors, your tests will not be brittle.

<br/>

```Java
public class HomePageMap {
	public static final PageObject SEARCH_BAR = new PageObject(".angucomplete-holder input");
	public static final PageObject SEARCH_BUTTON = new PageObject(".search-icon");
}

public class ResultsPageMap {
	public static final PageObject RESULTS_COUNT_PERSONALIZED =
  	    new PageObject(".results-personalized .results-returned");
	public static final PageObject RESULTS_COUNT_RELATED =
	    new PageObject(".results-related .results-returned");
}
```

<div style="page-break-after: always;"></div>

# Demo

Running different browsers is as easy as changing an environment variable - `browser : chrome` or `browser : firefox`.

Testing for different environments and individual servers works the same way `environment : dev` and `server : 1` points the test to the dev 1 server. You can configure load balancer urls as well as all of your individual servers. These are controlled by easily configured urls in your `Website` object.

Logging in as different users can easily by done by configuring the `Website` object.
* `WebPage webPage = PuxWebsite.openPuxAsNGL1(getBrowser());`  
* `WebPage webPage = PuxWebsite.openPuxAsNGL2(getBrowser());`

<br/><br/><br/>
<br/><br/><br/>
<br/><br/><br/>
<br/><br/><br/>
<br/><br/><br/>
<br/><br/><br/>
<br/><br/><br/>
<div style="page-break-after: always;"></div>
 <br/><br/><br/>
<br/><br/><br/>
<br/><br/><br/>
<br/><br/><br/>

<center>
 
# Getting started with automation is easy.

</center>

<br/><br/><br/>
<br/><br/><br/>
<br/><br/><br/>
<br/><br/><br/>
<br/><br/><br/>
 <div style="page-break-after: always;"></div>
 <br/><br/><br/>
<br/><br/><br/>
<br/><br/><br/>
<br/><br/><br/>

<center>
 
# Q&A

</center>

<br/><br/><br/>
<br/><br/><br/>
<br/><br/><br/>
<br/><br/><br/>
<br/><br/><br/>
 <div style="page-break-after: always;"></div>

# References
[^selenium]: Selenium: [http://www.seleniumhq.org/](http://www.seleniumhq.org/)  
[^webdriver]: WebDriver: [https://www.w3.org/TR/webdriver/](https://www.w3.org/TR/webdriver/)  
[^chromedriver]: ChromeDriver: [https://sites.google.com/a/chromium.org/chromedriver/](https://sites.google.com/a/chromium.org/chromedriver/)  
[^geckodriver]: GeckoDriver: [https://github.com/mozilla/geckodriver/releases](https://github.com/mozilla/geckodriver/releases)  
[^democode]: Demo Code: [https://github.com/Intelladon-LLC/selenium_demo](https://github.com/Intelladon-LLC/selenium_demo)  
[^framework]: Framework: [https://github.com/sseltzer/SeleniumQaDemo](https://github.com/sseltzer/SeleniumQaDemo)  
[^cssref]: CSS Selectors: [https://www.w3schools.com/cssref/css_selectors.asp](https://www.w3schools.com/cssref/css_selectors.asp)