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
	public CompilerOperation() {
		super();
	}
	public CompilerOperation(String operationType, String javaCode) {
		super();
		this.operationType = operationType;
		this.javaCode = javaCode;
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
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("Main.java"), StandardCharsets.UTF_8))) {
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
			Process p = Runtime.getRuntime().exec("cmd /c javac Main.java");
			BufferedReader r=null;
	        StringBuilder line=new StringBuilder();
	        while(p.isAlive()){
	        
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
	            line.append(temp);
	        }
	        cres.setOutput(line.toString());
	        Runtime.getRuntime().exec("cmd /c del Main.java Main.class");
			p.destroy();
		} catch (IOException e) {
			e.printStackTrace();
			cres.setResult("Error");
			cres.setOutput("Can't handle request at this time!");
		}
		return cres;
	}
	
	public CompilerResponse compAndRun(){
		CompilerResponse cres=new CompilerResponse();
		try {
			Process p = Runtime.getRuntime().exec("cmd /c javac Main.java && java Main");
			BufferedReader r=null;
	        StringBuilder line=new StringBuilder();
	        while(p.isAlive()){
		        
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
	            line.append(temp);
	        }
	        cres.setOutput(line.toString());
	        Runtime.getRuntime().exec("cmd /c del Main.java Main.class");
			p.destroy();
		} catch (IOException e) {
			e.printStackTrace();
			cres.setResult("Error");
			cres.setOutput("Can't handle request at this time!");
		}
		return cres;
	}
}