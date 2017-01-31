package dk.fitfit.event.domain;

import javax.persistence.*;

@Entity
public class Event {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String type;
	private long timestamp;
	@Lob
	private byte[] payload;

	public Event() {
	}

	public Event(String type, long timestamp, byte[] payload) {
		this.type = type;
		this.timestamp = timestamp;
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

	public byte[] getPayload() {
		return payload;
	}

	public void setPayload(byte[] payload) {
		this.payload = payload;
	}
}
