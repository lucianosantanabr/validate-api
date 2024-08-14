package com.pan.application.util;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.pan.application.util.mapper.ObjectConverter;
import com.pan.domain.resource.payload.TransactionRequest;
import io.quarkus.test.junit.QuarkusTest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class ObjectConverterTest {

  ObjectConverter converter;

  @BeforeEach
  public void init() {
    this.converter = new ObjectConverter();
    this.converter.mapper = new ObjectMapper();

    JavaTimeModule module = new JavaTimeModule();
    LocalDateTimeDeserializer localDateTimeDeserializer = new LocalDateTimeDeserializer(
        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SX"));
    module.addDeserializer(LocalDateTime.class, localDateTimeDeserializer);
    converter.mapper.registerModules(new JavaTimeModule())
        .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
  }

  @Test
  @SneakyThrows
  public void testCase_toObject_01() {
    Object obj = new TransactionRequest("123", "44444444", 100, LocalDateTime.now(), "VISA");

    TransactionRequest res = this.converter.toObject(obj, TransactionRequest.class);
    assertNotNull(res);
  }


  @Test
  @SneakyThrows
  public void testCase_toObject_02() {
    String obj = "{\"transactionId\":\"123\", \"customerDocument\":\"44444444\", \"transactionValue\":100, \"transactionId\":\"ViSA\"}";

    TransactionRequest res = this.converter.toObject(obj, TransactionRequest.class);
    assertNotNull(res);
  }

  @Test
  public void testCase_toObject_03() {
    String obj = "ababababa";

    assertThrows(RuntimeException.class, () -> {
      this.converter.toObject(obj, TransactionRequest.class, true);
    });

  }

  @Test
  @SneakyThrows
  public void testCase_toObject_04() {
    String obj = "ababababa";

    TransactionRequest res = this.converter.toObject(obj, TransactionRequest.class, false);
    assertNull(res);
  }


}
