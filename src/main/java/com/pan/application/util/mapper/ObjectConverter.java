package com.pan.application.util.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pan.application.exception.impl.ObjectConversionException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
public class ObjectConverter {

  public static final boolean THROWS_EXCEPTION_ON_FAILURE = true;

  @Inject
  public ObjectMapper mapper;

  public <T> T toObject(Object payload, Class<T> clazz) throws ObjectConversionException {
    return toObject(payload, clazz, THROWS_EXCEPTION_ON_FAILURE);
  }

  public <T> T toObject(Object payload, Class<T> clazz, boolean throwException)
      throws ObjectConversionException {
    try {
      String jsonString = mapper.writeValueAsString(payload);
      return mapper.readValue(jsonString, clazz);
    } catch (JsonProcessingException e) {
      if (throwException) {
        throw new ObjectConversionException(clazz.getName(), payload);
      }
    }
    return null;
  }

  public <T> T toObject(String json, Class<T> entity, boolean throwException) {
    try {
      return mapper.readValue(json, entity);
    } catch (JsonProcessingException e) {
      log.error("Falha ao transformar JSON em {}", entity.getName());
      if (throwException) {
        throw new RuntimeException(e);
      }
      return null;
    }
  }

  public <T> T toObject(String json, Class<T> entity) {
    return toObject(json, entity, THROWS_EXCEPTION_ON_FAILURE);
  }

  public <T> List<T> toList(String json, Class<T> entity, boolean throwException) {
    try {
      return mapper.readValue(json, new TypeReference<List<T>>() {
      });
    } catch (JsonProcessingException e) {
      log.error(String.format("Falha ao transformar JSON em List<%s>", entity.getName()));
      if (throwException) {
        throw new RuntimeException(e);
      }
      return List.of();
    }
  }

  public <T> List<T> toList(String json, Class<T> entity) {
    return toList(json, entity, THROWS_EXCEPTION_ON_FAILURE);
  }

  public String toJson(Object object, boolean throwException) {
    try {
      log.trace("Transformando Objeto {} em JSON");
      log.trace("Objeto >> {}", object);
      return mapper.writeValueAsString(object);
    } catch (JsonProcessingException e) {
      log.error("Falha ao transformar Objeto em Json");
      if (throwException) {
        throw new RuntimeException(e);
      }
      return null;
    }
  }

  public String toJson(Object object) {
    return toJson(object, THROWS_EXCEPTION_ON_FAILURE);
  }

}
