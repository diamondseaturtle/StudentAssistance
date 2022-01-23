import java.util.ArrayList;
import java.util.Scanner;

public class ToDoList {

	private static ArrayList<String> toDo;

	public ToDoList() {
		this.toDo = new ArrayList<>();
	}

	public String toString() {
		String output = "TODO:\n";
		for (int i = 0; i < toDo.size(); i++) {
			output += (i + 1) + ". " + this.toDo.get(i) + "\n";
		}
		return output;
	}

	/**
	 * Provides a simple menu-based interface for the user to use a single
	 * ToDoList.
	 */
	public static void main(String[] args) {

//		String uri = "mongodb://admin:<password>@cluster0-shard-00-00.nnhfz.mongodb.net:27017,cluster0-shard-00-01.nnhfz.mongodb.net:27017,cluster0-shard-00-02.nnhfz.mongodb.net:27017/myFirstDatabase?ssl=true&replicaSet=atlas-o8vojx-shard-0&authSource=admin&retryWrites=true&w=majority";
//		MongoClient mongoClient = new MongoClient();
//		MongoDatabase database = mongoClient.getDatabase("loginData");
//		MongoCollection<Document> collection = database.getCollection("users");

		Scanner keybd = new Scanner(System.in);
		ToDoList list = new ToDoList();

		int choice = 1;
		while (choice != 0) {
			//print list
			System.out.println();

			//print menu choices
			System.out.println("MENU:");
			System.out.println("1 - Add item");
			System.out.println("2 - Remove last item");
			System.out.println("3 - Remove specific item");
			System.out.println("0 - Quit");
			System.out.println();
			//list of to-do tasks
			System.out.println(list.toString());  //calls toString()
			System.out.print("Enter your menu choice: ");

			
			try {
				choice = keybd.nextInt();
				keybd.nextLine();  //clear input stream
				switch (choice) {
				case 1:  //ADD
					System.out.print("Enter the thing you need to do: ");
					String task = keybd.nextLine();
					boolean added = toDo.add(task);
					if (!added) {
						System.out.println("Sorry, but this to-do list is already full!");
					}
					break;

				case 2:  //REMOVE LAST
					String removed = toDo.remove(toDo.size() - 1);
					if (removed != null) {
						System.out.println("Removed: " + removed);
					}else {
						System.out.println("The to-do list is already empty.");
					}
					break;

				case 3:  //REMOVE AT
					/** throws index out of bounds */
					System.out.print("Enter the number of the item to remove: ");
					int index = keybd.nextInt();
					removed = toDo.remove(index - 1);
					if (removed != null) {
						System.out.println("Removed: " + removed);
					}else {
						System.out.println("There is no item to be removed at index " +
								index + ".");
					}
					break;

				case 0:
					System.out.println("Goodbye!");
					break;

				default:
					System.out.println("Sorry, but " + choice + " is not one of " +
							"the menu choices. Please try again.");
					break;
				}
			}catch (java.util.InputMismatchException ime) {
				System.out.println("Sorry, but you must enter a number.");
				keybd.nextLine();  //clear bad input from stream
			}
		}
	}
}
