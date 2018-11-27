package io.valhala.security;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("")
@PageTitle("Welcome!")
public class MainView extends VerticalLayout {
	
	public MainView() {
		Button b = new Button("Hello World");
		add(b);
	}

}
