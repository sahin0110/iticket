package az.vtb.iticket.dao.repository;

import az.vtb.iticket.dao.entity.TicketEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface TicketRepository extends CrudRepository<TicketEntity, Long>, JpaSpecificationExecutor<TicketEntity> {

    @EntityGraph(attributePaths = {"event"})
    Page<TicketEntity> findAll(Specification<TicketEntity> specification, @NonNull Pageable pageable);

    boolean existsByEventIdAndRowAndPlace(Long eventId, Integer row, Integer place);

    @Modifying
    @Transactional
    @Query("delete from TicketEntity t where t.event.id in :eventIds")
    void deleteByEventIds(@Param("eventIds") List<Long> eventIds);

    void deleteByEventId(Long eventId);
}
