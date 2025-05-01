package az.vtb.iticket.mapper;

import az.vtb.iticket.dao.entity.EventEntity;
import az.vtb.iticket.model.criteria.EventCriteria;
import az.vtb.iticket.model.request.CreateEventRequest;
import az.vtb.iticket.model.response.EventResponse;
import az.vtb.iticket.service.specification.EventSpecification;


public enum EventMapper {
    EVENT_MAPPER;

    public EventEntity toEventEntity(CreateEventRequest eventRequest) {
        return EventEntity.builder()
                .name(eventRequest.getName())
                .description(eventRequest.getDescription())
                .location(eventRequest.getLocation())
                .category(eventRequest.getCategory())
                .startTime(eventRequest.getStartTime())
                .endTime(eventRequest.getEndTime())
                .build();
    }

    public EventResponse toEventResponse(EventEntity eventEntity) {
        return EventResponse.builder()
                .id(eventEntity.getId())
                .name(eventEntity.getName())
                .description(eventEntity.getDescription())
                .location(eventEntity.getLocation())
                .category(eventEntity.getCategory())
                .startTime(eventEntity.getStartTime())
                .endTime(eventEntity.getEndTime())
                .build();
    }

    public EventSpecification toEventSpecification(EventCriteria eventCriteria) {
        return new EventSpecification(eventCriteria);
    }
}
