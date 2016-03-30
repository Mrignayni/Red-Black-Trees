import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class bbst {
	public static void main(String[] args) throws FileNotFoundException
	{
		RedBlackTree rbTree = new RedBlackTree();
		if(args[0].length() > 0)
		{
			File inputFile = new File(args[0]);
			
			Scanner scanner = new Scanner(inputFile);
			int n = scanner.nextInt();
			while(scanner.hasNext())
			{
				rbTree.insertNode(scanner.nextInt(), scanner.nextInt());
			}
			scanner.close();
			Scanner commands = new Scanner(System.in);
			String s = commands.nextLine();
			while (!s.equals("quit")) {
				String commandStr[] = s.split(" ");
				String command = commandStr[0];
	
				switch (command) 
				{
				case "increase":
					rbTree.increase(Integer.parseInt(commandStr[1]), Integer.parseInt(commandStr[2]));
					break;
				case "reduce":
					rbTree.reduce(Integer.parseInt(commandStr[1]), Integer.parseInt(commandStr[2]));
					break;
				case "count":
					rbTree.count(Integer.parseInt(commandStr[1]));
					break;
				case "inrange":
					rbTree.inRange(Integer.parseInt(commandStr[1]), Integer.parseInt(commandStr[2]));
					break;
				case "next":
					rbTree.next(Integer.parseInt(commandStr[1]));
					break;
				case "previous":
					rbTree.previous(Integer.parseInt(commandStr[1]));
					break;
				default:
					System.out.println("\nInvalid command: '" + command + "' ! Enter 'quit' to exit. ");
					break;
				}
				s = commands.nextLine();
			}
			commands.close();
		}
		else{
			System.out.println("Enter input file as argument. ");
		}
	}
}
