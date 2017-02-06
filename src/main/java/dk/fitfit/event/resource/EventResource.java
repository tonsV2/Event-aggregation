package dk.fitfit.event.resource;

import dk.fitfit.event.domain.Event;

import java.io.IOException;
import java.util.Map;

public class EventResource {
	private String type;
	private long timestamp;
	private String indexId;
	private Map<String, Object> payload;

	public EventResource() {
	}

	private EventResource(String type, long timestamp, String indexId, Map<String, Object> payload) {
		this.type = type;
		this.timestamp = timestamp;
		this.indexId = indexId;
		this.payload = payload;
	}

	public static EventResource of(Event event) throws IOException, ClassNotFoundException {
		return new EventResource(event.getType(), event.getTimestamp(), event.getIndexId(), event.getPayload());
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

	public String getIndexId() {
		return indexId;
	}

	public void setIndexId(String indexId) {
		this.indexId = indexId;
	}

	public Map<String, Object> getPayload() {
		return payload;
	}

	public void setPayload(Map<String, Object> payload) {
		this.payload = payload;
	}

}
