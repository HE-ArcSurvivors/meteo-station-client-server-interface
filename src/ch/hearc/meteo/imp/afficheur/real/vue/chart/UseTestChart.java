
package ch.hearc.meteo.imp.afficheur.real.vue.chart;

import org.jfree.ui.RefineryUtilities;



public class UseTestChart
	{

	public static void main( String[ ] args )
		   {
		   TestChart chart = new TestChart(
		      "Temp�rature" ,
		      "Temp�rature");

		      chart.pack( );
		      RefineryUtilities.centerFrameOnScreen( chart );
		      chart.setVisible( true );
		   }

	}

