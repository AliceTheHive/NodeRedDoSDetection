package com.google.javascript.jscomp;

import com.google.javascript.rhino.Node;

/**
 * Since the compilerpass Normalize is package visible, this class exists to make it useable in this project.
 */
public class AccessToControlFlowAnalysis  {

	public static ControlFlowGraph<Node> processControllFlowAnalysis(Compiler compiler) {
		ControlFlowAnalysis controlFlowAnalysis = new ControlFlowAnalysis(compiler, true, false);
		compiler.process(controlFlowAnalysis);
		return controlFlowAnalysis.getCfg();
	}
}
