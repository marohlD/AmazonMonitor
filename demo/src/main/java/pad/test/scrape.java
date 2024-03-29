package pad.test;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.*;


public class scrape {
    
   //webpage for ipad, could be swapped for any Amazon item's url to monitor that instead 
   private static String url = "https://www.amazon.com/2021-Apple-10-2-inch-iPad-Wi-Fi/dp/B09G9FPHY6/ref=sr_1_4?crid=2ZG8MVJI3H639&dib=eyJ2IjoiMSJ9.prFmn54ORvlh4DwvlnSkXzJwUR6Uwz8OKy6iHCfzgOhlFkp2XWzX-EWnTKgr7LnqEKhiv9TRsimKNSlJtcxrgx5WqH0hiNybyh2JhLD6NdfFPRwTCj0xsZiguMfwUweSDHlrGW0YZhAHaeAicQHwiZl7cB9eBV0EM2jL3mlUWJgItkWf00QnF4fS1uqlgAU1HR5AA44HG6Go8dzWrEjlOZgxxfDX0ZIoEwWT1h5Xp6Q.R8oz-wE4ffeBgMkGZLR33QBRskMHyx74l9lh2z8Z-Zs&dib_tag=se&keywords=amazon+ipad&qid=1710982825&sprefix=amazon+ipad%2Caps%2C200&sr=8-4";

    //returns a string for gui text output and history log
    public static String getPriceString(){
        try {
            //get the raw HTML from the provided URL
            final Document document = Jsoup.connect(url).get();
            //grab the dollar and cent price, format it then return the result
            Element dollarPrice = document.selectFirst("span.a-price-whole");
            Element centPrice = document.selectFirst("span.a-price-fraction");

            //build a compound string that can be converted into a double when performing price comparison
            String result = dollarPrice.text() + centPrice.text();
            return result;

        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        /*if the try block fails, it is likely the user is not connected to the internet. 
          Or the amazon page has been deleted if this is being read in the future, in which case feel free to update the url variable to the
          item of your choice.
        */
        return "error: check internet connection";
    }

    //returns a double for math operations
    public static Double getPriceDouble(){
        //in the event a double can't be returned (due to an error e.g no internet connection), return a default 0.0
        try{
            Double price = Double.parseDouble(getPriceString());
            return price;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0.0;
    }

    public static String getItemUrl(){
        return url;
    }

}
