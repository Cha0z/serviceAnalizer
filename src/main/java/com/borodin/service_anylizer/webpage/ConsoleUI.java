package com.borodin.service_anylizer.webpage;


import com.borodin.service_anylizer.webpage.sections.Content;
import com.borodin.service_anylizer.webpage.sections.Header;
import com.borodin.service_anylizer.webpage.sections.Navigation;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import org.springframework.beans.factory.annotation.Autowired;

@SpringUI
@UIScope
@Theme("mytheme")
public class ConsoleUI extends UI {

    @Autowired
    private Content content;
    @Autowired
    private Navigation navig;
    @Autowired
    private Header header;

    @Override
    protected void init(VaadinRequest vaadinRequest) {


        HorizontalLayout layout = new HorizontalLayout();

        VerticalLayout rightSide = new VerticalLayout();
        rightSide.addComponents(header, content);

        Panel rightSidePanel = new Panel(rightSide);
        rightSidePanel.setSizeFull();
        rightSidePanel.setStyleName(ValoTheme.PANEL_SCROLL_INDICATOR);
        rightSidePanel.addStyleName(ValoTheme.PANEL_BORDERLESS);

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.addComponent(navig);
        verticalLayout.setExpandRatio(navig,0);
        verticalLayout.setMargin(false);
        verticalLayout.setWidth("-1px");
        verticalLayout.setHeight("100%");

        layout.addComponents(verticalLayout, rightSidePanel);
        layout.setExpandRatio(verticalLayout,0);
        layout.setExpandRatio(rightSidePanel,1);
        layout.setSizeFull();

        verticalLayout.setStyleName("navigation");
        setContent(layout);
        content.setMargin(false);
        content.setSizeFull();
    }

}
