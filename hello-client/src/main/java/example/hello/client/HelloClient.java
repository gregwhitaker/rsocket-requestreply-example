package example.hello.client;

import io.rsocket.Payload;
import io.rsocket.RSocket;
import io.rsocket.RSocketFactory;
import io.rsocket.transport.netty.client.TcpClientTransport;
import io.rsocket.util.DefaultPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

/**
 * Client that sends a name to the hello-service and receives a hello message response.
 */
public class HelloClient {
    private static final Logger LOG = LoggerFactory.getLogger(HelloClient.class);

    public static void main(String... args) throws Exception {
        final String name = getNameFromArgs(args);

        RSocket rSocket = RSocketFactory.connect()
                .transport(TcpClientTransport.create(7000))
                .start()
                .block();

        CountDownLatch latch = new CountDownLatch(1);

        // Sending the request
        rSocket.requestResponse(DefaultPayload.create(name))
                .map(Payload::getDataUtf8)
                .subscribe(msg -> {
                    // Handling the response
                    LOG.info("Response: {}", msg);
                    latch.countDown();
                });

        latch.await();
    }

    private static String getNameFromArgs(String... args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("parameter 0 must be the name of the hello message recipient");
        }

        return args[0];
    }
}
