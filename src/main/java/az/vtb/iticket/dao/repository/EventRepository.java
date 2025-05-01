package az.vtb.iticket.dao.repository;

import az.vtb.iticket.dao.entity.EventEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<EventEntity, Long> {

    Page<EventEntity> findAll(Specification<EventEntity> specification, Pageable pageable);

    List<EventEntity> findEventEntityByEndTimeIsBefore(LocalDateTime endTimeBefore);
}
