package com.csc.spring.views;

import com.csc.spring.models.User;
import com.csc.spring.service.UserService;
import com.csc.spring.util.CurrentUser;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;

@Route("")
@Theme(value = Lumo.class, variant = Lumo.DARK)
public class MainView extends VerticalLayout {

    @Autowired
    private UserService userService;

    /**
     * set up the login screen
     */
    public MainView() {
        LoginOverlay login = new LoginOverlay();
        login.setOpened(true);
        LoginI18n i18n = LoginI18n.createDefault();
        i18n.setHeader(new LoginI18n.Header());
        i18n.getHeader().setTitle("Webvision");
        i18n.getHeader().setDescription("Please login, if you don't have an account you will be redirected to finish registration.");
        i18n.getForm().setUsername("Email");
        i18n.getForm().setPassword("Password");
        login.setI18n(i18n);
        login.addLoginListener(event -> {
           String email = event.getUsername();
           User user = userService.findByEmail(email);
           if (user == null) {
               login.close();
               UI.getCurrent().navigate(RegistrationView.ROUTE);
           } else {
               String password = event.getPassword();
               boolean isAuthenticated = password.equals(user.getPassword());
               if (isAuthenticated) {
                   CurrentUser.setCurrentUser(user);
                   login.close();
                   UI.getCurrent().navigate(HomeView.ROUTE);
               }
               login.setError(true);
           }
        });
        add(login);
    }

}
