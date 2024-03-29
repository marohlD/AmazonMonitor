package pad.test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import javafx.application.Platform;


public class monitor extends Thread{
    
    //thread variable that can be started and stopped with function calls as needed.
    private static monitor thread;
    private volatile boolean isRunning;


    private static int delayValue = 1;

    //Set duration between price scrapings when initializing monitor thread
    public static void setDelay(int delay){
        delayValue = delay;
    } 
  
    //Keep time for logging
    public static String getCurrentTime() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("Y:M:d:HH");
        //seconds version for testing
        String formattedTime = sdf.format(cal.getTime());
        return formattedTime;
    }

    public static void startMonitor() {
        thread = new monitor();
        thread.isRunning = true;
        thread.start();
    }

    public static void stopMonitor() {
        thread.isRunning = false;
        thread.interrupt();
        Thread.currentThread().interrupt();
    }


    //threaded loop to check ipad price
        public void run() {
            while (isRunning){
                try {
                    //fetch last price from log to compare to new value
                    double lastPrice = log.getLatestPrice();
                    System.out.println("Latest price from log " + lastPrice);


                    //scrape the price from Amazon, 
                    double currentPrice = scrape.getPriceDouble();

                    //compare current price with last log entry's price, notify the user if there is a drop
                    if (currentPrice < lastPrice){
                        System.out.println("Holy Moly, a price drop!!!!");
                        
                        //notification routine
                        notification.sendNotification();
                    }

                    //format new price with current date and time, enter it into the log file
                    String logEntry = getCurrentTime() + " Price=" + currentPrice;
                    log.writeLog(logEntry,false);

                    //jump to the main javafx GUI thread to run the update code
                    Platform.runLater(new Runnable() {
                        @Override public void run() {
                            //update the GUI to reflect current price
                            App.controller.updateGuiPrice(currentPrice);   
                        }
                    });

                        
                    System.out.println("Start delay of "+ delayValue + " hours, Time is: " + getCurrentTime());
                    TimeUnit.HOURS.sleep(delayValue);


                } 
                catch(Exception ex) {
                    Thread.currentThread().interrupt();
                }
            }
        }
}
  




