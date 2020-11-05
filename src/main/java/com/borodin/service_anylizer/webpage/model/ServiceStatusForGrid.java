package com.borodin.service_anylizer.webpage.model;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

@Data
public class ServiceStatusForGrid {


    private String applicationName;

    private String status;

    private String issue;

}
