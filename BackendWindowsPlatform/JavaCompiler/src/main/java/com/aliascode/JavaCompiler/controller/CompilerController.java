package com.aliascode.JavaCompiler.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.aliascode.JavaCompiler.model.CompilerOperation;
import com.aliascode.JavaCompiler.model.CompilerResponse;
import com.aliascode.JavaCompiler.service.CompilerService;

@RestController
@RequestMapping("/compile")
public class CompilerController {

	@CrossOrigin(origins = "*")
	@RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<CompilerResponse> greeting(@RequestBody CompilerOperation cops) {
		System.out.println("Reqest received!");
    	return new ResponseEntity<CompilerResponse>(new CompilerService(cops).service(), HttpStatus.OK);
    }

	@CrossOrigin(origins = "*")
	@RequestMapping(method = RequestMethod.GET)
	public String get(){
		return "Hi";
	}
}