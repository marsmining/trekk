package trekk.common;

import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class JsonUtil {

    private static final Logger log = LoggerFactory.getLogger(JsonUtil.class);

    private static ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.enable(MapperFeature.DEFAULT_VIEW_INCLUSION);
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    /**
     * Serialize object using view, swallowing exceptions.
     */
    public static String serialize(final Object obj, Class<?> view) {

        try {
            return mapper.writerWithView(view).writeValueAsString(obj);
        } catch (Exception e) {
            log.error("error serializing object", e);
            return null;
        }
    }

    public static JsonNode parse(String doc) {

        try {
            return mapper.readTree(doc);
        } catch (Exception e) {
            log.error("parse failed for: " + doc);
        }
        return null;
    }

    public static JsonNode parse(InputStream in) {

        try {
            return mapper.readTree(in);
        } catch (Exception e) {
            log.error("parse failed!", e);
        }
        return null;
    }

    public static <T> T parse(InputStream in, Class<T> clazz) {

        try {
            return mapper.readValue(in, clazz);
        } catch (Exception e) {
            log.error("parse failed!", e);
        }
        return null;
    }

    public static String serialize(JsonNode node) {

        try {
            return mapper.writeValueAsString(node);
        } catch (Exception e) {
            log.error("serialization error", e);
        }
        return null;
    }

    public static String serializeBean(Object bean) {

        try {
            return mapper.writeValueAsString(bean);
        } catch (Exception e) {
            log.error("serialization error", e);
        }
        return null;
    }

    public static ObjectNode createObjectNode() {
        return mapper.createObjectNode();
    }

    public static ArrayNode createArrayNode() {
        return mapper.createArrayNode();
    }

    public static ObjectMapper getMapper() {
        return mapper;
    }
}
