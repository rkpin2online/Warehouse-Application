package com.rk.util;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.stereotype.Component;

@Component
public class ShipmentTypeUtil {

	public void generatePieChart(String location,List<Object[]> list) {

		// a. Create DataSet and read List<Object[]> into DataSet values
		DefaultPieDataset dataset = new DefaultPieDataset();
		for(Object[] ob:list) {
			//Key - Shipment Mode, value - count value
			dataset.setValue(ob[0].toString(), Double.valueOf(ob[1].toString()));
		}

		// b. Convert DataSet data into JFreeChart object using ChartFactory class
		JFreeChart chart = ChartFactory.createPieChart("SHIPMENT TYPE MODE", dataset);

		// c. Convert JFreeChart object into one Image using ChartUtil class
		try {
			ChartUtils.saveChartAsJPEG(new File(location+"/shipmentA.jpg"), chart, 400, 300);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void generateBarChart(String location,List<Object[]> list) {
		// a. Create DataSet and read List<Object[]> into DataSet values
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		
		for(Object[] ob:list) {
			// value, key, display label
			dataset.setValue(Double.valueOf(ob[1].toString()), ob[0].toString(), "");
		}
		
		// b. Convert DataSet data into JFreeChart object using ChartFactory class
		JFreeChart chart = ChartFactory.createBarChart("SHIPMENT TYPE MODE", "SHIPMENT TYPES", "COUNT", dataset);
		
		// c. Convert JFreeChart object into one Image using ChartUtil class
		try {
			ChartUtils.saveChartAsJPEG(new File(location+"/shipmentB.jpg"), chart, 500, 350);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
