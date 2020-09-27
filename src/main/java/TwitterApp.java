import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;

import java.net.UnknownHostException;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import javax.swing.plaf.basic.BasicDirectoryModel;

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
        try {
            Mongo mongoCli;
            mongoCli = new MongoClient(""); // Rodando o Mongo no Docker, inserir o IP do container
            database = mongoCli.getDB("twDB");
            collection = database.getCollection("tweets");
            BasicDBObject index = new BasicDBObject("tweet_ID",1);
            collection.ensureIndex(index, new BasicDBObject("unique", true));
        }catch (UnknownHostException unknownEx){
            System.out.println("MongoException: " + unknownEx.getMessage());
        }catch (Exception e){
            System.out.println("Exception: " + e);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        TwitterApp tw = new TwitterApp();
        tw.conectaMongoDB();
        tw.configuraCredenciais();
        tw.capturaTweets();
    }
}