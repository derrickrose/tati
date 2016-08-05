package com.pushtech.crawler.ihm;

import java.awt.Dimension;

import javax.swing.JTextField;

public class CustomJTextField extends AbstractCustomJcomponent{
	
	CustomJTextField(){
		this.component = new JTextField();
		this.add(component);
	}
	
	
	CustomJTextField(String label){
		this.component = new JTextField(label);
		this.add(component);
	}
	
	CustomJTextField(String label, Dimension dimension){
		this.component = new JTextField(label);
		component.setPreferredSize(dimension);
		this.add(component);
	}

}
