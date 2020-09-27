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

    }

    public void configuraCredenciais() {
        TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
        StatusListener listener = new StatusListener() {
            public void onStatus(Status status) {
                BasicDBObject dbObject = new BasicDBObject();

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
        }
    }

    public void conectaMongoDB() {

    }

    public static void main(String[] args) throws InterruptedException {

    }
}