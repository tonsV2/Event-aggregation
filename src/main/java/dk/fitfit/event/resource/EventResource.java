package dk.fitfit.event.resource;

import dk.fitfit.event.domain.Event;

import java.util.Map;

public class EventResource {
	private String type;
	private long timestamp;
	private String objectId;
	private Map<String, Object> payload;

	public EventResource() {
	}

	private EventResource(String type, long timestamp, String objectId, Map<String, Object> payload) {
		this.type = type;
		this.timestamp = timestamp;
		this.objectId = objectId;
		this.payload = payload;
	}

	public static EventResource of(Event event) {
		return new EventResource(event.getType(), event.getTimestamp(), event.getObjectId(), event.getPayload());
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
		return payload;
	}

	public void setPayload(Map<String, Object> payload) {
		this.payload = payload;
	}

}
