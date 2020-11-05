package com.borodin.service_anylizer.webpage.sections;

import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.stereotype.Component;

@Component
@UIScope
public class Header extends HorizontalLayout {
    private Label label = new Label();

    public Header() {
        addComponent(label);
        label.addStyleName("title");
        label.addStyleName(ValoTheme.LABEL_H1);
    }

    public void setLabel(String title) {
       label.setValue(title);
    }
}
