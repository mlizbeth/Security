package io.valhala.security;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToLongConverter;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

@Route("")
@Theme(value = Lumo.class, variant = Lumo.DARK)
@PageTitle("Makerspace")
public class MainView extends VerticalLayout {

	private static final long serialVersionUID = 3461787310452366610L;
	private Grid<Student> sGrid = new Grid<>(Student.class);
	private TextField filter = new TextField();
	private HorizontalLayout toolbar = new HorizontalLayout();
	private Button newStudent = new Button("Add");
	private InfoPanel infoPanel = new InfoPanel();
	@Autowired
	StudentRepository repo;
	
	public MainView() {
		setSizeFull();
		initComponents();
		initListeners();
		add(toolbar);
		add(sGrid);
	}
	
	
	@PostConstruct
	private void init() {
		sGrid.setItems(repo.findAll());
	}
	
	private void initComponents() {

		sGrid.setWidth("100%");
		sGrid.setColumns("firstName", "lastName", "sid", "uid");
		sGrid.getColumnByKey("sid").setHeader("Student ID");
		sGrid.getColumnByKey("uid").setHeader("Unique ID");
		
		toolbar.setWidth("100%");
		toolbar.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
		toolbar.setFlexGrow(2, filter);
		toolbar.add(filter, newStudent);

		filter.setPlaceholder("Search...");
		
		infoPanel.setWidth("640px");
		infoPanel.setHeight("480px");
	}
	
	private void initListeners() {
		newStudent.addClickListener(e -> {
			infoPanel.setItem(new Student());
			infoPanel.open();
		});
		
		sGrid.asSingleSelect().addValueChangeListener(e -> {
			if(e.getValue() != null) {
				infoPanel.open();
				infoPanel.setItem(e.getValue());
			}
			else {
				infoPanel.close();
				infoPanel.setItem(null);
			}
		});
	}
	
	private class InfoPanel extends Dialog {

		private static final long serialVersionUID = -1794175141402670926L;
		private Student student;
		private TextField uidField, sidField, firstNameField, lastNameField;
		private Button save, delete, cancel;
		private FormLayout layout;
		private Binder<Student> binder = new Binder<>(Student.class);
		private HorizontalLayout bottom;
		
		/*
		 * TODO Fields
		 * firstName, lastName, email, major, graduation, gender - from colleague.
		 */
		
		public InfoPanel() {
			initComponents();
			binder.bindInstanceFields(this);
		}

		private void initComponents() {
			layout = new FormLayout();
			add(layout);
			layout.setSizeFull();
			
			uidField = new TextField("Unique ID");
			uidField.setEnabled(false);
			layout.add(uidField);
			
			sidField = new TextField("Student ID");
			layout.add(sidField);
			
			firstNameField = new TextField("First Name");
			layout.add(firstNameField);
			
			lastNameField = new TextField("Last Name");
			layout.add(lastNameField);
			
			bottom = new HorizontalLayout();
			layout.add(bottom);
			
			save = new Button("Save");
			delete = new Button("Delete");
			cancel = new Button("Cancel");
			
			save.addClickListener(e -> this.save());
			delete.addClickListener(e -> this.delete());
			cancel.addClickListener(e -> this.cancel());
			
			bottom.add(save, delete, cancel);
			
			binder.forMemberField(uidField).withConverter(new StringToLongConverter(uidField.getValue())).bind("uid");
			binder.forMemberField(sidField).bind("sid");
			binder.forMemberField(firstNameField).bind("firstName");
			binder.forMemberField(lastNameField).bind("lastName");
			//binder.forMemberField(firstNameField).asRequired().withValidator((string -> string != null && !string.isEmpty()), "Values cannot be empty").bind("firstName");
			
		}
		
		public void setItem(Student student) {
			this.student = student;
			try {
				binder.setBean(student);
			} catch(NullPointerException e) {}
		}
		
		private void delete() {
			repo.delete(student);
			this.close();
			sGrid.setItems(repo.findAll());
		}
		
		private void save() {
			repo.save(student);
			this.close();
			sGrid.setItems(repo.findAll());
		}
		
		private void cancel() {
			setItem(null);
			this.close();
		}
	}
}