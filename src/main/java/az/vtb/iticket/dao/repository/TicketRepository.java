package az.vtb.iticket.dao.repository;

import az.vtb.iticket.dao.entity.TicketEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface TicketRepository extends JpaRepository<TicketEntity, Long>, JpaSpecificationExecutor<TicketEntity> {

    @EntityGraph(attributePaths = {"event"})
    Page<TicketEntity> findAll(Specification<TicketEntity> specification, Pageable pageable);

    boolean existsByEventIdAndRowAndPlace(Long eventId, Integer row, Integer place);
}
