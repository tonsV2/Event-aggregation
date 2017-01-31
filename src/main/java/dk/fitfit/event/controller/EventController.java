package dk.fitfit.event.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dk.fitfit.event.domain.Event;
import dk.fitfit.event.resource.EventResource;
import dk.fitfit.event.service.EventServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class EventController {
	private final EventServiceInterface eventService;
	private ObjectMapper objectMapper;

	@Autowired
	public EventController(EventServiceInterface eventService) {
		this.eventService = eventService;
		objectMapper = new ObjectMapper();
	}

	@PostMapping("/collect")
	public EventResource collect(@RequestBody EventResource eventResource) throws JsonProcessingException {
		Object payload = eventResource.getPayload();
		byte[] payloadAsString = objectMapper.writeValueAsBytes(payload);
		Event save = eventService.save(eventResource.getType(), eventResource.getTimestamp(), payloadAsString);
		return EventResource.of(save);
	}

	@GetMapping("/collect/{id}")
	public EventResource collect(@PathVariable long id) {
		Event event = eventService.findOne(id);
		return EventResource.of(event);
	}
}
