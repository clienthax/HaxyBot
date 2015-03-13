package uk.co.haxyshideout.haxybot.utils.minecraft;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.util.List;
/**
 * @author zh32 <zh32 at zh32.de>
 */
public class MinecraftServerPingClient {
	private final InetSocketAddress host;
	private int timeout = 500;
	private int pingTries = 0;
	private final Gson gson = new Gson();

	public MinecraftServerPingClient(String host, int port) {
		this.host = new InetSocketAddress(host, port);

	}
	public InetSocketAddress getAddress() {
		return this.host;
	}
	void setTimeout(int timeout) {
		this.timeout = timeout;
	}
	int getTimeout() {
		return this.timeout;
	}
	int readVarInt(DataInputStream in) throws IOException {
		int i = 0;
		int j = 0;
		while (true) {
			int k = in.readByte();
			i |= (k & 0x7F) << j++ * 7;
			if (j > 5) throw new RuntimeException("VarInt too big");
			if ((k & 0x80) != 128) break;
		}
		return i;
	}
	void writeVarInt(DataOutputStream out, int paramInt) throws IOException {
		while (true) {
			if ((paramInt & 0xFFFFFF80) == 0) {
				out.writeByte(paramInt);
				return;
			}
			out.writeByte(paramInt & 0x7F | 0x80);
			paramInt >>>= 7;
		}
	}
	public IServerStatus ping() throws IOException, JsonSyntaxException {
		pingTries++;
		Socket socket = new Socket();
		OutputStream outputStream;
		DataOutputStream dataOutputStream;
		InputStream inputStream;
		InputStreamReader inputStreamReader;
		socket.setSoTimeout(this.timeout);
		try {
			socket.connect(host, timeout);
		} catch (SocketTimeoutException e) {
			try {
				socket.connect(host, timeout);
			} catch (Exception ignored) {}
		}
		outputStream = socket.getOutputStream();
		dataOutputStream = new DataOutputStream(outputStream);
		inputStream = socket.getInputStream();
		inputStreamReader = new InputStreamReader(inputStream);
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream handshake = new DataOutputStream(b);
		handshake.writeByte(0x00); //packet id for handshake
		writeVarInt(handshake, 4); //protocol version
		writeVarInt(handshake, this.host.getHostString().length()); //host length
		handshake.writeBytes(this.host.getHostString()); //host string
		handshake.writeShort(host.getPort()); //port
		writeVarInt(handshake, 1); //state (1 for handshake)
		writeVarInt(dataOutputStream, b.size()); //prepend size
		dataOutputStream.write(b.toByteArray()); //write handshake packet
		dataOutputStream.writeByte(0x01); //size is only 1
		dataOutputStream.writeByte(0x00); //packet id for ping
		DataInputStream dataInputStream = new DataInputStream(inputStream);
		@SuppressWarnings("UnusedAssignment") int size = readVarInt(dataInputStream); //size of packet
		int id = readVarInt(dataInputStream); //packet id
		if (id == -1) {
			if (pingTries == 1) return ping();
			throw new IOException("Premature end of stream.");
		}
		if (id != 0x00) { //we want a status response
			if (pingTries == 1) return ping();
			throw new IOException("Invalid packetID");
		}
		int length = readVarInt(dataInputStream); //length of json string
		if (length == -1) {
			if (pingTries == 1) return ping();
			throw new IOException("Premature end of stream.");
		}
		if (length == 0) {
			if (pingTries == 1) return ping();
			throw new IOException("Invalid string length.");
		}
		byte[] in = new byte[length];
		dataInputStream.readFully(in); //read json string
		String json = new String(in, Charset.forName("UTF-8"));

		long now = System.currentTimeMillis();
		dataOutputStream.writeByte(0x09); //size of packet
		dataOutputStream.writeByte(0x01); //0x01 for ping
		dataOutputStream.writeLong(now); //time!?
		readVarInt(dataInputStream);
		id = readVarInt(dataInputStream);
		if (id == -1) {
			if (pingTries == 1) return ping();
			throw new IOException("Premature end of stream.");
		}
		if (id != 0x01) {
			if (pingTries == 1) return ping();
			throw new IOException("Invalid packetID");
		}
		long pingtime = dataInputStream.readLong(); //read response

		System.out.println(json);
		IServerStatus response;
		try {
			response = gson.fromJson(json, StatusResponse17.class);//1.7 + 1.8 bungeecord -.-
		} catch (Exception e) {
			response = gson.fromJson(json, StatusResponse18.class);//1.8 servers+ ?
		}


		response.setTime((int) (now - pingtime));
		dataOutputStream.close();
		outputStream.close();
		inputStreamReader.close();
		inputStream.close();
		socket.close();
		return response;
	}

	public interface IServerStatus {
		public String getDescription();
		public Players getPlayers();
		public Version getVersion();
		public String getFavicon();
		public int getTime();
		public void setTime(int time);
	}

	public class StatusResponse18 implements IServerStatus {//used for newer(?) servers, the description is embedded in its own tag
		private Description description;
		private Players players;
		private Version version;
		private String favicon;
		private int time;
		public String getDescription() {
			return description.getText();
		}
		public Players getPlayers() {
			return players;
		}
		public Version getVersion() {
			return version;
		}
		public String getFavicon() {
			return favicon;
		}
		public int getTime() {
			return time;
		}
		public void setTime(int time) {
			this.time = time;
		}
	}

	public class Description {
		private String text;
		public String getText() { return text; }
		public void setText(String text) { this.text = text; }
	}

	public class StatusResponse17 implements IServerStatus {
		private String description;
		private Players players;
		private Version version;
		private String favicon;
		private int time;
		public String getDescription() {
			return description;
		}
		public Players getPlayers() {
			return players;
		}
		public Version getVersion() {
			return version;
		}
		public String getFavicon() {
			return favicon;
		}
		public int getTime() {
			return time;
		}
		public void setTime(int time) {
			this.time = time;
		}
	}

	public class Players {
		private int max;
		private int online;
		private List<Player> sample;
		public int getMax() {
			return max;
		}
		public int getOnline() {
			return online;
		}
		public List<Player> getSample() {
			return sample;
		}
	}
	public class Player {
		private String name;
		private String id;
		public String getName() {
			return name;
		}
		public String getId() {
			return id;
		}
	}
	public class Version {
		private String name;
		private String protocol;
		public String getName() {
			return name;
		}
		public String getProtocol() {
			return protocol;
		}
	}
}
