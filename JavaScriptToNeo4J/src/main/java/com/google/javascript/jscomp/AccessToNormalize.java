package com.google.javascript.jscomp;

/**
 * Since the compilerpass Normalize is package visible, this class exists to make it useable in this project.
 */
public class AccessToNormalize {

	public static Normalize getNormalizePass(Compiler compiler) {
		return new Normalize(compiler, false);
	}

	public static void processNormalize(Compiler compiler) {
		Normalize normalize = new Normalize(compiler, false);
		compiler.process(normalize);
	}
}
