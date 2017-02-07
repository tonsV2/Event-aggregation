package dk.fitfit.event.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
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
	public EventResource collect(@RequestBody EventResource eventResource) {
		Event save = eventService.save(eventResource.getType(), eventResource.getTimestamp(), eventResource.getIndexId(), eventResource.getPayload());
		return EventResource.of(save);
	}

	@GetMapping("/events/{id}")
	public EventResource getEvent(@PathVariable long id) {
		Event event = eventService.findOne(id);
		return EventResource.of(event);
	}

	@GetMapping("/heartbeat")
	public Map<String, Object> getHeartbeat() throws IOException {
		Map<String, Object> aggregation = readDsl("heartbeat");
		String eventType = aggregation.get("type").toString();
		@SuppressWarnings("unchecked")
		Map<String, String> span = (Map<String, String>) aggregation.get("span");
		String target = span.get("target");
		int limit = Integer.parseInt(span.get("limit"));

		Stream<Event> eventStream = getEventStream(eventType);

		Map<String, Object> result = new HashMap<>();
		result.put("type", eventType);

		Map<String, Object> spans = new HashMap<>();

		eventStream.forEach(event -> {
			@SuppressWarnings("unchecked")
			List<Object> indices = (List<Object>) spans.get(event.getIndexId());
			Map<String, Object> payload = event.getPayload();
			if (indices == null) {
				Map<String, String> spanMap = new HashMap<>();
				spanMap.put("begin", payload.get(target).toString());
				spanMap.put("duration", payload.get(target).toString());
				spans.put(event.getIndexId(), Lists.newArrayList(spanMap));
			} else {
				@SuppressWarnings("unchecked")
				Map<String, String> lastEntry = (Map<String, String>) indices.remove(indices.size() - 1);
				int lastTimestamp = Integer.parseInt(lastEntry.get("begin"));
				int currentTimestamp = Integer.parseInt(payload.get(target).toString());
				if (currentTimestamp - lastTimestamp < limit) {
					lastEntry.put("begin", lastEntry.get("begin"));
					lastEntry.put("duration", currentTimestamp + "");
					indices.add(lastEntry);
					spans.put(event.getIndexId(), indices);
				} else {
					indices.add(lastEntry);
					Map<String, String> spanMap = new HashMap<>();
					spanMap.put("begin", payload.get(target).toString());
					spanMap.put("duration", payload.get(target).toString());
					indices.add(spanMap);
					spans.put(event.getIndexId(), indices);
				}
			}
		});

		result.put("spans", spans);
		return result;
	}

	@GetMapping("/imgAgg")
	public Map<String, Object> getImgAgg() throws IOException {
		Map<String, Object> aggregation = readDsl("imgAgg");
		String eventType = aggregation.get("type").toString();

		Map<String, Object> result = new HashMap<>();
		result.put("type", eventType);

		// Count
		String count = aggregation.get("count").toString();
		if (count != null && count.equals("*")) {
			long totalEventCount = getEventStream(eventType).count();
			result.put("count", totalEventCount);
		}

		// CountBy
		String countBy = aggregation.get("countBy").toString();
		if (countBy != null && countBy.equals("*")) {
			Map<String, Long> countByCollection = getEventStream(eventType).collect(groupingBy(Event::getIndexId, counting()));
			result.put("countBy", countByCollection);
		}

		// Map
		@SuppressWarnings("unchecked")
		List<String> map = (List<String>) aggregation.get("map");
		if (map != null) {
			Map<String, Map<String, Long>> hashMap = new HashMap<>();
			getEventStream(eventType).forEach(event -> {
				Map<String, Object> payload = event.getPayload();
				String key = getKey(map, payload);
				Map<String, Long> o = hashMap.get(event.getIndexId());
				if (o == null) {
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

	private String getKey(List<String> indices, Map<String, Object> payload) {
		return indices.stream()
				.map(k -> payload.get(k).toString())
				.collect(Collectors.joining(","));
	}

	private Map<String, Object> readDsl(String dsl) throws IOException {
		String resourceName = "dsl/aggregation/" + dsl + ".json";
		URL url = Resources.getResource(resourceName);
		@SuppressWarnings("unchecked")
		Map<String, Object> map = mapper.readValue(url, Map.class);
		return map;
	}

	private Stream<Event> getEventStream(String eventType) {
		Supplier<Stream<Event>> events = () -> eventService.findByType(eventType);
		return events.get();
	}
}
