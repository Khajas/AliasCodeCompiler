package com.aliascode.JavaCompiler.model;

import org.springframework.stereotype.Repository;

@Repository
public class CompilerResponse {

	private String result;
	private String output;
	
	public CompilerResponse() {
		super();
	}
	public CompilerResponse(String result, String output) {
		super();
		this.result = result;
		this.output = output;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getOutput() {
		return output;
	}
	public void setOutput(String output) {
		this.output = output;
	}
}
/////////////////////////////////////////////////////
