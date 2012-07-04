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

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class Constants {

	public static final Map<String, String> CSS_PROPERTIES;
	public static final Map<String, String> MARKUP_TAGS;
	public static final Set<String> DOM_EVENTS;
	public static final Set<String> JAVA_KEYWORDS;
	public static final Set<String> JS_KEYWORDS;
	
	static {
		CSS_PROPERTIES = new TreeMap<String, String>();
	    CSS_PROPERTIES.put("azimuth", "azimuth");
	    CSS_PROPERTIES.put("background", "background");
	    CSS_PROPERTIES.put("background-attachment", "background-attachment");
	    CSS_PROPERTIES.put("background-color", "background-color");
	    CSS_PROPERTIES.put("background-image", "background-image");
	    CSS_PROPERTIES.put("background-position", "background-position");
	    CSS_PROPERTIES.put("background-repeat", "background-repeat");
	    CSS_PROPERTIES.put("border", "border");
	    CSS_PROPERTIES.put("border-collapse", "border-collapse");
	    CSS_PROPERTIES.put("border-color", "border-color");
	    CSS_PROPERTIES.put("border-spacing", "border-spacing");
	    CSS_PROPERTIES.put("border-style", "border-style");
	    CSS_PROPERTIES.put("border-top", "border-top");
	    CSS_PROPERTIES.put("border-right", "border-right");
	    CSS_PROPERTIES.put("border-bottom", "border-bottom");
	    CSS_PROPERTIES.put("border-left", "border-left");
	    CSS_PROPERTIES.put("border-top-color", "border-top-color");
	    CSS_PROPERTIES.put("border-right-color", "border-right-color");
	    CSS_PROPERTIES.put("border-bottom-color", "border-bottom-color");
	    CSS_PROPERTIES.put("border-left-color", "border-left-color");
	    CSS_PROPERTIES.put("border-top-style", "border-top-style");
	    CSS_PROPERTIES.put("border-right-style", "border-right-style");
	    CSS_PROPERTIES.put("border-bottom-style", "border-bottom-style");
	    CSS_PROPERTIES.put("border-left-style", "border-left-style");
	    CSS_PROPERTIES.put("border-top-width", "border-top-width");
	    CSS_PROPERTIES.put("border-right-width", "border-right-width");
	    CSS_PROPERTIES.put("border-bottom-width", "border-bottom-width");
	    CSS_PROPERTIES.put("border-left-width", "border-left-width");
	    CSS_PROPERTIES.put("border-width", "border-width");
	    CSS_PROPERTIES.put("bottom", "bottom");
	    CSS_PROPERTIES.put("caption-side", "caption-side");
	    CSS_PROPERTIES.put("clear", "clear");
	    CSS_PROPERTIES.put("clip", "clip");
	    CSS_PROPERTIES.put("color", "color");
	    CSS_PROPERTIES.put("content", "content");
	    CSS_PROPERTIES.put("counter-increment", "counter-increment");
	    CSS_PROPERTIES.put("counter-reset", "counter-reset");
	    CSS_PROPERTIES.put("cue", "cue");
	    CSS_PROPERTIES.put("cue-after", "cue-after");
	    CSS_PROPERTIES.put("cue-before", "cue-before");
	    CSS_PROPERTIES.put("cursor", "cursor");
	    CSS_PROPERTIES.put("direction", "direction");
	    CSS_PROPERTIES.put("display", "display");
	    CSS_PROPERTIES.put("elevation", "elevation");
	    CSS_PROPERTIES.put("empty-cells", "empty-cells");
	    CSS_PROPERTIES.put("float", "float");
	    CSS_PROPERTIES.put("font", "font");
	    CSS_PROPERTIES.put("font-family", "font-family");
	    CSS_PROPERTIES.put("font-size", "font-size");
	    CSS_PROPERTIES.put("font-size-adjust", "font-size-adjust");
	    CSS_PROPERTIES.put("font-stretch", "font-stretch");
	    CSS_PROPERTIES.put("font-style", "font-style");
	    CSS_PROPERTIES.put("font-variant", "font-variant");
	    CSS_PROPERTIES.put("font-weight", "font-weight");
	    CSS_PROPERTIES.put("height", "height");
	    CSS_PROPERTIES.put("left", "left");
	    CSS_PROPERTIES.put("letter-spacing", "letter-spacing");
	    CSS_PROPERTIES.put("line-height", "line-height");
	    CSS_PROPERTIES.put("list-style", "list-style");
	    CSS_PROPERTIES.put("list-style-image", "list-style-image");
	    CSS_PROPERTIES.put("list-style-position", "list-style-position");
	    CSS_PROPERTIES.put("list-style-type", "list-style-type");
	    CSS_PROPERTIES.put("margin", "margin");
	    CSS_PROPERTIES.put("margin-top", "margin-top");
	    CSS_PROPERTIES.put("margin-right", "margin-right");
	    CSS_PROPERTIES.put("margin-bottom", "margin-bottom");
	    CSS_PROPERTIES.put("margin-left", "margin-left");
	    CSS_PROPERTIES.put("marker-offset", "marker-offset");
	    CSS_PROPERTIES.put("marks", "marks");
	    CSS_PROPERTIES.put("max-height", "max-height");
	    CSS_PROPERTIES.put("max-width", "max-width");
	    CSS_PROPERTIES.put("min-height", "min-height");
	    CSS_PROPERTIES.put("min-width", "min-width");
	    CSS_PROPERTIES.put("orphans", "orphans");
	    CSS_PROPERTIES.put("outline", "outline");
	    CSS_PROPERTIES.put("outline-color", "outline-color");
	    CSS_PROPERTIES.put("outline-style", "outline-style");
	    CSS_PROPERTIES.put("outline-width", "outline-width");
	    CSS_PROPERTIES.put("overflow", "overflow");
	    CSS_PROPERTIES.put("padding", "padding");
	    CSS_PROPERTIES.put("padding-top", "padding-top");
	    CSS_PROPERTIES.put("padding-right", "padding-right");
	    CSS_PROPERTIES.put("padding-bottom", "padding-bottom");
	    CSS_PROPERTIES.put("padding-left", "padding-left");
	    CSS_PROPERTIES.put("page", "page");
	    CSS_PROPERTIES.put("page-break-after", "page-break-after");
	    CSS_PROPERTIES.put("page-break-before", "page-break-before");
	    CSS_PROPERTIES.put("page-break-inside", "page-break-inside");
	    CSS_PROPERTIES.put("pause", "pause");
	    CSS_PROPERTIES.put("pause-after", "pause-after");
	    CSS_PROPERTIES.put("pause-before", "pause-before");
	    CSS_PROPERTIES.put("pitch", "pitch");
	    CSS_PROPERTIES.put("pitch-range", "pitch-range");
	    CSS_PROPERTIES.put("play-during", "play-during");
	    CSS_PROPERTIES.put("position", "position");
	    CSS_PROPERTIES.put("quotes", "quotes");
	    CSS_PROPERTIES.put("richness", "richness");
	    CSS_PROPERTIES.put("right", "right");
	    CSS_PROPERTIES.put("size", "size");
	    CSS_PROPERTIES.put("speak", "speak");
	    CSS_PROPERTIES.put("speak-header", "speak-header");
	    CSS_PROPERTIES.put("speak-numeral", "speak-numeral");
	    CSS_PROPERTIES.put("speak-punctuation", "speak-punctuation");
	    CSS_PROPERTIES.put("speech-rate", "speech-rate");
	    CSS_PROPERTIES.put("stress", "stress");
	    CSS_PROPERTIES.put("table-layout", "table-layout");
	    CSS_PROPERTIES.put("text-align", "text-align");
	    CSS_PROPERTIES.put("text-decoration", "text-decoration");
	    CSS_PROPERTIES.put("text-indent", "text-indent");
	    CSS_PROPERTIES.put("text-shadow", "text-shadow");
	    CSS_PROPERTIES.put("text-transform", "text-transform");
	    CSS_PROPERTIES.put("top", "top");
	    CSS_PROPERTIES.put("unicode-bidi", "unicode-bidi");
	    CSS_PROPERTIES.put("vertical-align", "vertical-align");
	    CSS_PROPERTIES.put("visibility", "visibility");
	    CSS_PROPERTIES.put("voice-family", "voice-family");
	    CSS_PROPERTIES.put("volume", "volume");
	    CSS_PROPERTIES.put("white-space", "white-space");
	    CSS_PROPERTIES.put("widows", "widows");
	    CSS_PROPERTIES.put("width", "width");
	    CSS_PROPERTIES.put("word-spacing", "word-spacing");
	    CSS_PROPERTIES.put("z-index", "z-index");
	    
	    MARKUP_TAGS = new TreeMap<String, String>();
		MARKUP_TAGS.put("!--", "Specifies an HTML comment");
		MARKUP_TAGS.put("a", "Specifies a hyperlink");
		MARKUP_TAGS.put("abbr", "Specifies an abbreviation");
		MARKUP_TAGS.put("address", "Specifies an address element");
		MARKUP_TAGS.put("area", "Specifies an area inside an image map");
		MARKUP_TAGS.put("article", "Specifies an article (HTML 5)");
		MARKUP_TAGS.put("aside", "Specifies content aside from the page content (HTML 5)");
		MARKUP_TAGS.put("audio", "Specifies sound content (HTML 5)");
		MARKUP_TAGS.put("b", "Specifies bold text");
		MARKUP_TAGS.put("base", "Specifies a base URL for all the links in a page");
		MARKUP_TAGS.put("bb", "Specifies a user agent command (HTML 5)");
		MARKUP_TAGS.put("bdo", "Specifies the direction of text display");
		MARKUP_TAGS.put("big", "Specifies bigger text");
		MARKUP_TAGS.put("blockquote", "Specifies a long quotation");
		MARKUP_TAGS.put("body", "Specifies the body element");
		MARKUP_TAGS.put("br", "Inserts a single line break");
		MARKUP_TAGS.put("button", "Specifies a push button");
		MARKUP_TAGS.put("canvas", "Define graphics (HTML 5)");
		MARKUP_TAGS.put("caption", "Specifies a table caption");
		MARKUP_TAGS.put("capture", "Render the children of this element into the given variable (ESP)\nUsage: capture(var1) - will capture the rendered HTML into a new String variable named var1");
		MARKUP_TAGS.put("check", "Specifies an input of style: checkbox (ESP)");
		MARKUP_TAGS.put("cite", "Specifies a citation");
		MARKUP_TAGS.put("code", "Specifies computer code text");
		MARKUP_TAGS.put("col", "Specifies attributes for table columns ");
		MARKUP_TAGS.put("colgroup", "Specifies groups of table columns");
		MARKUP_TAGS.put("command", "Specifies a command (HTML 5)");
		MARKUP_TAGS.put("contentFor", "Render the children of this element into the yield element of the same name (ESP)\n" +
		                            "Usage: contentFor(\"name\") - will render its children into yield(\"name\")");
		MARKUP_TAGS.put("date", "Creates a formatted set of date fields for a form (ESP)");
		MARKUP_TAGS.put("datagrid", "Specifies data in a tree-list (HTML 5)");
		MARKUP_TAGS.put("datalist", "Specifies an \"autocomplete\" dropdown list (HTML 5)");
		MARKUP_TAGS.put("dd", "Specifies a definition description");
		MARKUP_TAGS.put("decimal", "Specifies an input that can only accept a decimal number (ESP)");
		MARKUP_TAGS.put("del", "Specifies deleted text");
		MARKUP_TAGS.put("details", "Specifies details of an element (HTML 5)");
		MARKUP_TAGS.put("dialog", "Specifies a dialog (conversation) (HTML 5)");
		MARKUP_TAGS.put("dfn", "Defines a definition term");
		MARKUP_TAGS.put("div", "Specifies a section in a document");
		MARKUP_TAGS.put("dl", "Specifies a definition list");
		MARKUP_TAGS.put("dt", "Specifies a definition term");
		MARKUP_TAGS.put("em", "Specifies emphasized text ");
		MARKUP_TAGS.put("embed", "Specifies external application or interactive content (HTML 5)");
		MARKUP_TAGS.put("errors", "Create a message block to show all errors contained in a model; does not render if empty (ESP)\n  Usage:\n  form(model) <- errors\n  errors(model)");
		MARKUP_TAGS.put("eventsource", "Specifies a target for events sent by a server (HTML 5)");
		MARKUP_TAGS.put("fieldset", "Specifies a fieldset");
		MARKUP_TAGS.put("fields", "Specifies a subsection of a form where the fields are for the given model (ESP) Usage:\n  fields(model)");
		MARKUP_TAGS.put("file", "Specifies an input of style: file (ESP)");
		MARKUP_TAGS.put("figure", "Specifies a group of media content, and their caption (HTML 5)");
		MARKUP_TAGS.put("footer", "Specifies a footer for a section or page (HTML 5)");
		MARKUP_TAGS.put("form", "Specifies a form ");
		MARKUP_TAGS.put("h1", "Specifies a heading level 1");
		MARKUP_TAGS.put("h2", "Specifies a heading level 2");
		MARKUP_TAGS.put("h3", "Specifies a heading level 3");
		MARKUP_TAGS.put("h4", "Specifies a heading level 4");
		MARKUP_TAGS.put("h5", "Specifies a heading level 5");
		MARKUP_TAGS.put("h6", "Specifies a heading level 6");
		MARKUP_TAGS.put("head", "Specifies information about the document");
		MARKUP_TAGS.put("header", "Specifies a header for a section or page (HTML 5)");
		MARKUP_TAGS.put("hidden", "Specifies a hidden form field (ESP)");
		MARKUP_TAGS.put("hr", "Specifies a horizontal rule");
		MARKUP_TAGS.put("html", "Specifies an html document");
		MARKUP_TAGS.put("i", "Specifies italic text");
		MARKUP_TAGS.put("iframe", "Specifies an inline sub window (frame)");
		MARKUP_TAGS.put("img", "Specifies an image");
		MARKUP_TAGS.put("input", "Specifies an input field");
		MARKUP_TAGS.put("ins", "Specifies inserted text");
		MARKUP_TAGS.put("kbd", "Specifies keyboard text");
		MARKUP_TAGS.put("label", "Specifies a label for a form control");
		MARKUP_TAGS.put("legend", "Specifies a title in a fieldset");
		MARKUP_TAGS.put("li", "Specifies a list item");
		MARKUP_TAGS.put("link", "Specifies a resource reference");
		MARKUP_TAGS.put("mark", "Specifies marked text (HTML 5)");
		MARKUP_TAGS.put("map", "Specifies an image map ");
		MARKUP_TAGS.put("menu", "Specifies a menu list");
		MARKUP_TAGS.put("messages", "Create a message block to show all errors, warnings, and notices in the flash; does not render if empty (ESP) Usage: messages");
		MARKUP_TAGS.put("meta", "Specifies meta information");
		MARKUP_TAGS.put("meter", "Specifies measurement within a predefined range (HTML 5)");
		MARKUP_TAGS.put("models", "Include and initialize the script models for this project (ESP) (may be called more than once)");
		MARKUP_TAGS.put("nav", "Specifies navigation links (HTML 5)");
		MARKUP_TAGS.put("noscript", "Specifies a noscript section");
		MARKUP_TAGS.put("number", "Specifies an input of style: text, that can only accept digits (ESP)");
		MARKUP_TAGS.put("object", "Specifies an embedded object");
		MARKUP_TAGS.put("ol", "Specifies an ordered list");
		MARKUP_TAGS.put("optgroup", "Specifies an option group");
		MARKUP_TAGS.put("option", "Specifies an option in a drop-down list");
		MARKUP_TAGS.put("options", "Specifies an option set belonging to a select element (ESP)");
		MARKUP_TAGS.put("output", "Specifies some types of output (HTML 5)");
		MARKUP_TAGS.put("p", "Specifies a paragraph");
		MARKUP_TAGS.put("param", "Specifies a parameter for an object");
		MARKUP_TAGS.put("password", "Specifies an input of style: password (ESP)");
		MARKUP_TAGS.put("pre", "Specifies preformatted text");
		MARKUP_TAGS.put("progress", "Specifies progress of a task of any kind (HTML 5)");
		MARKUP_TAGS.put("q", "Specifies a short quotation");
		MARKUP_TAGS.put("radio", "Specifies an input of style: radio (ESP)");
		MARKUP_TAGS.put("ruby", "Specifies a ruby annotation (used in East Asian typography) (HTML 5)");
		MARKUP_TAGS.put("reset", "Specifies an input of style: reset (ESP)");
		MARKUP_TAGS.put("rp", "Used for the benefit of browsers that don't support ruby annotations (HTML 5)");
		MARKUP_TAGS.put("rt", "Specifies the ruby text component of a ruby annotation. (HTML 5)");
		MARKUP_TAGS.put("samp", "Specifies sample computer code");
		MARKUP_TAGS.put("script", "Specifies a script");
		MARKUP_TAGS.put("section", "Specifies a section (HTML 5)");
		MARKUP_TAGS.put("select", "Specifies a selectable list");
		MARKUP_TAGS.put("small", "Specifies smaller text");
		MARKUP_TAGS.put("source", "Specifies media resources (HTML 5)");
		MARKUP_TAGS.put("span", "Specifies a section in a document");
		MARKUP_TAGS.put("strong", "Specifies strong text");
		MARKUP_TAGS.put("style", "Specifies a style definition");
		MARKUP_TAGS.put("sub", "Specifies subscripted text");
		MARKUP_TAGS.put("submit", "Specifies an input of style: submit (ESP)");
		MARKUP_TAGS.put("sup", "Specifies superscripted text");
		MARKUP_TAGS.put("table", "Specifies a table");
		MARKUP_TAGS.put("tbody", "Specifies a table body");
		MARKUP_TAGS.put("td", "Specifies a table cell");
		MARKUP_TAGS.put("text", "Specifies a multiline or single line text box (ESP)");
		MARKUP_TAGS.put("textArea", "Specifies a text area");
		MARKUP_TAGS.put("tfoot", "Specifies a table footer");
		MARKUP_TAGS.put("th", "Specifies a table header");
		MARKUP_TAGS.put("thead", "Specifies a table header");
		MARKUP_TAGS.put("time", "Specifies a date/time (HTML 5)");
		MARKUP_TAGS.put("title", "Specifies the document title");
		MARKUP_TAGS.put("tr", "Specifies a table row");
		MARKUP_TAGS.put("tt", "Specifies teletype, or monospaced, text");
		MARKUP_TAGS.put("ul", "Specifies an unordered list");
		MARKUP_TAGS.put("var", "Specifies a variable");
		MARKUP_TAGS.put("video", "Specifies a video (HTML 5)");
		MARKUP_TAGS.put("view", "Specifies a view of the given type (ESP)\nUsage: view<ViewName>(param1, param2, ...)");
		MARKUP_TAGS.put("yield", "Yields rendering (ESP)\nUsage: yield - will yield rendering to the current view's child\n" +
													   "       yield(view) - yield rendering to the given view\n" +
													   "       yield(\"name\") - render the content defined by contentFor(\"name\")");
		
	    DOM_EVENTS = new TreeSet<String>();
	    DOM_EVENTS.add("onblur");
	    DOM_EVENTS.add("onchange");
	    DOM_EVENTS.add("onclick");
	    DOM_EVENTS.add("ondblclick");
	    DOM_EVENTS.add("onerror");
	    DOM_EVENTS.add("onfocus");
	    DOM_EVENTS.add("onkeydown");
	    DOM_EVENTS.add("onkeypress");
	    DOM_EVENTS.add("onkeyup");
	    DOM_EVENTS.add("onmousedown");
	    DOM_EVENTS.add("onmousemove");
	    DOM_EVENTS.add("onmouseout");
	    DOM_EVENTS.add("onmouseover");
	    DOM_EVENTS.add("onmouseup");
	    DOM_EVENTS.add("onresize");
	    DOM_EVENTS.add("onselect");
	    DOM_EVENTS.add("onunload");
	    
		JAVA_KEYWORDS = new TreeSet<String>();
		JAVA_KEYWORDS.add("abstract");
		JAVA_KEYWORDS.add("assert");
		JAVA_KEYWORDS.add("boolean");
		JAVA_KEYWORDS.add("break");
		JAVA_KEYWORDS.add("byte");
		JAVA_KEYWORDS.add("case");
		JAVA_KEYWORDS.add("catch");
		JAVA_KEYWORDS.add("char");
		JAVA_KEYWORDS.add("class");
		JAVA_KEYWORDS.add("const");
		JAVA_KEYWORDS.add("continue");
		JAVA_KEYWORDS.add("default");
		JAVA_KEYWORDS.add("do");
		JAVA_KEYWORDS.add("double");
		JAVA_KEYWORDS.add("else");
		JAVA_KEYWORDS.add("enum");
		JAVA_KEYWORDS.add("extends");
		JAVA_KEYWORDS.add("false");
		JAVA_KEYWORDS.add("final");
		JAVA_KEYWORDS.add("finally");
		JAVA_KEYWORDS.add("float");
		JAVA_KEYWORDS.add("for");
		JAVA_KEYWORDS.add("goto");
		JAVA_KEYWORDS.add("if");
		JAVA_KEYWORDS.add("implements");
		JAVA_KEYWORDS.add("import");
		JAVA_KEYWORDS.add("instanceof");
		JAVA_KEYWORDS.add("int");
		JAVA_KEYWORDS.add("interface");
		JAVA_KEYWORDS.add("long");
		JAVA_KEYWORDS.add("native");
		JAVA_KEYWORDS.add("new");
		JAVA_KEYWORDS.add("null");
		JAVA_KEYWORDS.add("package");
		JAVA_KEYWORDS.add("private");
		JAVA_KEYWORDS.add("protected");
		JAVA_KEYWORDS.add("public");
		JAVA_KEYWORDS.add("return");
		JAVA_KEYWORDS.add("short");
		JAVA_KEYWORDS.add("static");
		JAVA_KEYWORDS.add("strictfp");
		JAVA_KEYWORDS.add("super");
		JAVA_KEYWORDS.add("switch");
		JAVA_KEYWORDS.add("synchronized");
		JAVA_KEYWORDS.add("this");
		JAVA_KEYWORDS.add("throw");
		JAVA_KEYWORDS.add("throws");
		JAVA_KEYWORDS.add("transient");
		JAVA_KEYWORDS.add("true");
		JAVA_KEYWORDS.add("try");
		JAVA_KEYWORDS.add("void");
		JAVA_KEYWORDS.add("volatile");
		JAVA_KEYWORDS.add("while");
		
		JS_KEYWORDS = new TreeSet<String>();
		JS_KEYWORDS.add("abstract");
		JS_KEYWORDS.add("boolean");
		JS_KEYWORDS.add("break");
		JS_KEYWORDS.add("byte");
		JS_KEYWORDS.add("case");
		JS_KEYWORDS.add("catch");
		JS_KEYWORDS.add("char");
		JS_KEYWORDS.add("class");
		JS_KEYWORDS.add("const");
		JS_KEYWORDS.add("continue");
		JS_KEYWORDS.add("debugger");
		JS_KEYWORDS.add("default");
		JS_KEYWORDS.add("delete");
		JS_KEYWORDS.add("do");
		JS_KEYWORDS.add("double");
		JS_KEYWORDS.add("else");
		JS_KEYWORDS.add("enum");
		JS_KEYWORDS.add("export");
		JS_KEYWORDS.add("extends");
		JS_KEYWORDS.add("false");
		JS_KEYWORDS.add("final");
		JS_KEYWORDS.add("finally");
		JS_KEYWORDS.add("float");
		JS_KEYWORDS.add("for");
		JS_KEYWORDS.add("function");
		JS_KEYWORDS.add("goto");
		JS_KEYWORDS.add("if");
		JS_KEYWORDS.add("implements");
		JS_KEYWORDS.add("import");
		JS_KEYWORDS.add("in");
		JS_KEYWORDS.add("instanceof");
		JS_KEYWORDS.add("int");
		JS_KEYWORDS.add("interface");
		JS_KEYWORDS.add("long");
		JS_KEYWORDS.add("native");
		JS_KEYWORDS.add("new");
		JS_KEYWORDS.add("null");
		JS_KEYWORDS.add("package");
		JS_KEYWORDS.add("private");
		JS_KEYWORDS.add("protected");
		JS_KEYWORDS.add("public");
		JS_KEYWORDS.add("return");
		JS_KEYWORDS.add("short");
		JS_KEYWORDS.add("static");
		JS_KEYWORDS.add("super");
		JS_KEYWORDS.add("switch");
		JS_KEYWORDS.add("synchronized");
		JS_KEYWORDS.add("this");
		JS_KEYWORDS.add("throw");
		JS_KEYWORDS.add("throws");
		JS_KEYWORDS.add("transient");
		JS_KEYWORDS.add("true");
		JS_KEYWORDS.add("try");
		JS_KEYWORDS.add("typeof");
		JS_KEYWORDS.add("var");
		JS_KEYWORDS.add("void");
		JS_KEYWORDS.add("volatile");
		JS_KEYWORDS.add("while");
		JS_KEYWORDS.add("with");
		
		
	}

	private Constants() {
		// class should not be instantiated
	}
	
}
