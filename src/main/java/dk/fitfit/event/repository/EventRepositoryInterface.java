package dk.fitfit.event.repository;

import dk.fitfit.event.domain.Event;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Stream;

@Repository
public interface EventRepositoryInterface extends CrudRepository<Event, Long> {
	Stream<Event> findByType(String type);

	@Query("SELECT DISTINCT e.objectId FROM Event e WHERE e.type = :type")
	List<String> findDistinctObjectIdByType(@Param("type") String type);

	Stream<Event> findByObjectId(String objectId);
}
