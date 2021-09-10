package com.csc.spring.views;

import com.csc.spring.models.Company;
import com.csc.spring.models.User;
import com.csc.spring.models.UserType;
import com.csc.spring.service.CompanyService;
import com.csc.spring.service.UserService;
import com.csc.spring.util.CurrentUser;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Route(RegistrationView.ROUTE)
@Theme(value = Lumo.class, variant = Lumo.DARK)
public class RegistrationView extends VerticalLayout {

    public static final String ROUTE = "register";  // URI to use

    @Autowired
    UserService userService;    // Injected user service to manipulate database

    @Autowired
    CompanyService companyService;  // Injected company service to manipulate database

    /**
     * Create registration form
     */
    public RegistrationView() {
        VerticalLayout layout = new VerticalLayout();
        H2 head = new H2("Registration");   // Title

        // Registration form
        TextField firstName = new TextField("First Name");
        TextField lastName = new TextField("Last Name");
        TextField email = new TextField("E-mail");
        PasswordField password = new PasswordField("Password");
        Button register = new Button("Register");
        ComboBox<String> userType = new ComboBox<>("userType");
        TextField company = new TextField("Company");
        userType.setItems(getItems());
        company.setVisible(false);

        // Mandatory to avoid null
        firstName.isRequired();
        lastName.isRequired();
        email.isRequired();
        password.isRequired();

        userType.addValueChangeListener(event -> {
            displayCompanyField(event.getValue(), company);
        });
        // Button listener
        register.addClickListener( e -> {
            register(e, firstName, lastName, email, company, password, userType);
        });

        layout.add(
                head,
                firstName,
                lastName,
                email,
                password,
                userType,
                company,
                register
        );

        layout.setAlignItems(Alignment.CENTER);

        add(
                layout
        );
    }

    /**
     * @return List to be used in the combo box.
     */
    private List<String> getItems() {
        List<String> items = new ArrayList<>();
        items.add("APPLICANT");
        items.add("INTERVIEWER");
        items.add("COORDINATOR");
        return items;
    }

    /**
     * Register user on click of register button.
     * @param event
     * @param firstName
     * @param lastName
     * @param email
     * @param company
     * @param password
     * @param userType
     */
    private void register(ClickEvent event, TextField firstName, TextField lastName,
                          TextField email, TextField company, PasswordField password, ComboBox<String> userType) {
        String fm = firstName.getValue();
        String lm = lastName.getValue();
        String em = email.getValue();
        String pw = password.getValue();
        String ut = userType.getValue(); // do something with this (drop down value)
        System.out.println("userType.getvalue()");
        System.out.println(ut);
        if (UserType.getType(ut) == UserType.APPLICANT){
            System.out.println("applicant 120");
            registerUser(fm, lm, em, pw, ut);
        } else if (UserType.getType(ut) == UserType.COORDINATOR || UserType.getType(ut) == UserType.INTERVIEWER) {
            String comp = company.getValue();
            registerUser(fm, lm, em, pw, ut, comp);
            System.out.println("119 ran");
        }

        CurrentUser.setNewUserType(UserType.getType(ut));
        UI.getCurrent().navigate(HomeView.ROUTE);
    }

    /**
     * If its not an applicant diplay text field.
     * @param type
     * @param company
     */
    private void displayCompanyField(String type, TextField company) {
        if ( !(UserType.getType(type) == UserType.APPLICANT)) {
            company.setVisible(true);
        }
    }

    /**
     * If company exists in our system, add the user to the list, otherwise create a new company instance and
     * save it in the database.
     * @param name
     * @param user
     */
    private void registerUserToCompany(String name, User user) {
        System.out.println("company name");
        System.out.println(name);
        if (companyService.companyExists(name)) {
            Company comp = companyService.addUser(user, name);
            CurrentUser.setCompany(comp);
        } else {
            Company comp = companyService.registerCompany(name, user);
            CurrentUser.setCompany(comp);
        }
    }

    /**
     * Create a new user given user information, this method is to be used when the user to register is an applicant.
     * @param firstName
     * @param lastName
     * @param email
     * @param password
     */
    private void registerUser(String firstName, String lastName, String email, String password, String role) {
       User newUser =  userService.registerUser(firstName, lastName, email, password, role);
        newUser.setRole("APPLICANT");
       CurrentUser.setCurrentUser(newUser);
    }

    /**
     * Create a new user given user information, this method is to be used when the user to register is not an applicant.
     * @param firstName
     * @param lastName
     * @param email
     * @param password
     * @param role
     * @param companyName
     */
    private void registerUser(String firstName, String lastName, String email, String password, String role,  String companyName) {
        User newUser = userService.registerUser(firstName, lastName, email, password, role);
        CurrentUser.setCurrentUser(newUser);
        registerUserToCompany(companyName, newUser);
    }
}
