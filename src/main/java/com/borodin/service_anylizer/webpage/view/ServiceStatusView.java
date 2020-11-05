package com.borodin.service_anylizer.webpage.view;

import com.borodin.service_anylizer.webpage.ApplicationView;
import com.borodin.service_anylizer.webpage.view.page.ServiceStatusPage;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Layout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@UIScope
public class ServiceStatusView implements ApplicationView {

    @Autowired
    private ServiceStatusPage content;

    @Override
    public String getTitle() {
        return "Service status";
    }

    @Override
    public Layout getView() {
        content.update();
        return content;
    }
}
