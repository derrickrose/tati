package com.pushtech.crawler.ihm;

import javax.swing.JComponent;
import javax.swing.JPanel;

// in order to manage component size, we add a component onto his proper panel
public abstract class AbstractCustomJcomponent extends JPanel{
	
	protected  JComponent component;

	public JComponent getComponent() {
		return component;
	}

	public void setComponent(JComponent component) {
		this.component = component;
	}

}
