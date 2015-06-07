
package ch.hearc.meteo.imp.afficheur.real.vue.chart;

import java.awt.Color;
import java.awt.FlowLayout;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import ch.hearc.meteo.imp.afficheur.real.vue.DataType;
import ch.hearc.meteo.spec.com.meteo.listener.event.MeteoEvent;

public class Chart extends JPanel
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public Chart(int dataType, List<MeteoEvent> listMeteoEvent)
		{
		this.nb = 0;
		this.listMeteoEvent = listMeteoEvent;

		dataset = new XYSeries(DataType.getString(dataType));
		updateDataset();

        //XYSeriesCollection implements XYDataset
        XYSeriesCollection dataCollection= new XYSeriesCollection();
        dataCollection.addSeries(dataset);

		lineChart = ChartFactory.createTimeSeriesChart(DataType.getString(dataType), "Date", DataType.getLegend(dataType), dataCollection);

		XYPlot plot = (XYPlot) lineChart.getPlot();
		DateAxis axis = (DateAxis)plot.getDomainAxis();
		axis.setDateFormatOverride(new SimpleDateFormat("hh:mm:ss"));

//		plot.setRangePannable(true);
//		plot.setRangeGridlinesVisible(true);
//		plot.setBackgroundAlpha(1);
		plot.setBackgroundPaint(Color.DARK_GRAY);


		lineChart.getLegend().setVisible(false);
		lineChart.setBackgroundPaint( new Color(255,255,255,0) );
		lineChart.setBackgroundImageAlpha(0.0f);

		renderer = (XYLineAndShapeRenderer)plot.getRenderer();
		renderer.setBaseShapesVisible(true);
		renderer.setDrawOutlines(true);
		renderer.setSeriesPaint(0, Color.WHITE);

		ChartPanel chartPanel = new ChartPanel(lineChart);
		setLayout(new FlowLayout());
		add(chartPanel);
		}

	private void updateDataset()
		{
		nb++;
		for(MeteoEvent event:listMeteoEvent)
			{
			System.out.println("event.getValue : " + event.getValue());
			dataset.add(event.getTime(),event.getValue());
			}
		if(nb > MAX_NB)
			{
			renderer.setBaseShapesVisible(false);
			}
		}

	public void update()
		{
		updateDataset();
		validate();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	/*------------------------------*\
	|*				Set				*|
	\*------------------------------*/

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	//Input
	private List<MeteoEvent> listMeteoEvent;

	//Tools
	private JFreeChart lineChart;
	private XYSeries dataset;
	private XYLineAndShapeRenderer renderer;
	private int nb;

	private static final int MAX_NB = 50;

	}
