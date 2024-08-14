package com.pan.application.message;

import static com.rabbitmq.client.BuiltinExchangeType.TOPIC;
import static java.nio.charset.StandardCharsets.UTF_8;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pan.application.exception.impl.ObjectConversionException;
import com.pan.domain.resource.payload.TransactionRequest;
import com.pan.domain.service.AdviceService;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import static java.util.concurrent.TimeUnit.*;

@QuarkusTest
public class ConsumerMessageTest {

  @ConfigProperty(name = "rabbitmq-host")
  String host;
  @ConfigProperty(name = "rabbitmq-port")
  int port;
  ObjectMapper objectMapper = new ObjectMapper();


  @InjectMock
  AdviceService adviceService;;

  @Inject
  ConsumerMessage consumerMessage;

  @Test
  void testProcessor() throws Exception {
    String quoteId = UUID.randomUUID().toString();

    Channel channel = getChannel();

    channel.exchangeDeclare("credit-advice", TOPIC, true, false, Map.of());

    String queue = channel.queueDeclare("credit-advice", true, false, false, Map.of())
        .getQueue();
    channel.queueBind(queue, "credit-advice", "#");

    AtomicReference<TransactionRequest> receive = new AtomicReference<>(mockTransaction());

    DeliverCallback deliverCallback = (consumerTag, message) -> {
      TransactionRequest request = objectMapper
          .readValue(mockTransaction().toString(), TransactionRequest.class);
      if (!Objects.equals(request.brand(), "VISA")) {
        return;
      }
      receive.set(request);
    };

    String consumerTag = channel.basicConsume(queue, true, deliverCallback, tag -> {});

    AMQP.BasicProperties props = new AMQP.BasicProperties.Builder()
        .contentType("application/json")
        .build();
    channel.basicPublish("credit-advice", mockTransaction().toString(), props, quoteId.getBytes(UTF_8));

    when(adviceService.credit(any())).thenReturn("Message ok");

    channel.basicCancel(consumerTag);
  }

  Channel getChannel() throws Exception {
    ConnectionFactory connectionFactory = new ConnectionFactory();
    connectionFactory.setHost(host);
    connectionFactory.setPort(port);
    connectionFactory.setUsername("guest");
    connectionFactory.setPassword("guest");
    connectionFactory.setChannelRpcTimeout((int) SECONDS.toMillis(3));

    Connection connection = connectionFactory.newConnection();
    return connection.createChannel();
  }

  private TransactionRequest mockTransaction() {
    return new TransactionRequest("1234", "44444444444", 100, LocalDateTime.now(), "VISA");
  }


  @Test
  void shouldCallConsumerCreditWithCorrectParameters() throws ObjectConversionException {
    var event = mockTransaction();

    when(adviceService.credit(any())).thenReturn("Message ok");
    consumerMessage.listenerCreditAdvide(event);
  }


  @Test
  void shouldCallConsumerDebitWithCorrectParameters() throws ObjectConversionException {
    var event = mockTransaction();

    when(adviceService.debit(any())).thenReturn("Message ok");
    consumerMessage.listenerDebitAdvide(event);
  }


}