package az.vtb.iticket.mapper

import az.vtb.iticket.dao.entity.EventEntity
import az.vtb.iticket.model.criteria.EventCriteria
import az.vtb.iticket.model.request.CreateEventRequest
import az.vtb.iticket.service.specification.EventSpecification
import io.github.benas.randombeans.EnhancedRandomBuilder
import io.github.benas.randombeans.api.EnhancedRandom
import spock.lang.Specification

import static az.vtb.iticket.mapper.EventMapper.EVENT_MAPPER

class EventMapperTest extends Specification {

    EnhancedRandom random = EnhancedRandomBuilder.aNewEnhancedRandom()

    def "TestMapRequestToEntity"() {
        given:
        def eventRequest = random.nextObject(CreateEventRequest)

        when:
        def eventEntity = EVENT_MAPPER.toEventEntity(eventRequest)

        then:
        eventRequest.name == eventEntity.name
        eventRequest.description == eventEntity.description
        eventRequest.location == eventEntity.location
        eventRequest.endTime == eventEntity.endTime
        eventRequest.startTime == eventEntity.startTime
        eventRequest.category == eventEntity.category
    }

    def "TestMapEntityToResponse"() {
        given:
        def eventEntity = random.nextObject(EventEntity)

        when:
        def eventResponse = EVENT_MAPPER.toEventResponse(eventEntity)

        then:
        eventEntity.id == eventResponse.id
        eventEntity.name == eventResponse.name
        eventEntity.description == eventResponse.description
        eventEntity.category == eventResponse.category
        eventEntity.location == eventResponse.location
        eventEntity.startTime == eventResponse.startTime
        eventEntity.endTime == eventResponse.endTime
    }

    def "TestSpecificationEvent"() {
        given:
        def eventCriteria = random.nextObject(EventCriteria)

        when:
        def specification = EVENT_MAPPER.toEventSpecification(eventCriteria)

        then:
        specification != null
        specification instanceof EventSpecification
    }
}
