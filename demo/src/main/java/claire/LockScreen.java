import javax.swing.JLayeredPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.BorderFactory;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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

public class LockScreen extends Screen {
    
    private JLabel ui = null;
    private JLabel buttonAccount = null;
    private JLabel buttonLogin = null;
    private JTextField[] textFields = null;
    private boolean creatingAccount;

    private String uri = "mongodb://admin:students@cluster0-shard-00-00.nnhfz.mongodb.net:27017,cluster0-shard-00-01.nnhfz.mongodb.net:27017,cluster0-shard-00-02.nnhfz.mongodb.net:27017/myFirstDatabase?ssl=true&replicaSet=atlas-o8vojx-shard-0&authSource=admin&retryWrites=true&w=majority";
    private MongoClient mongoClient = MongoClients.create(uri);
    private MongoDatabase database = mongoClient.getDatabase("loginData");
    private MongoCollection < Document > collection = database.getCollection("users");

    private boolean createAccount(String username, String password, MongoDatabase database, MongoCollection < Document > collection) {
        // return true if account is successfully created
        // return false otherwise; username is already taken
        Document doc = collection.find(eq("username", username)).first();
        if (doc == null) {
            InsertOneResult result = collection.insertOne(new Document()
                .append("_id", new ObjectId())
                .append("username", username)
                .append("password", password));
            return true;
        }
        return false;
    }
    
    private boolean login(String username, String password, MongoDatabase databse, MongoCollection < Document > collection) {
        // return true if login is successful
        // return false otherwise; username or password is incorrect
        BasicDBObject andQuery = new BasicDBObject();
        List < BasicDBObject > criteria = new ArrayList < BasicDBObject > ();
        criteria.add(new BasicDBObject("username", username));
        criteria.add(new BasicDBObject("password", password));
        andQuery.put("$and", criteria);
        Document doCheck = collection.find(andQuery).first();
        if (doCheck != null) {
            return true;
        }
        return false;
    }

    public LockScreen() {

        // user interface image overlay
        ui = new JLabel("", new ImageIcon("res/UILogin.png"), JLabel.LEFT);
        ui.setBounds(0, 0, 1280, 720);
        panel.add(ui);

        closeButton();

        // Button: Create a new account
        creatingAccount = false;
        buttonAccount = new JLabel("", new ImageIcon("res/AccountCreate.png"), JLabel.LEFT);
        buttonAccount.setBounds(587, 447, 450, 55);
        panel.add(buttonAccount);
        buttonAccount.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                textFields[0].setText("");
                textFields[1].setText("");

                creatingAccount = !creatingAccount; // swap boolean value
                if (creatingAccount) {
                    ui.setIcon(new ImageIcon("res/UIAccount.png"));
                    buttonAccount.setIcon(new ImageIcon("res/AccountLogin.png"));

                } else {
                    ui.setIcon(new ImageIcon("res/UILogin.png"));
                    buttonAccount.setIcon(new ImageIcon("res/AccountCreate.png"));

                }
            }
        });

        // Button: Login
        buttonLogin = new JLabel("", new ImageIcon("res/Login.png"), JLabel.LEFT);
        buttonLogin.setBounds(1010, 447, 260, 55);
        panel.add(buttonLogin);
        buttonLogin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                boolean successful;
                if (creatingAccount) {
                    successful = createAccount(textFields[0].getText(), textFields[1].getText(), database, collection);
                    if (successful) {
                        Window.switchScreen("home");
                    } else {
                        ui.setIcon(new ImageIcon("res/UIAccountError.png")); // Temporary code for testing
                    }
                } else {
                    successful = login(textFields[0].getText(), textFields[1].getText(), database, collection);
                    if (successful) {
                        Window.switchScreen("home");
                    } else {
                        ui.setIcon(new ImageIcon("res/UILoginError.png")); // Temporary code for testing
                    }
                }
            }
        });

        textFields = new JTextField[2];
        for (int i = 0; i < 2; i++) {
            textFields[i] = new JTextField();
            textFields[i].setColumns(1);
            textFields[i].setFont(Window.getFont(28));
            textFields[i].setBackground(Window.colorBox);
            textFields[i].setBorder(BorderFactory.createLineBorder(Window.colorBox));
            textFields[i].setForeground(Window.colorText);
        }

        textFields[0].setBounds(853, 303, 353, 55);
        panel.add(textFields[0]);

        textFields[1].setBounds(853, 375, 353, 55);
        panel.add(textFields[1]);

    }

}