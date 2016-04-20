package py.una.pol.web.tarea4.rest;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import javax.ws.rs.ext.ContextResolver;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


public class JacksonConfig implements ContextResolver<ObjectMapper> {
    private final ObjectMapper objectMapper;

    public JacksonConfig() {
        objectMapper = new ObjectMapper().configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE, true);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        objectMapper.setDateFormat(df);
    }

    public ObjectMapper getContext(Class<?> arg0) {
        return objectMapper;
    }
}
