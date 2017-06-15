package com.sseltzer.selenium.internal.test.tests.environment;
import static org.junit.Assert.assertEquals;

///*
import org.junit.Test;

import com.sseltzer.selenium.framework.environment.EnvironmentHandler;
import com.sseltzer.selenium.framework.environment.enums.BrowserEnum;
import com.sseltzer.selenium.framework.environment.enums.EnvironmentEnum;
import com.sseltzer.selenium.framework.environment.enums.LoggingEnum;
import com.sseltzer.selenium.framework.environment.enums.ServerEnum;

public class EnumTester {
///*
	private static String[] standard = {
		null,
		"",
		"asdf",
		"as df",
		"$",
		"5"
	};
	
	
	@Test
	public void testEnvironmentEnum() {
		stdTest(EnvironmentEnum.class);
		test(EnvironmentEnum.LOCAL, "local");
		test(EnvironmentEnum.DEV, "dev");
		test(EnvironmentEnum.TST, "tst");
		test(EnvironmentEnum.PRD, "prd");
	}
	@Test
	public void testBrowserEnum() {
		stdTest(BrowserEnum.class);
		test(BrowserEnum.CHROME, "chrome");
		test(BrowserEnum.FIREFOX, "firefox");
		test(BrowserEnum.IE, "ie");
	}
	@Test
	public void testServerEnum() {
		stdTest(ServerEnum.class);
		test(ServerEnum.SERVER1, "1");
		test(ServerEnum.SERVER2, "2");
		test(ServerEnum.SERVER3, "3");
		test(ServerEnum.SERVER4, "4");
	}
	@Test
	public void testLoggingEnum() {
		stdTest(LoggingEnum.class);
		test(LoggingEnum.SAVELOG, "savelog");
	}
	
	
	private <E extends Enum<E>> void set(Class<E> e, String s) {
		String propName = "";
		if (e == EnvironmentEnum.class)  propName = "environment";
		else if (e == BrowserEnum.class) propName = "browser";
		else if (e == ServerEnum.class)  propName = "server";
		else if (e == LoggingEnum.class) propName = "logging";
		if (s == null) {
			System.clearProperty(propName);
			return;
		}
		System.setProperty(propName, s);
		EnvironmentHandler.reload();
	}
	private <E extends Enum<E>> void stdTest(Class<E> e) {
		for (String s : standard) {
			set(e, s);
			if (e == EnvironmentEnum.class)  assrt(EnvironmentEnum.NONE, EnvironmentHandler.getEnvironment());
			else if (e == BrowserEnum.class) assrt(BrowserEnum.NONE,     EnvironmentHandler.getBrowser());
			else if (e == ServerEnum.class)  assrt(ServerEnum.NONE, 	 EnvironmentHandler.getServer());
			else if (e == LoggingEnum.class) assrt(LoggingEnum.NONE, 	 EnvironmentHandler.getLoggingParam());
		}
	}
	@SuppressWarnings("unchecked")
	private <E extends Enum<E>> void test(E e, String s) {
		set(e.getClass(), s);
		if (e.getClass() == EnvironmentEnum.class)  assrt((EnvironmentEnum)e, EnvironmentHandler.getEnvironment());
		else if (e.getClass() == BrowserEnum.class) assrt((BrowserEnum)e, EnvironmentHandler.getBrowser());
		else if (e.getClass() == ServerEnum.class)  assrt((ServerEnum)e, EnvironmentHandler.getServer());
		else if (e.getClass() == LoggingEnum.class) assrt((LoggingEnum)e, EnvironmentHandler.getLoggingParam());
	}
	private <E extends Enum<E>> void assrt(E e, E a) {
		assertEquals(e, a);
	}//*/
}
