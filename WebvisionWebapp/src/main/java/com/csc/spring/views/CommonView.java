package com.csc.spring.views;

import com.csc.spring.applayoutfactory.AppLayoutFactory;
import com.csc.spring.util.CurrentUser;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class CommonView extends VerticalLayout {

    private AppLayoutFactory factory = new AppLayoutFactory();
    AppLayout appLayout = factory.getLayout(CurrentUser.currentUser.getRole());


    public CommonView(){}

    /**
     * Create the common view (ie navigation bar) for all other views
     */

    /*
    public CommonView() {
        if (CurrentUser.isEmpty()) {
            UI.getCurrent().navigate("");
        }
        System.out.println("Common View");
        System.out.println(CurrentUser.currentUser.getRole());
        this.appLayout = factory.getLayout(CurrentUser.currentUser.getRole());
    }

     */

    public void refreshView() {
        System.out.println(CurrentUser.currentUser.getRole());
        this.appLayout = factory.getLayout(CurrentUser.currentUser.getRole());
        //Image img = new Image("frontend/logo.png", "logo");
        //img.setWidth("35px");
        //img.setHeight("30px");
        //appLayout.setBranding(img);
    }



}
