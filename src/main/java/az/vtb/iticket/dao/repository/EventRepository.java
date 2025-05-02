package az.vtb.iticket.dao.repository;

import az.vtb.iticket.dao.entity.EventEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends CrudRepository<EventEntity, Long> {

    Page<EventEntity> findAll(Specification<EventEntity> specification, @NonNull Pageable pageable);

    List<EventEntity> findEventEntityByEndTimeIsBefore(LocalDateTime endTimeBefore);
}
