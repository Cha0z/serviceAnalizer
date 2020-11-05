package com.borodin.service_anylizer.webpage.sections;


import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Layout;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


@Component
@UIScope
public class Content extends VerticalLayout {

    private Layout layout;

    @Autowired
    private Header header;

    @PostConstruct
    private void init(){
        setSpacing(false);
        setMargin(false);
    }


    public void replaceContent(Layout layout, String title) {
        if (this.layout == null) {
            addComponent(layout);
        } else {
            replaceComponent(this.layout, layout);
        }
        this.layout = layout;
        header.setLabel(title);
    }
}
