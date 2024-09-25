package com.example.Util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.example.entity.Request;

public class CommonUtil {
	
	public static String getFileAsString(String fileName) throws IOException {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());
        String content = new String(Files.readAllBytes(file.toPath()));
        return content;
    }
	
	public static Request getReqObjFromFile(String fileName) throws IOException {
		String reqLoadString = getFileAsString(fileName);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(reqLoadString, Request.class);
		
	}
	
	public static String createURLWithPort(String uri,int port) {
        return "http://localhost:" + port + uri;
	}

}
