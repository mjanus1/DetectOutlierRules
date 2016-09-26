package com.mariusz.janus.DetectOutlierRules.web;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;

import lombok.Getter;
import lombok.Setter;

@ManagedBean
@ViewScoped
public class BeanTest {
	
	@Getter @Setter private int count;
	@Getter @Setter private String test;
	
	
	
	public BeanTest() {
		test = "";
	}

	public void increment() {
	count++;
	}
	
	public void listenerTest(ValueChangeEvent e) {
		String s;
		s = (String) e.getNewValue();
		if(s!=null) {
			test = s;
		}
		else
			test = "";
	}
}
