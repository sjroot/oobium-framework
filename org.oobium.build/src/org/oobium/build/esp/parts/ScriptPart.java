package org.oobium.build.esp.parts;

import static org.oobium.build.esp.parts.EmbeddedJavaPart.embeddedJavaCheck;

import org.oobium.build.esp.EspPart;

public class ScriptPart extends EspPart {

	public ScriptPart(EspPart parent, int start, int end) {
		super(parent, Type.ScriptPart, start, end);
		parse();
	}

	protected int commentCheck(EspPart parent, char[] ca, int ix) {
		if(ix >= 0) {
			if(ix < end) {
				if(ca[ix] == '/' && (ix+1) < end && (ca[ix+1] == '*' || ca[ix+1] == '/')) {
					CommentPart comment = new CommentPart(parent, ix);
					ix = comment.getEnd();
				}
			}
		}
		return ix;
	}

	public boolean isSimple() {
		return !hasParts();
	}
	
	private void parse() {
		for(int s = start; s < end; s++) {
			s = embeddedJavaCheck(this, ca, s, end);
			s = commentCheck(this, ca, s);
		}
	}

}
