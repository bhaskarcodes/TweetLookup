import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import twitter4j.GeoLocation;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * @author Bhaskar ref :: http://rahular.com/twitter-sentiment-analysis/
 * http://stackoverflow.com/questions/18800610/how-to-retrieve-more-than-100-results-using-twitter4j
 *
 *
 */
public class TweetCollector {

    public static ArrayList<Status> getTweets(String topic, int num) throws TwitterException {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("C6cUT1Spwd4GRAPZcUH7gr4qL")
                .setOAuthConsumerSecret("tUG5W0eAmouq7vw1UZxulu1C24sgBn563Z5amnWNgw4ARiqenH")
                .setOAuthAccessToken("407651040-79M1aoKKDhPlTP8LBLMypAI4TfInD2ZBEzRI6hkf")
                .setOAuthAccessTokenSecret("70K6CII1YseeOVXxLugPdxj42EkfJOPQk6jAkYQTL5rxN");
        Twitter twitter = new TwitterFactory(cb.build()).getInstance();
        Query query = new Query(topic);
        int numberOfTweets = num;   //no of tweets needed
        long lastID = Long.MAX_VALUE;
        ArrayList<Status> tweets = new ArrayList<Status>();
        QueryResult result = twitter.search(query);
        //System.out.println(result.getTweets().size());
        while (tweets.size() < numberOfTweets && result.getTweets().size() > 0) {
            if (numberOfTweets - tweets.size() > 100) {
                query.setCount(100);
            } else {
                query.setCount(numberOfTweets - tweets.size());
            }
            try {
                result = twitter.search(query);
                tweets.addAll(result.getTweets());
                System.out.println("Gathered " + tweets.size() + " tweets");
                for (Status t : tweets) {
                    if (t.getId() < lastID) {
                        lastID = t.getId();
                    }
                }

            } catch (TwitterException te) {
                System.out.println("Couldn't connect: " + te);
            };
            query.setMaxId(lastID - 1);
        }


        /*
        
TwitterFactory tf = new TwitterFactory(cb.build());
Twitter twitter = tf.getInstance();
        ArrayList<String> tweetList = new ArrayList<String>();
        try {
            Query query = new Query(topic);
            QueryResult result;
            do {
                result = twitter.search(query);
                List<Status> tweets = result.getTweets();
                for (Status tweet : tweets) {
                    tweetList.add(tweet.getText());
                }
            } while ((query = result.nextQuery()) != null);
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to search tweets: " + te.getMessage());
        }
         */ /*ArrayList<String> tweetList = new ArrayList<String>();
        for (Status tweet : tweets) {
            tweetList.add(tweet.getText());
        }*/
        return tweets;

    }
}

class TweetAnalyzer {

    public static int lookup(String topic, String num) throws IOException, TwitterException {
        System.out.println("Please wait...........");
        ArrayList<Status> tweets = TweetCollector.getTweets(topic, Integer.parseInt(num));
        if (tweets.size() == 0) {
            return -1;
        }
        try {
            FileOutputStream fos = new FileOutputStream("SearchResults.txt");
            DataOutputStream bos = new DataOutputStream(fos);
            FileOutputStream fos1 = new FileOutputStream("textcollection.txt");
            DataOutputStream bos1 = new DataOutputStream(fos1);
            
	  
            bos.write(("Twitter LookUp\r\n").getBytes());
            bos.write(("Made By : Bhaskar Tejaswi\r\n").getBytes());
            bos.write(("Search Topic :: " + topic + "\r\n").getBytes());
            bos.write(("# of tweets  :: " + num + "\r\n").getBytes());
            bos.write("\r\n".getBytes());

            int k = 0;
            for (Status tweet : tweets) {
                String user = tweet.getUser().getScreenName();
                String msg = tweet.getText();
                GeoLocation loc = tweet.getGeoLocation();
                k++;
                bos.write(("(" + k + ")").getBytes());
                bos.write(("USER : " + user + "\r\n").getBytes());
                bos.write(("TWEET : " + msg + "\r\n").getBytes());
                String store;
                if(msg.contains("https://")){    //remove links for sentiment analysis
               // bw.write((msg.split("https://")[0]));
		//bw.newLine();
                store = msg.split("https://")[0];
                store=store.replaceAll("\\p{C}", "?");
                store = store.replace("\r\n", "");
                store = store+"$$$$$";
                bos1.write(store.getBytes());
                System.out.println(k+":::"+msg);
                }
                
                else
                {
                store = msg;
                store=store.replaceAll("\\p{C}", "?");
                store = store.replace("\r\n", "");
                store = store+"$$$$$";
                bos1.write(store.getBytes()); 
                
                System.out.println(k+":::"+msg);
                    //bw.write(msg);
                 //bw.newLine();
                }                 
                
                bos.write("==================================================\r\n".getBytes()); //to add a new line we need to give \r\n in windows
            }
            bos1.close();
            bos.close();
            Process p = Runtime.getRuntime().exec("python C:\\Users\\bhaskar\\AppData\\Local\\Programs\\Python\\Python35\\text1.py");
             
            if (k == 0) {
                System.out.println("No tweets on this!");
                return -1;
            } else {
                System.out.println("Search Successful..");
                if (Desktop.isDesktopSupported()) {
                    File file = new File("SearchResults.txt");
                    Desktop.getDesktop().edit(file);
                } else {
                    System.out.println("Sorry..Desktop not supported");
                    return -1;
                }
            }
        } catch (Exception e) {
        }
        finally{
        }
        return +1;
    }
}