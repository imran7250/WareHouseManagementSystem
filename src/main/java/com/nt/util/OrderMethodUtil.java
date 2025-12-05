package com.nt.util;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.stereotype.Component;

@Component
public class OrderMethodUtil {

	//create PI Chart
	public void generatePie(String path, List<Object[]> data) {
		
		//a.setValue(key[String],value[double])
	    DefaultPieDataset dataset = new DefaultPieDataset();
	    for (Object[] ob : data) {
	        dataset.setValue(String.valueOf(ob[0]), Double.valueOf(ob[1].toString()));
	    }
	    //b. create JFree charts with dataset and other details
	    JFreeChart chart = ChartFactory.createPieChart3D("OrderMethod TYPE AND COUNT", dataset);
	    
	    //c. read chartArea object
	    PiePlot plot=(PiePlot)chart.getPlot();
	    PieSectionLabelGenerator gen =
	    	    new StandardPieSectionLabelGenerator(
	    	        "{0} : {1} ({2})", new DecimalFormat("0"), new DecimalFormat("0%")
	    	    );
	    	plot.setLabelGenerator(gen);
	
	    try {
	        File dir = new File(path);
	        if (!dir.exists()) {    
	            dir.mkdirs();   // ✅ create /images folder if missing
	        }

	        File out = new File(dir, "OrderMethodA.jpg");
	        ChartUtils.saveChartAsJPEG(out, chart, 500, 300);
	    } catch (IOException e) {
	        e.printStackTrace();
	    } 
	}

	//Generate Pi chart
   public void generateBar(String path,List<Object[]> data) {	
	   
		//a. create dataSet for Bar and add data to it
		DefaultCategoryDataset dataset=new DefaultCategoryDataset();
		for(Object[]ob:data) {
			//val,key,label to display
			dataset.setValue(
					Double.valueOf(ob[1].toString()),//value
					String.valueOf(ob[0]),//key
					String.valueOf(ob[0]) );//label
		}
		//b. create JFreeCharts object with dataSet and other details
		// title,x-axix label,y-axix label,dataset
		JFreeChart chart=ChartFactory.createBarChart("OrderMethod TYPE COUNT","OrderMethod TYPE", "COUNT", dataset);
		//c. create JFreeCharts Object as image
	    try {
	        File dir = new File(path);
	        if (!dir.exists()) {  
	            dir.mkdirs();   // ✅ create /images folder if missing
	        }

	        File out = new File(dir, "OrderMethodB.jpg");
	        ChartUtils.saveChartAsJPEG(out, chart, 500, 500);
	    } catch (IOException e) {
	        e.printStackTrace();
	    } 
   }
}

