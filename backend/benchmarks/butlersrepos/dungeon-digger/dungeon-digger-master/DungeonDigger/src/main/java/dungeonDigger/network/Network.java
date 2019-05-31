package dungeonDigger.network;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Network {
	static public final int port = 54555;
	//private Logger logger = Logger.getLogger("DungeonDigger.Network");
	
	//////////////////////////////////
	// Listeners that the game uses //
	//////////////////////////////////
	static public class ServerListener {
		Logger logger = Logger.getLogger("ServerListener");
	}
	static public class ClientListener {
		Logger logger = Logger.getLogger("ClientListener");
	}
	
	//////////////////////////////////////
	// Traffic, packets, responses, etc //
	//////////////////////////////////////
	static abstract class Response {
		public boolean response;
	}
	static public class ChatPacket {
		public String text;

		public ChatPacket() {}
	}
	static public class GameJoinPacket {
		public int gameStateId;

		public GameJoinPacket() {}
		public GameJoinPacket(int gameStateId) { this.gameStateId = gameStateId; }
	}
	static public class GameStartPacket {
		public int x, y;

		public GameStartPacket() {}
		public GameStartPacket(int startX, int startY) {
			x = startX;
			y = startY;
		}
	}
	static public class LoginRequest {
		public String ipAddress, account, password;

		public LoginRequest() { }
	}
	static public class LoginResponse{
		public LoginResponse() { }
		public LoginResponse(boolean val) { }
	}
	static public class SignOff {
		public String ipAddress, account, password;

		public SignOff() {
		}
	}
	static public class TextPacket {
		public String text;
		// Duration, 0 = indefinite
		public int x, y, durationInMilliseconds, passedTime = 0;

		public TextPacket() { }
	}
	static public class PlayerInfoPacket {
	}
	static public class PlayerListPacket {
		public PlayerListPacket(){ }
		
	}
	static public class PlayerMovementUpdate {
		public String player;
		public float x, y; 
		public PlayerMovementUpdate() {}
	}
	static public class PlayerMovementRequest {
		public String player;
		public float x, y; 
		public PlayerMovementRequest() {}
	}
	static public class PlayerMovementResponse {
		public PlayerMovementResponse() {}
		public PlayerMovementResponse(boolean success) {
		}
	}
	static public class TilesResponse {
	}
	static public class TileResponse {
		public int row, col;
		public TileResponse(){}
	}
	static public class TilesRequest {
		public int[] list;

		public TilesRequest(int... coords) {
			list = coords;
		}
	}
	static public class WholeMapPacket {
	}
}
