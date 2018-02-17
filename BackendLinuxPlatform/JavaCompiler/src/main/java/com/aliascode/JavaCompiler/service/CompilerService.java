package com.aliascode.JavaCompiler.service;

import org.springframework.stereotype.Service;

import com.aliascode.JavaCompiler.model.CompilerOperation;
import com.aliascode.JavaCompiler.model.CompilerResponse;

@Service
public class CompilerService {
	private CompilerOperation cops;
	public CompilerService(CompilerOperation cops){
//		System.out.println("Received Request");
		this.cops=cops;
	}
	public CompilerResponse service(){
		if(cops==null || cops.getJavaCode()==null){
			return new CompilerResponse("Error", "Invalid request!");
		}else if(cops.getJavaCode().isEmpty()){
			return new CompilerResponse("Error", "No java code to perform requested operation!");
		}else{
			if(cops.getOperationType().equals("compile")){
				return cops.compileCode();
			}else if(cops.getOperationType().equals("run")){
				return cops.runCode();
			}else{
				return new CompilerResponse("Error:", "Unsupported request!");
			}
		}
	}
}
////////////////////////////////////////////
