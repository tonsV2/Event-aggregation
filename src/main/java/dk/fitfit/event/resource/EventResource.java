package dk.fitfit.event.resource;

import dk.fitfit.event.domain.Event;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Map;

public class EventResource {
	private String type;
	private long timestamp;
	private Map<String, Object> payload;

	public EventResource() {
	}

	private EventResource(String type, long timestamp, Map<String, Object> payload) {
		this.type = type;
		this.timestamp = timestamp;
		this.payload = payload;
	}

	// TODO: This does not belong here...
	public static EventResource of(Event event) throws IOException, ClassNotFoundException {
		ByteArrayInputStream ba = new ByteArrayInputStream(event.getPayload());
		ObjectInputStream oba = new ObjectInputStream(ba);
		Map<String, Object> payload = (Map<String, Object>) oba.readObject();
		return new EventResource(event.getType(), event.getTimestamp(), payload);
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

	public Map<String, Object> getPayload() {
		return payload;
	}

	public void setPayload(Map<String, Object> payload) {
		this.payload = payload;
	}

}
