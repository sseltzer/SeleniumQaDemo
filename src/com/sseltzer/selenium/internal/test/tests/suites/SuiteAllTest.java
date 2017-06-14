
package com.sseltzer.selenium.internal.test.tests.suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.sseltzer.selenium.framework.verification.junit.TestSuite;
import com.sseltzer.selenium.internal.test.tests.environment.EnumTester;
import com.sseltzer.selenium.internal.test.tests.error.ErrorManagerInternal;

@RunWith(Suite.class)
@Suite.SuiteClasses({
		EnumTester.class, 
		ErrorManagerInternal.class
})
public class SuiteAllTest extends TestSuite {
}
