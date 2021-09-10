package com.csc.spring.views;


import com.csc.spring.models.Notifications;
import com.csc.spring.service.NotificationService;
import com.csc.spring.util.CurrentUser;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.contextmenu.GridContextMenu;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

///THIS VIEW WILL WORK BUT SEEMS LIKE IT MAY THROW A NULL EXCEPTION FOR SOME REASON

@Route(HomeView.ROUTE)
@Theme(value = Lumo.class, variant =Lumo.DARK)
public class HomeView extends CommonView implements BeforeEnterObserver {

    private VerticalLayout layout = new VerticalLayout();
    private Grid<Notifications> messages = new Grid<>(Notifications.class);

    public static final String ROUTE = "home";


    private NotificationService notificationService;

    /**
     * Create the home view
     * @param service
     */
    @Autowired
    public HomeView(NotificationService service) {
        super();
        this.notificationService = service;
        setUpView();
    }

    public void setUpView() {
        createGrid();
        createVertLayout();
        layout.getStyle().set("background", "url(frontend/loginbackground.jpg)");
        layout.setSizeFull();
        appLayout.setContent(layout);
        add(appLayout);
    }

    /* All methods below are helper methods for building the view bit by bit*/

    private void createVertLayout(){

        HorizontalLayout temp = new HorizontalLayout();
        H3 notification = new H3("Notifications");
        temp.add(notification);
        Image img = new Image("frontend/temp.png", "Webvision");
        notification.setWidth("50%");
        layout.add(img, temp, messages);
        layout.setHorizontalComponentAlignment(Alignment.CENTER, temp);
        layout.setHorizontalComponentAlignment(Alignment.CENTER, img);
    }

    private void createGrid(){

        List<Notifications> notifications = notificationService.getNotifications(CurrentUser.getCurrentUserId());

        messages.setItems(notifications);
        messages.setColumns("date", "message");
        messages.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        messages.addItemClickListener(event ->
                Notification.show("Value: " + event.getItem()));
        GridContextMenu<Notifications> contextMenu = messages.addContextMenu();
        Button doc = new Button("View Documents");
        doc.addClickListener(e -> {
            Notification.show("Success");
        });
        contextMenu.addItem(doc);
        messages.setHeight("300px");
    }


    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        //setUpView();
    }
}
