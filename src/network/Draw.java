/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;
import java.awt.Color; 
import java.awt.BasicStroke; 
import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import org.jfree.chart.ChartPanel; 
import org.jfree.chart.JFreeChart; 
import org.jfree.data.xy.XYDataset; 
import org.jfree.data.xy.XYSeries;
import org.jfree.ui.ApplicationFrame; 
import org.jfree.ui.RefineryUtilities; 
import org.jfree.chart.plot.XYPlot; 
import org.jfree.chart.ChartFactory; 
import org.jfree.chart.plot.PlotOrientation; 
import org.jfree.data.xy.XYSeriesCollection; 
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Millisecond;
class Draw extends Thread
{       
    public int method;
    Draw(int method){
        this.method = method;
    }
    boolean isRun = true;

    @Override
    public void run() {
        JFrame frame = new JFrame();  
        frame.addWindowListener(
            new java.awt.event.WindowAdapter()
              {
                @Override
                 public void windowClosing(java.awt.event.WindowEvent windowEvent)
                   {
                       isRun = false;
                   }
              }
           );
        
        while(isRun) {
            JFreeChart xylineChart;
            if(method==0){
                System.out.println("hi");
                xylineChart = ChartFactory.createXYLineChart(
                "Tempreture" ,
                "Time" ,
                "Tempreture" ,
                createDataset(0) ,
                PlotOrientation.VERTICAL ,
                true , true , false);
            }
            else{
                System.out.println("hi");
                xylineChart = ChartFactory.createXYLineChart(
                "Tempreture" ,
                "Time" ,
                "Tempreture" ,
                createDataset(1) ,
                PlotOrientation.VERTICAL ,
                true , true , false);
            }
            final XYPlot plot = xylineChart.getXYPlot( );
            XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer( );
            renderer.setSeriesPaint( 0 , Color.RED );
            renderer.setSeriesStroke( 0 , new BasicStroke( 1.0f ) );
            plot.setRenderer( renderer ); 

            frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            frame.setSize(700,500);
            frame.getContentPane().removeAll();

            ChartPanel chartPanel = new ChartPanel( xylineChart );                                      
            frame.add(chartPanel);    
            frame.setVisible(true);
            try {
                Thread.sleep(10000);
            } catch (InterruptedException ex) {
                System.out.println(ex);
            }
        }
    }
 private static XYDataset createDataset(int method)
   {
        XYSeries tempreture = new XYSeries( "Tempreture" );          
        String output = null;
        String url = "http://localhost/server/dia.php";
        System.out.println(url);
        try {
            if(method == 0){
                output = HTTP.getHTML(url);
                System.out.println("get");              
            }else{
               output = HTTP.postHTML(url,""); 
               System.out.println("post");
            }
        } catch (Exception ex) {
            Logger.getLogger(Draw.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(output);
       
        String[] strArray = output.split("&");
        
        //String data[] = output.split("&");
//      
//         for(int i=0;i<10;i++){
//            System.out.print(data[i]);
//        }
        int x,y;
        
        for(int i = strArray.length-10; i < strArray.length; ) {
            x = Integer.parseInt(strArray[i]);
            y = Integer.parseInt(strArray[i+1]);
            System.out.print(x+":"+y+"\n");

            tempreture.add(x, y);          
            i=i+2;
        }
//       
        final XYSeriesCollection dataset = new XYSeriesCollection( );          
        dataset.addSeries( tempreture );      
     
        return dataset;
   }

    

}