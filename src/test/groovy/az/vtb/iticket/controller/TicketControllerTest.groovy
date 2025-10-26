package az.vtb.iticket.controller

import az.vtb.iticket.exception.ErrorHandler
import az.vtb.iticket.model.criteria.PageCriteria
import az.vtb.iticket.model.criteria.TicketCriteria
import az.vtb.iticket.model.request.CreateTicketRequest
import az.vtb.iticket.model.request.UpdateTicketRequest
import az.vtb.iticket.model.response.PageableResponse
import az.vtb.iticket.model.response.TicketResponse
import az.vtb.iticket.service.abstraction.TicketService
import org.skyscreamer.jsonassert.JSONAssert
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class TicketControllerTest extends Specification {

    TicketService ticketService
    TicketController ticketController
    MockMvc mockMvc

    def setup() {
        ticketService = Mock()
        ticketController = new TicketController(ticketService)
        mockMvc = MockMvcBuilders.standaloneSetup(ticketController)
                .setControllerAdvice(ErrorHandler.class)
                .build()
    }

    def "TestCreateTicket success case"() {
        given:
        def url = "/v1/tickets"

        def createTicketRequest = CreateTicketRequest.builder()
                .price(10.0)
                .row(1)
                .place(1)
                .eventId(1L)
                .build()

        def jsonRequest =
                """
                {
                    "price": 10.0,
                    "row": 1,
                    "place": 1,
                    "eventId": 1
                }
                """

        when:
        def jsonResponse = mockMvc.perform(post(url)
                .contentType(APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isCreated())
                .andReturn()

        then:
        1 * ticketService.saveTicket(createTicketRequest)
        jsonResponse.response.status == CREATED.value()
    }

    def "TestGetAllTickets success case"() {
        given:
        def url = "/v1/tickets"
        def totalPages = 0
        def totalElements = 2

        def ticket1 = TicketResponse.builder()
                .id(1)
                .price(10.0)
                .row(1)
                .place(1)
                .build()

        def ticket2 = TicketResponse.builder()
                .id(2)
                .price(20.0)
                .row(2)
                .place(2)
                .build()


        def pageResponse = new PageableResponse<TicketResponse>(
                content: [ticket1, ticket2],
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
        1 * ticketService.getAllTickets(_ as PageCriteria, _ as TicketCriteria) >> pageResponse
    }

    def "TestGetById success case"() {
        given:
        def ticketId = 1L
        def url = "/v1/tickets/$ticketId"

        def ticketResponse = TicketResponse.builder()
                .id(1L)
                .price(10.0)
                .row(1)
                .place(1)
                .event(null)
                .build()

        def expectedResponse =
                """
                   {
                    "id": 1,
                    "price": 10.0,
                    "row": 1,
                    "place": 1,
                    "event": null
                   }
                """

        when:
        def jsonResponse = mockMvc
                .perform(get(url)
                .contentType(APPLICATION_JSON))
                .andReturn()

        then:
        1 * ticketService.getTicketById(ticketId) >> ticketResponse
        jsonResponse.response.status == OK.value()
        JSONAssert.assertEquals(expectedResponse.toString(), jsonResponse.response.contentAsString.toString(), true)
    }

    def "TestUpdateTicketById success case"() {
        given:
        def ticketId = 1L
        def url = "/v1/tickets/$ticketId"

        UpdateTicketRequest.builder()
                .price(10.0)
                .place(1)
                .row(1)
                .eventId(1L)
                .build()

        def jsonRequest =
                """
                    {
                    "price": 10.0,
                    "place": 1,
                    "row": 1,
                    "eventId": 1
                    }
                """

        when:
        mockMvc.perform(put(url)
                .contentType(APPLICATION_JSON)
                .content(jsonRequest))
                .andReturn()

        then:
        1 * ticketService.updateTicket(ticketId, _ as UpdateTicketRequest)
    }

    def "TestDeleteTicketById success case"() {
        given:
        def ticketId = 1L
        def url = "/v1/tickets/$ticketId"

        when:
        mockMvc.perform(delete(url)
                .contentType(APPLICATION_JSON)).andReturn()

        then:
        1 * ticketService.deleteTicket(ticketId)
    }
}
