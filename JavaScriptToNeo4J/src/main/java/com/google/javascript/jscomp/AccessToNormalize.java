/**
 * Created by Per on 05.07.2015.
 */
package com.google.javascript.jscomp;

import com.google.javascript.jscomp.Compiler;
import com.google.javascript.jscomp.Normalize;

public class AccessToNormalize {

	public static Normalize getNormalizePass(Compiler compiler) {
		return new Normalize(compiler, false);
	}

	public static void processNormalize(Compiler compiler) {
		Normalize normalize = new Normalize(compiler, false);
		compiler.process(normalize);
	}
}
