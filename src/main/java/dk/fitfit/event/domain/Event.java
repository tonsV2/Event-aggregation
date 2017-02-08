package dk.fitfit.event.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.*;
import java.io.IOException;
import java.util.Map;

@Entity
public class Event {
	@Transient
	private ObjectMapper objectMapper = new ObjectMapper();

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String type;
	// TODO: Be more specific here... ts of creation, receive or something else
	private long timestamp;
	private String objectId;

// TODO: This defaults to varchar(255)... which probably is a bit too short... and the wrong type (should probably be byte[])... Time will tell
//	@Column(columnDefinition = "TEXT")
	private String payload;

	@Transient
	private Map<String, Object> payloadObj;

	public Event() {
	}

	public Event(String type, long timestamp, String objectId, Map<String, Object> payload) {
		this.type = type;
		this.timestamp = timestamp;
		this.objectId = objectId;
		this.payloadObj = payload;
	}

	@PrePersist
	public void payloadMapToText() throws JsonProcessingException {
		this.payload = mapToString(this.payloadObj);
	}

	@PostLoad
	public void payloadTextToMap() throws IOException {
		this.payloadObj = stringToMap(this.payload);
	}

	// TODO: Make conversion interface and wrap these (toByteArray, toMap) helper methods in some classes... autowire, generics
	// Move objectMapper and other logic away from this pojo... object mapper should be autowired conversionInterface thing
	private Map<String, Object> stringToMap(String payload) throws IOException {
		@SuppressWarnings("unchecked")
		Map<String, Object> map = objectMapper.readValue(payload, Map.class);
		return map;
	}

	private String mapToString(Map<String, Object> payloadOb) throws JsonProcessingException {
		return objectMapper.writeValueAsString(payloadOb);
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

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public Map<String, Object> getPayload() {
		return payloadObj;
	}

	public void setPayload(Map<String, Object> payloadOb) {
		this.payloadObj = payloadOb;
	}
}

/*
@Entity
public class Event {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String type;
	private long timestamp;
	@Lob
	@MapKeyColumn(name = "id")
	@Column(name = "payload")
	@ElementCollection
	private Map<String, Object> payload;

	// Needed by hibernate
	public Event() {
	}

	public Event(String type, long timestamp, Map<String, Object> payload) throws IOException {
		this.type = type;
		this.timestamp = timestamp;
//		this.payload = toByteArray(payload);
		this.payload = payload;
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
//		return toMap(payload);
		return payload;
	}

	public void setPayload(Map<String, Object> payload) throws IOException {
//		this.payload = toByteArray(payload);
		this.payload = payload;
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
*/
