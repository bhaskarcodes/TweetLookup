import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
 
public class PieChart_AWT extends JFrame
{
    static double arr[]=new double[3];
 
   public PieChart_AWT( String title,double []dd) 
   {
      super( title ); 
      arr=dd;
      setContentPane(createDemoPanel());
      this.setDefaultCloseOperation(HIDE_ON_CLOSE);
   }
   private static PieDataset createDataset( ) 
   {
      DefaultPieDataset dataset = new DefaultPieDataset( );
      dataset.setValue( "Positive" , arr[2] );  
      dataset.setValue( "Neutral" , arr[1] );   
      dataset.setValue( "Negative" , arr[0] );     
      return dataset;         
   }
   private static JFreeChart createChart( PieDataset dataset )
   {
      JFreeChart chart = ChartFactory.createPieChart(      
         "Twitter Sentiment Analysis",  // chart title 
         dataset,        // data    
         true,           // include legend   
         true, 
         true);

      return chart;
   }
   public static JPanel createDemoPanel( )
   {
      JFreeChart chart = createChart(createDataset());
      PiePlot plot = (PiePlot) chart.getPlot();
      return new ChartPanel( chart ); 
   }
   public void setarr(double []ss){
       arr=ss;
   }

}