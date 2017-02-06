package dk.fitfit.event.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Resources;
import dk.fitfit.event.domain.Event;
import dk.fitfit.event.resource.EventResource;
import dk.fitfit.event.service.EventServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

@RestController
public class EventController {
	private final EventServiceInterface eventService;
	// TODO: Wrap this in a class... abstraction
	private ObjectMapper mapper = new ObjectMapper();

	@Autowired
	public EventController(EventServiceInterface eventService) {
		this.eventService = eventService;
	}

	@PostMapping("/events")
	public EventResource collect(@RequestBody EventResource eventResource) throws IOException, ClassNotFoundException {
		Event save = eventService.save(eventResource.getType(), eventResource.getTimestamp(), eventResource.getIndexId(), eventResource.getPayload());
		return EventResource.of(save);
	}

	@GetMapping("/events/{id}")
	public EventResource getEvents(@PathVariable long id) throws IOException, ClassNotFoundException {
		Event event = eventService.findOne(id);
		return EventResource.of(event);
	}

	@GetMapping("/events")
	public Map<String, Object> getEventsByStream() throws IOException, ClassNotFoundException {
		Map<String, Object> aggregation = readDsl();
		String eventType = aggregation.get("type").toString();
		Supplier<Stream<Event>> events = () -> eventService.findByType(eventType);

		Map<String, Object> result = new HashMap<>();
		result.put("type", eventType);

		// Count
		String count = aggregation.get("count").toString();
		if (count != null && count.equals("*")) {
			long totalEventCount = events.get().count();
			result.put("count", totalEventCount);
		}

		// CountBy
		String countBy = aggregation.get("countBy").toString();
		if (countBy != null && countBy.equals("*")) {
			Map<String, Long> countByCollection = events.get().collect(groupingBy(Event::getIndexId, counting()));
			result.put("countBy", countByCollection);
		}

		// Map
		@SuppressWarnings("unchecked")
		List<String> map = (List<String>) aggregation.get("map");
		if (map != null) {
			Map<String, Map<String, Long>> hashMap = new HashMap<>();
			events.get().forEach(event -> {
				Map<String, Object> payload = event.getPayload();
				String key = getKey(map, payload);
				Map<String, Long> o = hashMap.get(event.getIndexId());
				if(o == null) {
					o = new HashMap<>();
					o.put(key, 1L);
				} else {
					Long countByIndex = o.get(key);
					if (countByIndex == null) {
						o.put(key, 1L);
					} else {
						o.put(key, 1L + countByIndex);
					}
				}
				hashMap.put(event.getIndexId(), o);
			});
			result.put("map", hashMap);
		}

		return result;
	}

	private Map<String, Object> readDsl() throws IOException {
		String resourceName = "dsl/aggregation/imgAgg.json";
		URL url = Resources.getResource(resourceName);
		@SuppressWarnings("unchecked")
		Map<String, Object> map = mapper.readValue(url, Map.class);
		return map;
	}

	private String getKey(List<String> indices, Map<String, Object> payload) {
		return indices.stream()
				.map(k -> payload.get(k).toString())
				.collect(Collectors.joining(","));
	}
}
