package claire;
import java.util.Arrays;
import java.util.Scanner;
import org.bson.Document;
import org.bson.types.ObjectId;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertOneResult;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        //Client connections and setup
        String uri = "mongodb://admin:<password>@cluster0-shard-00-00.nnhfz.mongodb.net:27017,cluster0-shard-00-01.nnhfz.mongodb.net:27017,cluster0-shard-00-02.nnhfz.mongodb.net:27017/myFirstDatabase?ssl=true&replicaSet=atlas-o8vojx-shard-0&authSource=admin&retryWrites=true&w=majority";
        MongoClient mongoClient = MongoClients.create(uri);
        MongoDatabase database = mongoClient.getDatabase("loginData");
        MongoCollection<Document> collection = database.getCollection("users");
        Scanner s = new Scanner(System.in);

        //Scan user input
        System.out.println("Enter new username: ");
        String usr = s.nextLine();
        System.out.println("Enter new password: ");
        String pss = s.nextLine();
        s.close();

        //Insert logins
        InsertOneResult result = collection.insertOne(new Document()
        .append("_id", new ObjectId())
        .append("username", usr)
        .append("password", pss));
    }
}
    