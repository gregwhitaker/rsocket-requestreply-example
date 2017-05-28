/*
 * Copyright 2017 Greg Whitaker
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.gregwhitaker.rsocket.example;

import io.rsocket.RSocket;
import io.rsocket.RSocketFactory;
import io.rsocket.transport.netty.client.TcpClientTransport;
import io.rsocket.util.PayloadImpl;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;

public class Ping {

    private final String bindAddress;
    private final int port;

    public Ping() {
        this("localhost", 8080);
    }

    public Ping(final String bindAddress, final int port) {
        this.bindAddress = bindAddress;
        this.port = port;
    }

    public void start() throws InterruptedException {
        RSocket client = RSocketFactory
                .connect()
                .transport(TcpClientTransport.create(bindAddress, port))
                .start()
                .block();

        CountDownLatch latch = new CountDownLatch(100);

        for (int i = 1; i <= 100; i++) {
            System.out.println("Ping: " + i);
            client.requestResponse(new PayloadImpl("Ping " + i))
                    .subscribe(payload -> {
                        System.out.println(StandardCharsets.UTF_8.decode(payload.getData()).toString());
                        latch.countDown();
                    });
        }

        latch.await();
        System.exit(0);
    }
}
