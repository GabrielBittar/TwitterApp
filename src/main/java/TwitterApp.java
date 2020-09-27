import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;

import java.net.UnknownHostException;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterApp {
    private ConfigurationBuilder cb;
    private DB database;
    private DBCollection collection;

    public void capturaTweets() throws InterruptedException {
        TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
        StatusListener listener = new StatusListener() {
            public void onStatus(Status status) {
                BasicDBObject dbObject = new BasicDBObject();
                dbObject.put("tweet_ID", status.getId());
                dbObject.put("usuario", status.getUser().getScreenName());
                dbObject.put("tweet", status.getText());

                try{
                    collection.insert(dbObject);
                }catch (Exception e){
                    System.out.println("Erro de conexão: " + e.getMessage());
                }
            }

            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {

            }

            public void onTrackLimitationNotice(int i) {

            }

            public void onScrubGeo(long l, long l1) {

            }

            public void onStallWarning(StallWarning stallWarning) {

            }

            public void onException(Exception e) {

            }
        };

        String palavrasParaBuscar[] = {"Chopin"};
        FilterQuery fq = new FilterQuery();
        fq.track(palavrasParaBuscar);
        twitterStream.addListener(listener);
        twitterStream.filter(fq);
    }

    public void configuraCredenciais() {
        cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true);
        cb.setOAuthConsumerKey("");
        cb.setOAuthConsumerSecret("");
        cb.setOAuthAccessToken("");
        cb.setOAuthAccessTokenSecret("");
    }

    public void conectaMongoDB() {

    }

    public static void main(String[] args) throws InterruptedException {

    }
}