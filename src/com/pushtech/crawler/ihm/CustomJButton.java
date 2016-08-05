package com.pushtech.crawler.ihm;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JLabel;

public class CustomJButton extends AbstractCustomJcomponent{
	
	CustomJButton(String label){
		this.component = new JButton(label);
		this.add(component);
	}
	
	CustomJButton(String label, Dimension dimension){
		this.component = new JButton(label);
		this.component.setPreferredSize(dimension);
		this.add(component);
	}

}
