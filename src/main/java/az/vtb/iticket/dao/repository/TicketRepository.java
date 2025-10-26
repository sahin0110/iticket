package az.vtb.iticket.dao.repository;

import az.vtb.iticket.dao.entity.TicketEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;


public interface TicketRepository extends CrudRepository<TicketEntity, Long>, JpaSpecificationExecutor<TicketEntity> {

    @EntityGraph(attributePaths = {"event"})
    Page<TicketEntity> findAll(Specification<TicketEntity> specification, @NonNull Pageable pageable);

    boolean existsByEventIdAndRowAndPlace(Long eventId, Integer row, Integer place);
}
