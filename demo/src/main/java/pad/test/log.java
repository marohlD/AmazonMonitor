package pad.test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class log {
    public static List<String> readLog(){     
        try {
            //return data from log as an array of entries
            List<String> result = Files.readAllLines(Paths.get("src\\main\\java\\pad\\test\\log.txt"));
            //System.out.println(result);
            return result;
        }
        catch (Exception ex){
            System.out.println("can't find log file");
            return null;
        }
    }

    public static void writeLog(String entry,boolean isNotification){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("src\\main\\java\\pad\\test\\log.txt", true));
            //insert entry onto a new line
            writer.write("\n");

            //mark entry with a type
            if (isNotification) writer.write("1 ");
            else writer.write("0 ");

            //enter data
            writer.write(entry);
            writer.close();
            }
        catch (Exception ex){
            System.out.println("couldn't write to log file, is log.txt missing?");
            return;
        }
    }


    public static double getLatestPrice(){
        List<String> entries = readLog();
        double price = 0.0;

        //Get the latest price entered into the log file. We start at the end and loop backwards to save time
        for (int i = entries.size() - 1; i >= 0; i--){
            
            //if log entry is a notification, skip
            String entry = entries.get(i);
            //System.out.println("Entry at index " + i + " is " +entry);
            if (entry.charAt(0) != '0') {
                continue;
            }   
            else{
                //grab price value from log entry. (Whatever comes after the '='), then return it as a double
                String priceString = entry.split("=")[1];
                //System.out.println(priceString);
                price = Double.parseDouble(priceString);
                break;
            }
        }

        return price;
    }





}
