package com.borodin.service_anylizer.webpage.sections;


import com.borodin.service_anylizer.webpage.ApplicationView;
import com.vaadin.server.ClassResource;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;

@Component
@UIScope
public class Navigation extends VerticalLayout implements ApplicationContextAware {

    private Accordion accordion;
    private VerticalLayout layout = new VerticalLayout();


    @Autowired
    private Content content;

    @Autowired
    private ApplicationView view;

    private ApplicationContext context;

    public Navigation() {

        configNavigation();

        Label title = new Label("Service analyzer");

        title.addStyleName("white-text");

        title.addStyleName(ValoTheme.LABEL_H3);
        addComponent(title);
        setComponentAlignment(title, Alignment.MIDDLE_CENTER);
        setExpandRatio(title, 0);

        Image profileImage = new Image(null, new ClassResource("static/profile.png"));
        addComponent(profileImage);
        setComponentAlignment(profileImage, Alignment.MIDDLE_CENTER);
        setExpandRatio(profileImage, 0);

        Label name = new Label("Service Manager");
        addComponent(name);
        setComponentAlignment(name, Alignment.MIDDLE_CENTER);
        setExpandRatio(name, 0);

    }


    private void configNavigation() {
        setSpacing(false);
        setMargin(false);
        setWidth("16em");
        setHeight("-1px");
    }


    @PostConstruct
    public void addButton() {
        layout = new VerticalLayout();
        addComponent(layout);
        String viewTitle = view.getTitle();
        Button button = new Button(viewTitle);
        button.setStyleName(ValoTheme.BUTTON_BORDERLESS);
        button.setWidth("100%");

        layout.addComponent(button);

        button.addClickListener(e -> content.replaceContent(view.getView(), view.getTitle()));
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}
