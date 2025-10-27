package az.vtb.iticket.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public enum ObjectMapperFactory {
    OBJECT_MAPPER;

    private final ObjectMapper objectMapper;

    ObjectMapperFactory() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    public ObjectMapper getInstance() {
        return objectMapper;
    }
}