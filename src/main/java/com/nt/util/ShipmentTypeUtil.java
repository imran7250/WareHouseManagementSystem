package com.nt.util;

import java.awt.Color;
import java.io.File;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.stereotype.Component;

@Component
public class ShipmentTypeUtil {
 
	// Generate Pie Chart
	public void generatPie(String path,List<Object[]> data) {
		//create DataSet
		DefaultPieDataset dataset=new DefaultPieDataset();
		//add data to DetaSet key(String),value(Double)
		for(Object[]ob:data) {
			dataset.setValue(String.valueOf(ob[0]),Double.valueOf(ob[1].toString()));
		}
		//b. create JFreeChart Object using ChartFactory
		JFreeChart chart=ChartFactory.createPieChart("SHIPMENT TYPE MODE COUNT",dataset);
		
		PiePlot plot=(PiePlot) chart.getPlot();
		plot.setBackgroundPaint(Color.white);
		//Convert JFreeChart into image
		try {
			ChartUtils.saveChartAsJPEG(new File(path+"/shipmentA.jpg"), chart, 500, 400);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	//Generate Bar Chart
	public void generateBar(String path,List<Object[]> data) {
		//create DataSet
		DefaultCategoryDataset dataset=new DefaultCategoryDataset();
		//add data to DetaSet value(Double), key(String),label(String)
	   for(Object[]ob:data) {
		   dataset.setValue(Double.valueOf(ob[1].toString()),String.valueOf(ob[0]),"");
		}
	   
		//b. create JFreeChart Object using ChartFactory
		JFreeChart chart=ChartFactory.createBarChart("SHIPMENT TYPE MODE COUNT","TYPE","COUNT",dataset);
		
		CategoryPlot plot=(CategoryPlot) chart.getPlot();
		plot.setBackgroundPaint(Color.LIGHT_GRAY);
		
		//Convert JFreeChart into image
		try {
			ChartUtils.saveChartAsJPEG(new File(path+"/shipmentB.jpg"), chart, 500, 400);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
}
