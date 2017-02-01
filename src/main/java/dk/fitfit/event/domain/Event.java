package dk.fitfit.event.domain;

import javax.persistence.*;
import java.io.*;
import java.util.Map;

@Entity
public class Event {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String type;
	private long timestamp;
	@Lob
	private byte[] payload;

	// Needed by hibernate
	public Event() {
	}

	public Event(String type, long timestamp, Map<String, Object> payload) throws IOException {
		this.type = type;
		this.timestamp = timestamp;
		this.payload = toByteArray(payload);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public Map<String, Object> getPayload() throws IOException, ClassNotFoundException {
		return toMap(payload);
	}

	public void setPayload(Map<String, Object> payload) throws IOException {
		this.payload = toByteArray(payload);
	}

	// TODO: Move to util package
	// Inspiration: https://www.thecodingforums.com/threads/hashmap-conversion.388535/#post-2001548
	private byte[] toByteArray(Map<String, Object> payload) throws IOException {
		ByteArrayOutputStream byteArrayStream = new ByteArrayOutputStream();
		ObjectOutputStream objectStream = new ObjectOutputStream(byteArrayStream);
		objectStream.writeObject(payload);
		return byteArrayStream.toByteArray();
	}

	// TODO: Move to util package
	// Inspiration: https://www.thecodingforums.com/threads/hashmap-conversion.388535/#post-2001548
	private Map<String, Object> toMap(byte[] payload) throws IOException, ClassNotFoundException {
		ByteArrayInputStream byteArrayStream = new ByteArrayInputStream(payload);
		ObjectInputStream objectStream = new ObjectInputStream(byteArrayStream);
		@SuppressWarnings("unchecked") // the byte[] is expected to contain a Map<String, Object>
		Map<String, Object> map = (Map<String, Object>) objectStream.readObject();
		return map;
	}
}
