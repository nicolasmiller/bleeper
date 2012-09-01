package com.nicolasmiller.Bleeper;

import com.nicolasmiller.Bleeper.AudioSystemUtility;

import junit.framework.TestCase;

public class AudioSystemUtilityTest extends TestCase {
	public void testIndent() {
		assertEquals("", AudioSystemUtility.indent(0));
		assertEquals("\t", AudioSystemUtility.indent(1));
		assertEquals("\t\t", AudioSystemUtility.indent(2));
		assertEquals("\t\t\t", AudioSystemUtility.indent(3));
		assertEquals("\t\t\t\t", AudioSystemUtility.indent(4));
	}
}
