package com.aliascode.JavaCompiler.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

import org.springframework.stereotype.Repository;

/**
 * Perform the Compiler tasks here
 */

@Repository
public class CompilerOperation{

	private String operationType;
	private String javaCode;
	private String args;
	
	public CompilerOperation() {
		super();
	}
	public CompilerOperation(String operationType, String javaCode, String args) {
		super();
		this.operationType = operationType;
		this.javaCode = javaCode;
		this.args=args;
	}
	public String getOperationType() {
		return operationType;
	}
	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}
	public String getJavaCode() {
		return javaCode;
	}
	public void setJavaCode(String javaCode) {
		this.javaCode = javaCode;
	}
	public String getArgs(){
		return this.args;
	}
	public void setArgs(String args){
		this.args=args;
	}	
	// Write methods for compile and run
	public CompilerResponse compileCode(){
		if(this.writeToFile())
			return this.comp();
		return new CompilerResponse("Error","Can't handle request at this time!");
	}
	
	public CompilerResponse runCode(){
		if(this.writeToFile())
			return this.compAndRun();
		return new CompilerResponse("Error","Can't handle request at this time!");
	}
	
	public boolean writeToFile(){
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("/tmp/Main.java"), StandardCharsets.UTF_8))) {
		    writer.write(this.javaCode);
		    return true;
		} 
		catch (IOException ex) {
			ex.printStackTrace();
			return false;
		}
	}
	
	public CompilerResponse comp(){
		CompilerResponse cres=new CompilerResponse();
		try {
			Process p = Runtime.getRuntime().exec("javac /tmp/Main.java");
			BufferedReader r=null;
		        StringBuilder line=new StringBuilder();
		        while(p.isAlive()){	// Waiting indefinitely for the process to terminate
	        
	        	}
		        if(p.exitValue()==0){
		        	r = new BufferedReader(new InputStreamReader(p.getInputStream()));
	        		cres.setResult("Success");
	        		line.append("Compiled Successfully!");
		        }else{
				r = new BufferedReader(new InputStreamReader(p.getErrorStream()));
				cres.setResult("Error!");
		        }
	        	while (r!=null) {
	           		String temp = r.readLine();
				if (temp == null) { break; }
	            		line.append(temp+"\n");
	        	}
		        cres.setOutput(line.toString());
		        if(this.operationType.equals("compile"))
				Runtime.getRuntime().exec("rm /tmp/Main.java /tmp/Main.class");
			p.destroy();
		} catch (IOException e) {
//			e.printStackTrace();
			cres.setResult("Error!");
			cres.setOutput(e.getMessage());
		}
		return cres;
	}
	
	public CompilerResponse compAndRun(){
		CompilerResponse cres=new CompilerResponse();
		try {
			CompilerResponse crs=this.comp();
			if(!crs.getResult().equals("Success"))
				return crs;
			Process p=Runtime.getRuntime().exec("chmod 777 /tmp/Main*");	// Check if 777 is necessary
			p=Runtime.getRuntime().exec("java -Djava.security.manager -Djava.security.policy==~/security/sec.policy -cp /tmp Main "+args);
			BufferedReader r=null;
	        	StringBuilder line=new StringBuilder();
	        	while(p.isAlive()){	// Waiting indefinately for the process to terminate
		        
	        	}
	        	if(p.exitValue()==0){
	        		r = new BufferedReader(new InputStreamReader(p.getInputStream()));
		        	cres.setResult("Success");
		        }else{
				r = new BufferedReader(new InputStreamReader(p.getErrorStream()));
				cres.setResult("Error!");
	        	}
		        while (r!=null) {
		            String temp = r.readLine();
	        	    if (temp == null) { break; }
		            line.append(temp+"\n");
		        }
		        cres.setOutput(line.toString());
		        Runtime.getRuntime().exec("rm /tmp/Main.java /tmp/Main.class");
			p.destroy();
		} catch (IOException e) {
//			e.printStackTrace();
			cres.setResult("Error!");
			cres.setOutput(e.getMessage());
		}
		return cres;
	}
}
////////////////////////////////////////////////////////////////
