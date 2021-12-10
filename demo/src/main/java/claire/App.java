package claire;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.BasicDBObject;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.model.Filters;
import static com.mongodb.client.model.Filters.eq;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.client.model.ReplaceOptions;
// import com.mongodb.client.model.Projections;
// import com.mongodb.client.model.Sorts;


public class App 
{
    public static void createUser(String usr, String pss, MongoCollection<Document> collection, MongoDatabase database)
    {
        Document doc = collection.find(eq("username", usr)).first();
        if (doc == null)
        {
            InsertOneResult result = collection.insertOne(new Document()
            .append("_id", new ObjectId())
            .append("username", usr)
            .append("password", pss));
        }
        else 
        {
            
            System.out.println("This username is already taken. Try again.");
        }
    }

    public static void deleteUser(String usr, String pss, MongoCollection<Document> collection, MongoDatabase database)
    {
        BasicDBObject andQuery = new BasicDBObject();
        List<BasicDBObject> criteria = new ArrayList<BasicDBObject>();
        criteria.add(new BasicDBObject("username", usr));
        criteria.add(new BasicDBObject("password", pss));
        andQuery.put("$and", criteria);
        Document doc = collection.find(andQuery).first();
        if (doc != null)
        {
            DeleteResult result = collection.deleteOne(Filters.eq("username", usr));
        }
        else
        {
            System.out.println("Unable to delete. Username or password may be incorrect.");
        }
        
    }

    public static void changeUser(String usr, String pss, String newUsr, MongoCollection<Document> collection, MongoDatabase database)
    {
        Document doc = collection.find(eq("username", newUsr)).first();
        if (doc != null)
        {
            System.out.println("This username is already taken.");
        } 
        else 
        {
            BasicDBObject andQuery = new BasicDBObject();
            List<BasicDBObject> criteria = new ArrayList<BasicDBObject>();
            criteria.add(new BasicDBObject("username", usr));
            criteria.add(new BasicDBObject("password", pss));
            andQuery.put("$and", criteria);
            Document doCheck = collection.find(andQuery).first();
            if (doCheck != null)
            {
                Document query = new Document().append("username", usr);
                Bson updates = Updates.combine(Updates.set("username", newUsr));
                UpdateResult result = collection.updateOne(query, updates);
            }
            else
            {
                System.out.println("Unable to change username. Username or password may be incorrect.");
            }
        }
    }
    public static void main( String[] args )
    {
        //Client connections and setup
        String uri = "mongodb://admin:<password>@cluster0-shard-00-00.nnhfz.mongodb.net:27017,cluster0-shard-00-01.nnhfz.mongodb.net:27017,cluster0-shard-00-02.nnhfz.mongodb.net:27017/myFirstDatabase?ssl=true&replicaSet=atlas-o8vojx-shard-0&authSource=admin&retryWrites=true&w=majority";
        MongoClient mongoClient = MongoClients.create(uri);
        MongoDatabase database = mongoClient.getDatabase("loginData");
        MongoCollection<Document> collection = database.getCollection("users");
        Scanner s = new Scanner(System.in);

        //User selection
        System.out.println("a to create user, b to delete user, c to change username");
        String option = s.nextLine();

        //User option conditionals
        if (option.equals("a"))
        {
            System.out.println("Enter new username: ");
            String usr = s.nextLine();
            System.out.println("Enter new password: ");
            String pss = s.nextLine();
            s.close();
            createUser(usr, pss, collection, database);
        }
        else if (option.equals("b"))
        {
            System.out.println("Enter username of account you wish to delete: ");
            String usr = s.nextLine();
            System.out.println("Enter matching password: ");
            String pss = s.nextLine();
            s.close();
            deleteUser(usr, pss, collection, database);
        }
        else if (option.equals("c"))
        {
            System.out.println("Enter username of account you wish to change: ");
            String usr = s.nextLine();
            System.out.println("Enter matching password");
            String pss = s.nextLine();
            System.out.println("Enter new username: ");
            String newUsr = s.nextLine();
            s.close();
            changeUser(usr, pss, newUsr, collection, database);
        }
        else
        {
            System.out.println("Invalid response");
        }

       
        
        

     
        
        

      
        
    }
}
    