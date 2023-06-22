package main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;

import exceptions.ServerException;
import keyboardinput.Keyboard;

/**
 * This is a Java program that creates a client to communicate with a server.
 * The program allows the user to choose between two options: loading a clusters
 * from a file or discovering clusters from a database. Depending on the user's
 * choice, the program will execute different methods to communicate with the
 * server and retrieve the necessary information. It uses input/output streams
 * to communicate with the server and handles exceptions that may occur during
 * the communication process
 */
public class MainTest {

	/**
	 * @param ip   the ip of the client
	 * @param port the port of the server
	 */

	/** The output stream to the server */
	private ObjectOutputStream out;
	/** The input stram of the server */
	private ObjectInputStream in;

	/**
	 * The constructor for the 'MainTest' class that takes in an IP address and port
	 * number parameters. It creates a new 'InetAddress' object using the provided
	 * IP address, creates a new 'Socket' object using the IP address and port
	 * number, and initializes the 'out' and 'in' instance variables with new
	 * 'ObjectOutputStream' and 'ObjectInputStream' objects, respectively, using the
	 * 'Socket' object's output and input streams. This sets up the client to
	 * communicate with the server at the specified IP address and port number.
	 * 
	 * @param ip   name of the ip
	 * @param port integer number of the port in which the server is listening
	 * @throws IOException
	 */
	public MainTest(String ip, int port) throws IOException {
		InetAddress addr = InetAddress.getByName(ip); // ip
		System.out.println("addr = " + addr);
		Socket socket = new Socket(addr, port); // Port
		System.out.println(socket);

		out = new ObjectOutputStream(socket.getOutputStream());
		in = new ObjectInputStream(socket.getInputStream());
		; // stream con richieste del client
	}

	/**
	 * The function displays a menu with two options and prompts the user to choose
	 * one, then returns the user's choice.
	 * 
	 * @return returns an integer value, which is the user's choice of option from
	 *         the menu.
	 */
	private int menu() {
		int answer;
		System.out.println("Choose and option:");
		do {
			System.out.println("(1) Load Cluster from File");
			System.out.println("(2) Upload Data");
			System.out.print("Answer:");
			answer = Keyboard.readInt();
		} while (answer <= 0 || answer > 2);
		return answer;

	}

	/**
	 * This Java function sends a request to a server to retrieve a file's clusters
	 * and returns the clusters as a string.
	 * 
	 * @return The method is returning a String that represents the clusters of a
	 *         file that was read from the server.
	 * @throws SocketException
	 * @throws ServerException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private String learningFromFile() throws SocketException, ServerException, IOException, ClassNotFoundException {
		out.writeObject(3);

		System.out.print("File name:");
		String tabName = Keyboard.readString();
		out.writeObject(tabName);
		System.out.println("Your file clusters are:");
		String result = (String) in.readObject();
		if (result.equals("OK")) {
			return (String) in.readObject();
		} else {
			throw new ServerException(result);
		}
	}

	/**
	 * This function sends a request to the server to upload a table from a database
	 * and receives a response if the process was successful.
	 * 
	 * @throws SocketException
	 * @throws ServerException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private void storeTableFromDb() throws SocketException, ServerException, IOException, ClassNotFoundException {
		out.writeObject(0);
		System.out.print("Table name:");
		String tabName = Keyboard.readString();
		out.writeObject(tabName);
		String result = (String) in.readObject();
		if (!result.equals("OK")) {
			throw new ServerException(result);
		}
	}

	/**
	 * This Java function sends a request to a server to perform clustering on a
	 * database previously uploaded, table and returns the clustering output.
	 * 
	 * @return The method is returning a String, which is the clustering output
	 *         obtained from the database table.
	 * @throws SocketException
	 * @throws ServerException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private String learningFromDbTable() throws SocketException, ServerException, IOException, ClassNotFoundException {
		out.writeObject(1);
		System.out.print("Number of clusters:");
		int k = Keyboard.readInt();
		out.writeObject(k);
		String result = (String) in.readObject();
		if (result.equals("OK")) {
			System.out.println("Clustering output:" + in.readObject());
			return (String) in.readObject();
		} else
			throw new ServerException(result);

	}

	/**
	 * This function stores the clusters discovered int a file. The file name is
	 * sent to the server via 'out' stream. The server serialized it and sends OK if
	 * everything was ok.
	 * 
	 * @throws SocketException
	 * @throws ServerException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private void storeClusterInFile() throws SocketException, ServerException, IOException, ClassNotFoundException {
		out.writeObject(2);

		System.out.println("Enter the name of the file in which to serialise the cluster discovered.");
		String fileName = Keyboard.readString();
		out.writeObject(fileName);

		String result = (String) in.readObject();
		if (!result.equals("OK")) {
			throw new ServerException(result);
		}
	}

	/**
	 * This is the main function of the client program that takes user input
	 * arguments to perform the different operations related to KMeans algorithm on
	 * data from either a file or a database.
	 */
	public static void main(String[] args) {
		String ip = args[0];
		int port = new Integer(args[1]).intValue();
		MainTest main = null;
		try {
			main = new MainTest(ip, port);
		} catch (IOException e) {
			System.out.println(e);
			return;
		}

		do {
			int menuAnswer = main.menu();
			switch (menuAnswer) {
				case 1:
					try {
						String kmeans = main.learningFromFile();
						System.out.println(kmeans);
					} catch (SocketException e) {
						System.out.println(e);
						return;
					} catch (FileNotFoundException e) {
						System.out.println(e);
						return;
					} catch (IOException e) {
						System.out.println(e);
						return;
					} catch (ClassNotFoundException e) {
						System.out.println(e);
						return;
					} catch (ServerException e) {
						System.out.println(e.getMessage());
					}
					break;
				case 2: // learning from db

					while (true) {
						try {
							main.storeTableFromDb();
							break; // esce fuori dal while
						}

						catch (SocketException e) {
							System.out.println(e);
							return;
						} catch (FileNotFoundException e) {
							System.out.println(e);
							return;

						} catch (IOException e) {
							System.out.println(e);
							return;
						} catch (ClassNotFoundException e) {
							System.out.println(e);
							return;
						} catch (ServerException e) {
							System.out.println(e.getMessage());
						}
					} // end while [viene fuori dal while con un db (in alternativa il programma
						// termina)

					char answer = 'y';// itera per learning al variare di k
					do {
						try {
							String clusterSet = main.learningFromDbTable();
							System.out.println(clusterSet);

							main.storeClusterInFile();

						} catch (SocketException e) {
							System.out.println(e);
							return;
						} catch (FileNotFoundException e) {
							System.out.println(e);
							return;
						} catch (ClassNotFoundException e) {
							System.out.println(e);
							return;
						} catch (IOException e) {
							System.out.println(e);
							return;
						} catch (ServerException e) {
							System.out.println(e.getMessage());
						}
						System.out.print("Do you want to repete the execution?(y/n)");
						answer = Keyboard.readChar();
					} while (answer == 'y');
					break; // fine case 2
				default:
					System.out.println("Invalid option!");
			}

			System.out.print("Do you want to choose another menu operation?(y/n)");
			if (Keyboard.readChar() != 'y')
				break;
		} while (true);
	}
}
