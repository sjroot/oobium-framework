/*******************************************************************************
 * Copyright (c) 2010 Oobium, Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Jeremy Dowdall <jeremy@oobium.com> - initial API and implementation
 ******************************************************************************/
package org.oobium.build.esp;

import static org.junit.Assert.*;

import org.junit.Test;
import org.oobium.build.esp.EspCompiler;
import org.oobium.build.esp.EspDom;
import org.oobium.build.esp.ESourceFile;

public class EssCompilerTests {

	private String body(String method) {
		int s1 = 0;
		while(s1 < method.length() && method.charAt(s1) != '{') {
			s1++;
		}
		s1++;
		while(s1 < method.length() && Character.isWhitespace(method.charAt(s1))) {
			s1++;
		}
		return method.substring(s1, method.length() - 3).replace("\n\t\t", "\n");
	}
	
	private String css(String ess) {
		ESourceFile src = src(ess);
		String str = body(src.getMethod("doRender"));
		System.out.println(src.getMethod("doRender").replace("\n\t", "\n"));
		return str;
	}
	
	private ESourceFile src(String ess) {
		EspDom dom = new EspDom("MyEss.ess", ess);
		EspCompiler e2j = new EspCompiler("com.mydomain", dom);
		return e2j.compile();
	}

	@Test
	public void testEmpty() throws Exception {
		assertFalse(src("").hasMethod("render"));
	}
	
	@Test
	public void testImport() throws Exception {
		assertTrue(src("import com.mydomain.MyClass").hasImport("com.mydomain.MyClass"));
	}
	
	@Test
	public void testConstructor() throws Exception {
		String esp;
		esp = "MyEss(String arg1)";
		assertTrue(src(esp).hasVariable("arg1"));
		assertEquals("String arg1", src(esp).getVariable("arg1"));
		assertEquals(1, src(esp).getConstructorCount());
		assertTrue(src(esp).hasConstructor(0));
		assertEquals("\tpublic MyEss(String arg1) {\n\t\tthis.arg1 = arg1;\n\t}", src(esp).getConstructor(0));
	}
	
	@Test
	public void testCssOnly() throws Exception {
		String ess;
		ess = ".myClass { color: red }";
		assertEquals("sb.append(\".myClass{color:red}\");", css(ess));

		ess = ".myClass { color: red }\n\n.myOtherClass\n\tcolor: blue";
		assertEquals("sb.append(\".myClass{color:red} .myOtherClass{color:blue}\");", css(ess));

		ess = 	"input:not([type=submit])\n"+
				"\tborder: 1px solid black\n"+
				"\tpadding: 3px 6px\n"+
				"\twidth: 200px\n"+
				"\tbackground-color: #eee\n"+
				"\t-moz-border-radius: 8px\n"+
				"\tborder-radius: 8px\n"+
				"input[type=text]\n"+
				"\tcolor: grey";

		assertEquals("sb.append(\"input[type=text]{color:red}\");", css(ess));
	}

	@Test
	public void testJava() throws Exception {
		String ess;
		ess = ".myClass { width = (width * 2) + \"px\" }";
		assertEquals("sb.append(\".myClass{width:\").append((width * 2) + \"px\").append(\"}\");", css(ess));

		ess = "-int width = 10;\n\n.myClass { width = (width * 2) + \"px\" }";
		assertEquals("int width = 10;\nsb.append(\".myClass{width:\").append((width * 2) + \"px\").append(\"}\");", css(ess));
	}

}