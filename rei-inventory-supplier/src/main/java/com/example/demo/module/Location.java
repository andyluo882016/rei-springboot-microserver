package com.example.demo.module;

import org.springframework.stereotype.Component;

@Component
public class Location {

	 private String name;
	 private String state;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSatete() {
		return state;
	}
	public void setSatete(String satete) {
		this.state = satete;
	}
	public Location(String name, String satete) {
		super();
		this.name = name;
		this.state = satete;
	}
	public Location() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Location [name=" + name + ", satete=" + state + "]";
	}
	 
	 
}
