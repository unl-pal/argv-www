import java.net.*;
import java.util.*;
import java.io.*;

public class Client {
	BufferedReader inFromServer;
	PrintWriter outToServer;
	// Socket that used to connect with server
	private static Socket socket;
	private static ServerSocket listensocket;
	// private static listenThread listen;
	private static streamThread serWriter;
	private static String uuid;

	private static String serverIP;
	private static int PORT;
	static int clientPort;
	private static int HEARTBEAT = 30;
	private static String name;
	private static String pwd;
	private static HashMap<String, String> p2pSocket = new HashMap<String, String>();

	/**
	 * Connect to server and start reading and writing
	 * 
	 * @param serverIP
	 * @param PORT
	 * @throws IOException
	 */
	public Client(String serverIP, int PORT) throws IOException {
		// establish the connection
		try {
			socket = new Socket(serverIP, PORT);
			// CREATE a thread to write to server
			serWriter = new streamThread(socket);
			serWriter.start();

		} catch (UnknownHostException e) {
			System.err.println("Can't find the server.");
			System.exit(-1);

		} catch (IOException e) {
			System.err.println("Couldn't get I/O from the server.");
			System.exit(-1);
		}
	}

	public static void main(String[] args) throws Exception {
		serverIP = args[0];
		PORT = Integer.valueOf(args[1]);
		int max = 50000;
		int min = 40000;
		boolean check = true;
		Client clientApplication = new Client(serverIP, PORT);
		// Generate the listening port randomly before checking if the port is in use
		while (check) {
			Random random = new Random();
			clientPort = random.nextInt(max) % (max - min + 1) + min;
			check = isPortUsing(clientPort);
		}
		listensocket = new ServerSocket(clientPort);
		while (true) {
			Socket conn = listensocket.accept();
			ListenThread connThread = new ListenThread(conn, p2pSocket);
			connThread.start();
		}
	}

	// check if listening port is in use or not
	public static boolean isPortUsing(int port) {
		try {
			ServerSocket server = new ServerSocket(port);
			server.close();
			return false;
		} catch (IOException e) {
		}
		return true;
	}

	public static class streamThread extends Thread {
		private InputStream inputStream;
		private OutputStream outputStream;
		private BufferedReader inFromServer;
		private PrintWriter outToServer;
		private Socket clientSocket;

		/**
		 * Construct the streamThread
		 * 
		 * @param input
		 *            stream to get data from server
		 * @param output
		 *            stream to write to the server
		 */
		public streamThread(Socket socket) {
			this.clientSocket = socket;
		}

		// HEARTBEAT
		private void heartbeat(final String heartip, final int heartport,
				final String id) {
			new Thread() {
				public void run() {
					try {
						String msg = id + " HEARTBEAT";
						while (true) {
							Socket heartSK = new Socket(heartip, heartport);
							PrintWriter pw = new PrintWriter(
									heartSK.getOutputStream(), true);
							pw.println(msg);
							heartSK.close();
							Thread.sleep(HEARTBEAT * 1000);
						}
					} catch (Exception e) {
					}

				}
			}.start();
		}

		public boolean isP2PValid(String ip, int port) {
			try {
				Socket sk = new Socket(ip, port);
				sk.close();
				return false; // the address is valid
			} catch (IOException e) {
			}
			return true;
		}

		/**
		 * Buffer the input stream and write it to the output stream.
		 */
		public void run() {
			try {
				String line;
				inputStream = clientSocket.getInputStream();
				outputStream = clientSocket.getOutputStream();
				inFromServer = new BufferedReader(new InputStreamReader(
						inputStream));
				outToServer = new PrintWriter(outputStream, true);
				// authentication
				Scanner sc = new Scanner(System.in);
				System.out.println("Username: ");
				name = sc.nextLine();
				System.out.println("Password: ");
				pwd = sc.nextLine();
				String namePwd = name + " " + pwd;
				outToServer.println("USERNAME " + namePwd);
				while (true) {
					line = inFromServer.readLine();
					if ((line).startsWith("0")) {
						System.out.println("Welcome to simple chat server!");
						String[] spl = line.split(" ");
						uuid = spl[1]; // identifier for the user
						// send the client listening port to the server
						outToServer.println("CLIENTPORT " + uuid + " "
								+ clientPort);
						break;
					}
					if ((line + "").equals("1")) {
						System.out
								.println("Invalid Password. Please try again");
						System.out.println("Username: ");
						name = sc.nextLine();
						System.out.println("Password: ");
						pwd = sc.nextLine();
						namePwd = name + " " + pwd;
						outToServer.println("USERNAME " + namePwd);
					}
					if ((line + "").equals("2")) {
						System.out
								.println("Invalid Password. Your account has been blocked. Please try again after sometime");
						System.exit(-1);
					}
					if ((line + "").equals("3")) {
						System.out
								.println("Due to multiple login failures, your account has been blocked. Please try again after sometime.");
						System.exit(-1);
					}
				}
				socket.close();// for non-persistent connection
				this.heartbeat(serverIP, PORT, uuid);
				while (true) {
					BufferedReader br = new BufferedReader(
							new InputStreamReader(System.in));
					// read in from user input
					String input = br.readLine();
					String toServer = uuid + " " + input;
					if (!input.startsWith("private")) {
						socket = new Socket(serverIP, PORT);// init TCP connection
						// write to the server
						PrintWriter pw = new PrintWriter(
								socket.getOutputStream(), true);
						pw.println(toServer);
						socket.close();// deinit TCP connection
						// p2p connection
					} else {
						String[] spl = input.split(" ");
						String usr = spl[1];
						String msg = name+": "+input
								.replaceAll(spl[0] + " " + spl[1], "");
						if (p2pSocket.containsKey(usr)) {
							String p2pip = p2pSocket.get(usr).split(" ")[0];
							int p2pport = Integer.parseInt(p2pSocket.get(usr)
									.split(" ")[1]);
							if (!this.isP2PValid(p2pip, p2pport)) {
								socket = new Socket(p2pip, p2pport);
								// write to the server
								PrintWriter pw = new PrintWriter(
										socket.getOutputStream(), true);
								pw.println(msg);
								socket.close();
							} else {
								System.out
										.println("Please send offline message.");
							}
						} else {
							System.out
									.println("Please use getaddress <user> command to start P2P connection.");
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public static class ListenThread extends Thread {
		Socket socket;
		BufferedReader inFromServer;
		HashMap<String, String> map;

		public ListenThread(Socket socket, HashMap<String, String> hashmap) {
			this.socket = socket;
			this.map = hashmap;
		}

		public void run() {
			try {
				inFromServer = new BufferedReader(new InputStreamReader(
						socket.getInputStream()));
				String line = inFromServer.readLine();
				String[] spl = null;
				if (line != null) {
					spl = line.split(" ");
					if (line.startsWith("logout")) {
						System.exit(-1);
					}
					if (line.startsWith("EXISTING")) {
						System.out
								.println("You have logged out since other people have logged in using the same USERNAME");
						System.exit(-1);
					}
					if (line.startsWith("BLOCK")){
						String blockName = spl[1];
						if (map.containsKey(blockName)){
							map.remove(blockName);
						}
					}
					// incoming message from another user for p2p connection
					// not from the server
					if (line.startsWith("P2P")) {
						String key = spl[1];
						String p2pip = spl[3];
						int p2pport = Integer.parseInt(spl[5]);
						if (map.containsKey(key)) {
							map.remove(key);
							map.put(key, p2pip + " " + p2pport);
						} else {
							map.put(key, p2pip + " " + p2pport);
						}
					}
					//receiving p2p message from another user
					if (line.startsWith("private")) {
						System.out.println(line.replaceAll("private " + spl[1]
								+ " ", ""));
					} 
					else {
						System.out.println(line);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
