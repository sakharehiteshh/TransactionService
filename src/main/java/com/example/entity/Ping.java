package com.example.entity;

public class Ping {

	private String serverTime;

	public Ping() {
		
	}
	
	public Ping(String serverTime) {
		super();
		this.serverTime = serverTime;
	}

	public String getServerTime() {
		return serverTime;
	}

	public void setServerTime(String serverTime) {
		this.serverTime = serverTime;
	}
	
	
	
	
}
