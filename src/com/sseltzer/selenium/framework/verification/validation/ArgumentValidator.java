package com.sseltzer.selenium.framework.verification.validation;

import java.util.List;

import com.sseltzer.selenium.framework.error.ErrorManager;
import com.sseltzer.selenium.framework.strings.maps.InternalErrorStrings;

/**
 * The ArgumentValidator object is a simple convenience object which provides a cleaner
 * method to perform internal validation of variables. Since the framework is intended
 * to provide a test structure for QA staff, some safeguards have been put in place to
 * ensure that the framework is sound from an external perspective. The core of the framework
 * provides error checking on most core calls to ensure that a test author does not inadvertently
 * pass in a null argument or invalid index. As part of this, the ArgumentValidator simplified
 * this repetitive tasks by implementing a chainable check to test internal arguments and
 * produce an InternalException notifying the test author of the error. The ArgumentValidator provides
 * a simple function to validate any object and ensure that it is not null while taking in the
 * argument name for debugging output. If the argument is not null, the object returns itself to
 * perform another chained validation call. This is especially important when dealing with PageObjects
 * as the toString method is generally called to extract the selector string from the page object
 * when interacting with the Selenium framework. If the PageObject passed in by the test author were
 * null, we would have an unchecked null exception bubble up through the framework; instead a very
 * specific error message is provided with a caught InternalException pointing to the exact cause of
 * the failure.
 * 
 * @author Sean Seltzer
 *
 */
public class ArgumentValidator {

	public ArgumentValidator() {
	}

	public static ArgumentValidator create() {
		return new ArgumentValidator();
	}

	public ArgumentValidator validate(Object argument, String argumentName) {
		if (argument == null) ErrorManager.throwAndDumpInternalError(InternalErrorStrings.NULL_ARG, argumentName);
		return this;
	}

	//TODO look through the project to see if there's anything similar being done, refactor to use this.
	public ArgumentValidator validateArrayNotEmpty(Object[] argument, String argumentName) {
		//TODO create a new Internal Error String for this
		validate(argument, argumentName);
		if (argument.length < 1) ErrorManager.throwAndDumpInternalError(InternalErrorStrings.NULL_ARG, argumentName);
		return this;
	}

	//TODO look through the project to see if there's anything similar being done, refactor to use this.
	public ArgumentValidator validateArrayLength(Object[] argument, int i, String argumentName) {
		//TODO create a new Internal Error String for this
		validate(argument, argumentName);
		if (argument.length != i) ErrorManager.throwAndDumpInternalError(InternalErrorStrings.NULL_ARG, argumentName);
		return this;
	}

	//TODO look through the project to see if there's anything similar being done, refactor to use this.
	public ArgumentValidator validatePositive(int i, String argumentName) {
		//TODO create a new Internal Error String for this
		if (i < 0) ErrorManager.throwAndDumpInternalError(InternalErrorStrings.NULL_ARG, argumentName);
		return this;
	}

	//TODO look through the project to see if there's anything similar being done, refactor to use this.
	public ArgumentValidator validatePositive(int value, int upper, String argumentName) {
		//TODO create a new Internal Error String for this
		if (value < 0) ErrorManager.throwAndDumpInternalError(InternalErrorStrings.NULL_ARG, argumentName);
		if (value > upper) ErrorManager.throwAndDumpInternalError(InternalErrorStrings.NULL_ARG, argumentName);
		return this;
	}

	//TODO look through the project to see if there's anything similar being done, refactor to use this.
	public ArgumentValidator validateListNotEmpty(List<?> list, String argumentName) {
		//TODO create a new Internal Error String for this
		if (list.size() < 1) ErrorManager.throwAndDumpInternalError(InternalErrorStrings.NULL_ARG, argumentName);
		return this;
	}
}
