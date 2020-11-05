package com.borodin.service_anylizer.webpage.view.page;

import com.borodin.service_anylizer.manager.LogAnalyzer;
import com.borodin.service_anylizer.webpage.helper.CheckWebStatusHelper;
import com.borodin.service_anylizer.webpage.model.ServiceStatusForGrid;
import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.*;
import com.vaadin.annotations.Widgetset;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;

import com.vaadin.ui.Label;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
@UIScope
@Slf4j
public class ServiceStatusPage extends VerticalLayout {

    @Autowired
    private LogAnalyzer logAnalyzer;

    @Value("${analyzer.application-name}")
    private String applicationName;

    private HorizontalLayout horizontalLayout = new HorizontalLayout();


    private HorizontalLayout buttons = new HorizontalLayout();

    private Button chartYear = new Button("Year");
    private Button chartMonth = new Button("Month");
    private Button chartDay = new Button("Day");

    private Grid<ServiceStatusForGrid> grid = new Grid<>(ServiceStatusForGrid.class);
    private Chart chart;
    @Autowired
    private CheckWebStatusHelper helper;


    @PostConstruct
    private void init() {
        chartConfiguration();
        grid.setColumns("applicationName", "status", "issue");
        horizontalLayout.addComponents(grid, chart);
        buttons.addComponents(chartYear, chartMonth, chartDay);
        addComponents(horizontalLayout, buttons);

        grid.asSingleSelect().addValueChangeListener(e -> {

        });
        chartYear.addClickListener(e -> updateChartForYear());
        chartMonth.addClickListener(e -> updateChartForMonth());
        chartDay.addClickListener(e -> updateChartForDay());
    }

    private void chartConfiguration() {
        chart = new Chart();
        Configuration conf = chart.getConfiguration();
        conf.setTitle("Service traffic");
        conf.getChart().setType(ChartType.AREASPLINE);
        conf.getLegend().setEnabled(false);
        updateChartForMonth();
//        List<Integer> list = logAnalyzer.getNumberOfVisitsForYear();
//        log.info("{}", list);


    }

    private void updateChartForYear() {
        Configuration conf = chart.getConfiguration();
        conf.removexAxes();
        conf.removeyAxes();
        ListSeries series = new ListSeries("Traffic");
        logAnalyzer.getNumberOfVisitsForYear().forEach(series::addData);
        conf.setSeries(series);

        XAxis xaxis = new XAxis();
        xaxis.setCategories("January", "February", "March",
                "April", "May", "June",
                "May", "June", "July", "August", "September", "October", "November", "December");
        xaxis.setTitle("Month of year");
        conf.addxAxis(xaxis);


        YAxis yaxis = new YAxis();
        yaxis.setTitle("Traffic");
        yaxis.getLabels().setFormatter(
                "function() {return Math.floor(this.value);}");
        yaxis.getLabels().setStep(2);
        conf.addyAxis(yaxis);

        chart.drawChart();
        update();
    }

    private void updateChartForMonth() {
        Configuration conf = chart.getConfiguration();
        conf.removexAxes();
        conf.removeyAxes();
        DataSeries dataSeries = new DataSeries("Traffic");

        ListSeries series = new ListSeries("Traffic");
        logAnalyzer.getNumberOfVisitsForMonth().forEach(series::addData);

        conf.setSeries(series);

        LocalDate toDate = LocalDate.now();
        LocalDate fromDate = toDate.minus(1, ChronoUnit.MONTHS);

        XAxis xaxis = new XAxis();
        while (fromDate.getDayOfYear() <= toDate.getDayOfYear()) {

            xaxis.addCategory(String.valueOf(fromDate.getDayOfMonth()));
            fromDate = fromDate.plusDays(1);
        }

        xaxis.setTitle("Days during month");
        conf.addxAxis(xaxis);

// Set the Y axis title
        YAxis yaxis = new YAxis();
        yaxis.setTitle("Traffic");
        yaxis.getLabels().setFormatter(
                "function() {return Math.floor(this.value);}");
        yaxis.getLabels().setStep(2);
        conf.addyAxis(yaxis);

        chart.drawChart();

        update();
    }

    private void updateChartForDay() {

        Configuration conf = chart.getConfiguration();
        conf.removexAxes();
        conf.removeyAxes();


        ListSeries series = new ListSeries("Traffic");
        logAnalyzer.getNumberOfVisitsForDay().forEach(series::addData);

        conf.setSeries(series);

        LocalDateTime toDate = LocalDateTime.now();
        LocalDateTime fromDate = toDate.minus(1, ChronoUnit.DAYS);

        XAxis xaxis = new XAxis();

        while (fromDate.getDayOfYear() <= toDate.getDayOfYear()) {

            xaxis.addCategory(String.valueOf(fromDate.getHour()));
            fromDate = fromDate.plusHours(1);
        }

        xaxis.setTitle("Days during one day");
        conf.addxAxis(xaxis);

// Set the Y axis title
        YAxis yaxis = new YAxis();
        yaxis.setTitle("Traffic");
        yaxis.getLabels().setFormatter(
                "function() {return Math.floor(this.value);}");
        yaxis.getLabels().setStep(2);
        conf.addyAxis(yaxis);

        chart.drawChart();
        update();
    }


    public void update() {
        ServiceStatusForGrid serviceStatusForGrid = new ServiceStatusForGrid();
        serviceStatusForGrid.setStatus(helper.checkStatus());
        serviceStatusForGrid.setApplicationName(applicationName);
        serviceStatusForGrid.setIssue(logAnalyzer.analyzeIfDosAttackIsPresent()?"Dos":""+ (logAnalyzer.analyzeIfDdosAttackIsPresent()?" DDos":""));

        grid.setItems(serviceStatusForGrid);
    }


}
