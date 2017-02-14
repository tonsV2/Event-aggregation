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
	private final Map<String, Object> spanStorage = new HashMap<>();

	@Autowired
	public EventController(EventServiceInterface eventService) {
		this.eventService = eventService;
	}

	@PostMapping("/events")
	public EventResource collect(@RequestBody EventResource eventResource) {
		Event save = eventService.save(eventResource.getType(), eventResource.getTimestamp(), eventResource.getObjectId(), eventResource.getPayload());
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
		Map<String, String> spanCmd = (Map<String, String>) aggregation.get("span");
		String target = spanCmd.get("target");
		int limit = Integer.parseInt(spanCmd.get("limit"));

		List<String> objectIds = eventService.findObjectIdsByType(eventType);
		objectIds.forEach(oid -> {
			Stream<Event> events = eventService.findByObjectId(oid);
			Map<String, List<Map<String, String>>> span = getSpans(target, limit, events);
			storeSpan(span);
		});

		Map<String, Object> result = new HashMap<>();
		result.put("type", eventType);
		result.put("spans", getStoredSpans());
		return result;
	}

	private Map<String, Object> getStoredSpans() {
		return spanStorage;
	}

	private void storeSpan(Map<String, List<Map<String, String>>> span) {
		spanStorage.putAll(span);
	}

	private Map<String, List<Map<String, String>>> getSpans(String target, int limit, Stream<Event> eventStream) {
		Map<String, List<Map<String, String>>> spans = new HashMap<>();
		eventStream.forEach(event -> {
			Map<String, Object> payload = event.getPayload();
			List<Map<String, String>> indices = spans.get(event.getObjectId());
			if (indices == null) {
				Map<String, String> spanMap = new HashMap<>();
				spanMap.put("begin", payload.get(target).toString());
				spanMap.put("duration", payload.get(target).toString());
				spans.put(event.getObjectId(), Lists.newArrayList(spanMap));
			} else {
				Map<String, String> lastEntry = indices.remove(indices.size() - 1);
				int lastTimestamp = Integer.parseInt(lastEntry.get("begin"));
				int currentTimestamp = Integer.parseInt(payload.get(target).toString());
				if (currentTimestamp - lastTimestamp < limit) {
					lastEntry.put("begin", lastEntry.get("begin"));
					lastEntry.put("duration", currentTimestamp + "");
					indices.add(lastEntry);
					spans.put(event.getObjectId(), indices);
				} else {
					indices.add(lastEntry);
					Map<String, String> spanMap = new HashMap<>();
					spanMap.put("begin", payload.get(target).toString());
					spanMap.put("duration", payload.get(target).toString());
					indices.add(spanMap);
					spans.put(event.getObjectId(), indices);
				}
			}
		});
		return spans;
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
			Map<String, Long> countByCollection = getEventStream(eventType).collect(groupingBy(Event::getObjectId, counting()));
			result.put("countBy", countByCollection);
		}

		// Map
		@SuppressWarnings("unchecked")
		List<String> mapCmd = (List<String>) aggregation.get("map");
		if (mapCmd != null) {
			Map<String, Map<String, Long>> hashMap = new HashMap<>();
			getEventStream(eventType).forEach(event -> {
				Map<String, Object> payload = event.getPayload();
				String key = getKey(mapCmd, payload);
				Map<String, Long> o = hashMap.get(event.getObjectId());
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
				hashMap.put(event.getObjectId(), o);
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
		// TODO: Wrap this in a class... abstraction... autowire
		ObjectMapper mapper = new ObjectMapper();
		@SuppressWarnings("unchecked")
		Map<String, Object> map = mapper.readValue(url, Map.class);
		return map;
	}

	private Stream<Event> getEventStream(String eventType) {
		Supplier<Stream<Event>> events = () -> eventService.findByType(eventType);
		return events.get();
	}
}
