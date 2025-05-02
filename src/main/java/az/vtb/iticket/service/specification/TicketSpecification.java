package az.vtb.iticket.service.specification;

import az.vtb.iticket.dao.entity.TicketEntity;
import az.vtb.iticket.model.criteria.TicketCriteria;
import az.vtb.iticket.util.PredicateUtil;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import static az.vtb.iticket.dao.entity.TicketEntity.Fields.place;
import static az.vtb.iticket.dao.entity.TicketEntity.Fields.row;
import static az.vtb.iticket.dao.entity.TicketEntity.Fields.price;

@RequiredArgsConstructor
public class TicketSpecification implements Specification<TicketEntity> {

    private final TicketCriteria ticketCriteria;

    @Override
    public Predicate toPredicate(@NonNull Root<TicketEntity> root,
                                 CriteriaQuery<?> query,
                                 @NonNull CriteriaBuilder cb) {
        var predicates = PredicateUtil.builder()
                .addNullSafety(ticketCriteria.getRow(),
                        it -> cb.equal(root.get(row), it)
                )
                .addNullSafety(ticketCriteria.getPlace(),
                        it -> cb.equal(root.get(place), it)
                )
                .addNullSafety(ticketCriteria.getEventId(),
                        it -> cb.equal(root.get("event").get("id"), it)
                )
                .addNullSafety(ticketCriteria.getMinPrice(),
                        it -> cb.greaterThanOrEqualTo(root.get(price), it)
                )
                .addNullSafety(ticketCriteria.getMaxPrice(),
                        it -> cb.lessThanOrEqualTo(root.get(price), it)
                )

                .build();
        return cb.and(predicates);
    }
}
