package com.sseltzer.selenium.framework.error;

import java.io.PrintStream;
import java.util.ArrayList;

import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.interactions.MoveTargetOutOfBoundsException;
import org.openqa.selenium.support.ui.UnexpectedTagNameException;

import com.sseltzer.selenium.framework.error.exceptions.FrameworkException;
import com.sseltzer.selenium.framework.error.exceptions.InternalException;
import com.sseltzer.selenium.framework.error.exceptions.PublicException;
import com.sseltzer.selenium.framework.selenium.mapping.PageObject;
import com.sseltzer.selenium.framework.selenium.webpage.base.WebPage;
import com.sseltzer.selenium.framework.strings.maps.FrameworkStrings;
import com.sseltzer.selenium.framework.strings.maps.InternalErrorStrings;
import com.sseltzer.selenium.framework.strings.maps.PublicErrorStrings;

/**
 * The ErrorManager object acts as a static service to handle all errors throughout the framework. Every
 * error that occurs is routed through the error manager whether internal to the framework or assertion error,
 * and the ErrorManager throws the appropriate exception. Three types of exceptions exist. A parent
 * FrameworkException is used as an upcast to handle all exceptions the same way without cluttering the method
 * signatures and try catch blocks. InternalException is one which it has been determined that a test author has
 * made an error, for example passing a null to a required variable. PublicException is one in which an assert
 * has failed somewhere meaning that a test has failed - usually from JUnitTester or its derivatives. Note that
 * some InternalExceptions cannot be known and appear as PublicExceptions because the framework can only detect
 * so much generically. The Framework cannot know that a test for the existence of an object will fail if that
 * object never existed on a web page to begin with. As such, InternalExceptions are limited to Java errors made 
 * by the test author. InternalException and PublicException both extend FrameworkException, thus allowing the
 * common error handing method as they are both upcast.
 * <br><br>
 * In addition to exceptions, all error messages are routed through ErrorManager. ErrorManager maintains a
 * PrintStream much in the same way as System.out, and is able to be piped to any other stream. This funnel
 * allows the default message output to be changed later by simply changing a single variable. Any
 * System.out.println() or similar calls may be made by calling ErrorManager.out(). This also features an
 * upcasted Object as the sole input to out, where .toString is called on any object passed in. That allows
 * for any object to be funneled through by making a single call, whether it is a String, PageObject, or some
 * other class. This implementation mimics the Java standard library.
 * <br><br>
 * The ErrorManager also features two error modes. The default error mode will cause the framework to fail on
 * the first error as an exception will immediately be thrown. A collect error mode will cause the framework
 * to collect and hold onto the exceptions and attempt to continue the test while collecting all subsequent
 * exceptions. All Exceptions are available to be pulled at the end of the test and dumped at one time instead
 * of stopping on the first error. This is useful for iterative tests when a single point of failure could prevent
 * dozens of subsequent tests from being performed - even if the failure has no real consequence on the rest of
 * the test.
 * <br><br>
 * The ErrorManager's primary purpose is to manage errors whether it collects them or throws them immediately. It
 * has a dual role of providing exacting and verbose error messages to easily diagnose issues. This is done with
 * the form filled error string system. Two .properties files exist in the strings package which maintain a listing
 * of template errors that repeatedly occur in the framework. Template strings exist in the strings which are markers
 * to be replaced with real data. For instance:
 * <br><br>
 * {@literal
 * PES.11=\n\nError:\tElement text does not match expected value.\nElement:\t%STR0\nExpected:\t%STR1\nActual:  \t%STR2\n\n%STACK
 * }
 * <br><br>
 *  In this case, when an exception is thrown, this template is provided. The error indicates that an element text
 *  did not match the expected value. Form filled data is provided as an ArrayList<String> which is commonly done with
 *  the FillDataBuilder helper class. The template is taken along with the form fill data, and replaced one by one with
 *  the form fill data. First the %STR0 marker is searched for and replaced with the element mapping text. The expected
 *  text string is form filled next into %STR1. Finally, the actual text is form filled into %STR2. The %STACK marker
 *  is a special marker which tells the ErrorManager to take a stack dump of the current execution environment, and filter
 *  out anything that is not within the current package. This shows a filtered stack trace of only the project in which the
 *  error occurred. This will eliminate a large amount of JUnit stack dump which is irrelevant to writing test scripts. Finally
 *  the rest of the exception dump will have the normal exception information. So when an error is thrown, internal or public,
 *  a form filled template with a specific error and data that cause the error is provided as well as a filtered stack trace
 *  that will show only the route through the application that cause the error.    
 * @author Sean Seltzer
 *
 */
public class ErrorManager {
	private static final boolean DOSTACKLINKS = false;
	private static PrintStream printStream = System.out;
	private static final boolean DUMP_SOURCE_ON_ERROR = false;

	/**
	 * Returns the PrintStream currently being used by ErrorManager for out calls.
	 * @return the PrintStream currently being used by ErrorManager for out calls.
	 */
	public PrintStream getMessagePrintStream() {
		return ErrorManager.printStream;
	}

	/**
	 * Sets the PrintStream to be used by ErrorManager.out calls.
	 * @param printStream to be used by ErrorManager.out calls.
	 */
	public static void setMessagePrintStream(PrintStream printStream) {
		ErrorManager.printStream = printStream;
	}

	/**
	 * Resets the ErrorManager.out PrintStream to the default of System.out.
	 */
	public static void setDefaultPrintStream() {
		ErrorManager.printStream = System.out;
	}

	/**
	 * Write to the stored PrintStream. Any object passed will have toString called. This mirrors
	 * the Java library functions for sysout.
	 * @param object to be written to the PrintStream.
	 */
	public static void print(Object object) {
		printStream.print(object);
	}
	
	/**
	 * Write to the stored PrintStream. Any object passed will have toString called. This mirrors
	 * the Java library functions for sysout.
	 * @param object to be written to the PrintStream.
	 */
	public static void println(Object object) {
		printStream.println(object);
	}
	
	/**
	 * Write a newline character to the stored PrintStream.
	 */
	public static void println() {
		printStream.print('\n');
	}

	private static ArrayList<FrameworkException> exceptions = new ArrayList<FrameworkException>();
	private static final boolean DEFAULT_ERROR_MODE = false;
	private static boolean collectErrors = DEFAULT_ERROR_MODE;

	/**
	 * Sets the ErrorManager error mode to collect exceptions instead of throwing them immediately. Exceptions
	 * may be retrieved layer by calling getExceptions.
	 */
	public static void setErrorModeCollectErrors() {
		collectErrors = true;
	}

	/**
	 * Sets the ErrorManager error mode to throw exceptions immediately when a throw method is called.
	 */
	public static void setErrorModeThrowImmediately() {
		collectErrors = false;
	}

	/**
	 * Resets the ErrorManager error mode to the default state of throwing immediately.
	 */
	public static void setErrorModeDefault() {
		collectErrors = DEFAULT_ERROR_MODE;
	}

	/**
	 * Returns whether or not the ErrorManager is in error collection mode.
	 * @return whether or not the ErrorManager is in error collection mode.
	 */
	public static boolean isCollectingErrors() {
		return collectErrors;
	}

	/**
	 * Returns an ArrayList of stored exceptions collected with the error manager collection mode.
	 * @return an ArrayList of stored exceptions collected with the error manager collection mode.
	 */
	public static ArrayList<FrameworkException> getExceptions() {
		return exceptions;
	}

	/**
	 * Clear stored exceptions.
	 */
	public static void clearExceptions() {
		exceptions.clear();
	}

	public static void throwAndDumpCoreException(Exception exception, FillDataBuilder data) {
		// The error does not always have additional data to throw. e.g. a clear action
		if (data == null) data = FillDataBuilder.create("");

		try {
			throw exception;
		} catch (ElementNotVisibleException e) {
			ErrorManager.throwAndDump(PublicErrorStrings.ELEMENT_NOT_VISIBLE, data);
		} catch (StaleElementReferenceException e) {
			ErrorManager.throwAndDump(PublicErrorStrings.ELEMENT_STALE, data);
		} catch (InvalidElementStateException e) {
			ErrorManager.throwAndDump(PublicErrorStrings.INVALID_STATE, data);
		} catch (MoveTargetOutOfBoundsException e) {
			ErrorManager.throwAndDump(PublicErrorStrings.MOVE_OUT_OF_BOUNDS, data);
		} catch (NoSuchWindowException e) {
			ErrorManager.throwAndDump(PublicErrorStrings.NO_WINDOW, data);
		} catch (NoSuchFrameException e) {
			ErrorManager.throwAndDump(PublicErrorStrings.NO_FRAME, data);
		} catch (NoSuchElementException e) {
			ErrorManager.throwAndDump(PublicErrorStrings.ELEMENT_NOT_EXIST, data);
		} catch (UnsupportedOperationException e) {
			ErrorManager.throwAndDump(PublicErrorStrings.UNSUPPORTED_OPERATION, data);
		} catch (UnexpectedTagNameException e) {
			ErrorManager.throwAndDump(PublicErrorStrings.BAD_TAG_NAME, data);
		} catch (TimeoutException e) {
			ErrorManager.throwAndDump(PublicErrorStrings.TIMEOUT, data);
		} catch (Exception e) {
			data.add(e.getMessage());
			ErrorManager.throwAndDump(PublicErrorStrings.UNKNOWN, data);
		}
		data.add(exception.getMessage());
		ErrorManager.throwAndDump(PublicErrorStrings.UNKNOWN, data);
	}

	/**
	 * Throws an internal exception with the given String template. If error collection mode is on, then it stores the exception
	 * instead of throwing it immediately. InternalExceptions have an added warning to indicate that the exception was an internal
	 * one and not caused by an actual assert.
	 * @param template String template indicating the cause of the exception.
	 * @throws InternalException 
	 */
	public static void throwAndDumpInternalError(Enum<?> template) {
		throwAndDumpInternalError(template, FillDataBuilder.create());
	}

	/**
	 * Throws an internal exception with the given String template. If error collection mode is on, then it stores the exception
	 * instead of throwing it immediately. InternalExceptions have an added warning to indicate that the exception was an internal
	 * one and not caused by an actual assert.
	 * @param template String template indicating the cause of the exception.
	 * @param data is the form fill data to be inserted into the template string to provide additional information.
	 * @throws InternalException
	 */
	public static void throwAndDumpInternalError(Enum<?> template, String data) {
		throwAndDumpInternalError(template, FillDataBuilder.create(data));
	}

	/**
	 * Throws an internal exception with the given String template. If error collection mode is on, then it stores the exception
	 * instead of throwing it immediately. InternalExceptions have an added warning to indicate that the exception was an internal
	 * one and not caused by an actual assert.
	 * @param template String template indicating the cause of the exception.
	 * @param data is the form fill data to be inserted into the template string to provide additional information.
	 * @throws InternalException
	 */
	public static void throwAndDumpInternalError(Enum<?> template, FillDataBuilder data) {
		dumpSourceIfEnabled();
		InternalException e = new InternalException(InternalErrorStrings.WARNING + fillErrorString(template, data)); 
		if (collectErrors) exceptions.add(e);
		else throw e;
	}

	/**
	 * Throws a public exception with the given String template. If error collection mode is on, then it stores the exception instead
	 * of throwing it immediately. PublicExceptions are used for assert failures.
	 * @param template String template indicating the cause of the exception.
	 * @param data is the form fill data to be inserted into the template string to provide additional information.
	 * @throws PublicException
	 */
	public static void throwAndDump(Enum<?> template, PageObject data) {
		throwAndDump(template, FillDataBuilder.create(data));
	}

	/**
	 * Throws a public exception with the given String template. If error collection mode is on, then it stores the exception instead
	 * of throwing it immediately. PublicExceptions are used for assert failures.
	 * @param template String template indicating the cause of the exception.
	 * @param data is the form fill data to be inserted into the template string to provide additional information.
	 * @throws PublicException
	 */
	public static void throwAndDump(Enum<?> template, String data) {
		throwAndDump(template, FillDataBuilder.create(data));
	}

	/**
	 * Throws a public exception with the given String template. If error collection mode is on, then it stores the exception instead
	 * of throwing it immediately. PublicExceptions are used for assert failures.
	 * @param template String template indicating the cause of the exception.
	 * @param data is the form fill data to be inserted into the template string to provide additional information.
	 * @throws PublicException
	 */
	public static void throwAndDump(Enum<?> template, ArrayList<String> data) {
		dumpSourceIfEnabled();
		PublicException e = new PublicException(fillErrorString(template, data));
		if (collectErrors) exceptions.add(e);
		else throw e;
	}

	/**
	 * Throws a public exception with the given String template. If error collection mode is on, then it stores the exception instead
	 * of throwing it immediately. PublicExceptions are used for assert failures.
	 * @param template String template indicating the cause of the exception.
	 * @param data is the form fill data to be inserted into the template string to provide additional information.
	 * @throws PublicException
	 */
	public static void throwAndDump(Enum<?> template, FillDataBuilder data) {
		throwAndDump(template, data.get());
	}
	
	public static void throwAndDump(String str) {
		throwAndDump(str, null);
	}
	
	public static void throwAndDump(String str, Throwable src) {
		dumpSourceIfEnabled();
		PublicException e = new PublicException(str, src);
		if (collectErrors) exceptions.add(e);
		else throw e;
	}
	public static void throwAndDump(String str, Throwable src, Object... data) {
		throwAndDump(String.format(str, data), src);
	}
	
	public static void throwDeadCode() {
		ErrorManager.throwAndDumpInternalError(InternalErrorStrings.DEAD_END_CODE);
	}

	/**
	 * Takes a String template and fill data. It looks for a String in the template specfied in FrameworkStrings.properties
	 * to find all occurrences of where it should fill the data into and begins to replace them will the provided data. If it
	 * finds a special marker for stack traces, also specified in the FrameworkStrings.properties, then it replaces it with
	 * a filtered stack trace.
	 * @param template String to form fill the data into.
	 * @param data to be form filled into the template.
	 * @return complete form filled String.
	 */
	public static String fillErrorString(Enum<?> template, String data) {
		return fillErrorString(template, FillDataBuilder.create(data));
	}

	/**
	 * Takes a String template and fill data. It looks for a String in the template specfied in FrameworkStrings.properties
	 * to find all occurrences of where it should fill the data into and begins to replace them will the provided data. If it
	 * finds a special marker for stack traces, also specified in the FrameworkStrings.properties, then it replaces it with
	 * a filtered stack trace.
	 * @param template String to form fill the data into.
	 * @param fillData to be form filled into the template.
	 * @return complete form filled String.
	 */
	public static String fillErrorString(Enum<?> template, ArrayList<String> fillData) {
		String filledMessage = template.toString();
		if (template.toString().contains(FrameworkStrings.STACK_MARKER.toString())) filledMessage = fillStackTrace(template);
		int index = 0;
		while (filledMessage.contains(FrameworkStrings.STR_MARKER.toString())) {
			if (index >= fillData.size()) break;
			filledMessage = filledMessage.replace(FrameworkStrings.STR_MARKER.toString() + index, fillData.get(index));
			++index;
		}
		return filledMessage;
	}

	/**
	 * Takes a String template and fill data. It looks for a String in the template specfied in FrameworkStrings.properties
	 * to find all occurrences of where it should fill the data into and begins to replace them will the provided data. If it
	 * finds a special marker for stack traces, also specified in the FrameworkStrings.properties, then it replaces it with
	 * a filtered stack trace.
	 * @param template String to form fill the data into.
	 * @param fillData to be form filled into the template.
	 * @return complete form filled String.
	 */
	public static String fillErrorString(Enum<?> template, FillDataBuilder fillData) {
		return fillErrorString(template, fillData.get());
	}

	/**
	 * Takes a String template and fill data. If it finds a special marker for stack traces, specified in the 
	 * FrameworkStrings.properties, then it replaces it with a filtered stack trace.
	 * @param template String to form fill the data into.
	 * @return complete form filled String.
	 */
	private static String fillStackTrace(Enum<?> template) {
		return template.toString().replace(FrameworkStrings.STACK_MARKER.toString(), filterStackTrace(Thread.currentThread().getStackTrace()));
	}

	/**
	 * Takes a stack trace and filters it based on the package provided in FrameworkStrings.properties.
	 * Returns a concatenated string of filtered stack trace elements formatted in a human readable manner.
	 * @param stackTraceElements
	 * @return filtered stack trace from this project only.
	 */
	private static String filterStackTrace(StackTraceElement[] stackTraceElements) {
		//String s = "";
		StringBuffer s = new StringBuffer();
		FillDataBuilder fillData = new FillDataBuilder();
		for (StackTraceElement element : stackTraceElements) {
			if (element.getClassName().contains(FrameworkStrings.FILTERPACKAGE.toString())) {
				fillData.add(element.getClassName());
				fillData.add(element.getMethodName());
				fillData.add(String.valueOf(element.getLineNumber()));
				//s += fillErrorString(FrameworkStrings.STACKLINE, fillData);
				s.append(fillErrorString(FrameworkStrings.STACKLINE, fillData));
				fillData.clear();
				if (DOSTACKLINKS) {
					fillData.add(element.getClassName());
					fillData.add(String.valueOf(element.getLineNumber()));
					ErrorManager.print(fillErrorString(FrameworkStrings.LINE_HYPERLINK, fillData));
					fillData.clear();
				}
			}
		}
		return s.toString();
	}

	/**
	 * Debug function to dump the page source to the current stored PrintStream.
	 */
	private static void dumpSourceIfEnabled() {
		if (!DUMP_SOURCE_ON_ERROR) return;
		ErrorManager.print(WebPage.getCurrentPage().getBrowser().getPageSource());
	}
}
