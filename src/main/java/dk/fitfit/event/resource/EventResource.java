package dk.fitfit.event.resource;

import dk.fitfit.event.domain.Event;

public class EventResource {
	private String type;
	private long timestamp;
	private Object payload;

	public EventResource() {
	}

	private EventResource(String type, long timestamp, String payload) {
		this.type = type;
		this.timestamp = timestamp;
		this.payload = payload;
	}

	public static EventResource of(Event event) {
		return new EventResource(event.getType(), event.getTimestamp(), event.getPayload().toString());
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

	public Object getPayload() {
		return payload;
	}

	public void setPayload(Object payload) {
		this.payload = payload;
	}

}
