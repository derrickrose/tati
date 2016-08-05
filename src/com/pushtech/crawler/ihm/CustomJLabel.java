package com.pushtech.crawler.ihm;

import java.awt.Dimension;

import javax.swing.JLabel;

public class CustomJLabel extends AbstractCustomJcomponent{
	
	CustomJLabel(String label){
		this.component = new JLabel(label);
		this.add(component);
	}
	
	CustomJLabel(String label, Dimension dimension){
		this.component = new JLabel(label);
		component.setPreferredSize(dimension);
		this.add(component);
	}

}
