package az.vtb.iticket.controller

import az.vtb.iticket.exception.ErrorHandler
import az.vtb.iticket.model.criteria.EventCriteria
import az.vtb.iticket.model.criteria.PageCriteria
import az.vtb.iticket.model.request.CreateEventRequest
import az.vtb.iticket.model.response.EventResponse
import az.vtb.iticket.model.response.PageableResponse
import az.vtb.iticket.service.abstraction.EventService
import org.skyscreamer.jsonassert.JSONAssert
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static az.vtb.iticket.model.enums.Category.SPORT
import static java.time.LocalDateTime.now
import static java.time.format.DateTimeFormatter.ofPattern
import static java.time.temporal.ChronoUnit.MINUTES
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class EventControllerTest extends Specification {

    private EventService eventService
    private EventController eventController
    private MockMvc mockMvc

    def setup() {
        eventService = Mock()
        eventController = new EventController(eventService)
        mockMvc = MockMvcBuilders.standaloneSetup(eventController)
                .setControllerAdvice(ErrorHandler.class)
                .build()
    }

    def "CreateEvent success case"() {
        given:
        var url = "/v1/events"
        def formatter = ofPattern("yyyy-MM-dd HH:mm")
        def now = now()
        def startTime = now.plusHours(1).truncatedTo(MINUTES)
        def endTime = now.plusHours(2).truncatedTo(MINUTES)

        def createEventRequest = CreateEventRequest.builder()
                .name("name")
                .description("description")
                .location("location")
                .category(SPORT)
                .startTime(startTime)
                .endTime(endTime)
                .build()

        def jsonRequest =
                """
                    {
                    "name": "name",
                    "description": "description",
                    "location": "location",
                    "category": "SPORT",
                    "startTime": "${startTime.format(formatter)}",
                    "endTime": "${endTime.format(formatter)}"
                    }
                """
        when:
        def jsonResponse = mockMvc.perform(post(url)
                .contentType(APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isCreated())
                .andReturn()

        then:
        1 * eventService.createEvent(createEventRequest)
        jsonResponse.response.status == CREATED.value()
    }

    def "GetAllEvents success case"() {
        given:
        def url = "/v1/events"
        def totalPages = 0
        def totalElements = 2
        def now = now()
        def startTime = now.plusHours(1).truncatedTo(MINUTES)
        def endTime = now.plusHours(2).truncatedTo(MINUTES)

        def event1 = EventResponse.builder()
                .id(1)
                .name("name1")
                .description("description1")
                .location("location1")
                .category(SPORT)
                .startTime(startTime)
                .endTime(endTime)
                .build()

        def event2 = EventResponse.builder()
                .id(2)
                .name("name2")
                .description("description2")
                .location("location2")
                .category(SPORT)
                .startTime(startTime)
                .endTime(startTime)
                .build()

        def pageResponse = new PageableResponse<EventResponse>(
                content: [event1, event2],
                totalPages: totalPages,
                totalElements: totalElements
        )

        when:
        mockMvc.perform(get(url)
                .param("page", totalPages.toString())
                .param("elements", totalElements.toString())
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()

        then:
        1 * eventService.getAllEvents(_ as PageCriteria, _ as EventCriteria) >> pageResponse
    }

    def "TestGetById success case"() {
        given:
        def id = 1L
        def url = "/v1/events/$id"
        def now = now()
        def startTime = now.plusHours(1).truncatedTo(MINUTES)
        def endTime = now.plusHours(2).truncatedTo(MINUTES)
        var formatter = ofPattern("yyyy-MM-dd HH:mm")
        def eventResponse = EventResponse.builder()
                .id(id)
                .name("name")
                .description("description")
                .location("location")
                .category(SPORT)
                .startTime(startTime)
                .endTime(endTime)
                .build()
        def expectedResponse =
                """
                    {
                    "id": 1,
                    "name": "name",
                    "description": "description",
                    "location": "location",
                    "category": "SPORT",
                    "startTime": "${startTime.format(formatter)}",
                    "endTime": "${endTime.format(formatter)}"
                    }
                """
        when:
        def jsonResponse = mockMvc
                .perform(get(url)
                .contentType(APPLICATION_JSON))
                .andReturn()

        then:
        1 * eventService.getEventById(id) >> eventResponse
        jsonResponse.response.status == OK.value()
        JSONAssert.assertEquals(expectedResponse.toString(), jsonResponse.response.contentAsString.toString(), true)
    }

    def "TestDeleteById"() {
        given:
        def id = 1L
        def url = "/v1/events/$id"

        when:
        mockMvc.perform(delete(url)
                .contentType(APPLICATION_JSON))
                .andReturn()

        then:
        1 * eventService.deleteEvent(id)
    }
}