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

import io.rsocket.AbstractRSocket;
import io.rsocket.Payload;
import io.rsocket.RSocketFactory;
import io.rsocket.transport.netty.server.TcpServerTransport;
import io.rsocket.util.PayloadImpl;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

public class Pong {

    private final String bindAddress;
    private final int port;

    public Pong() {
        this("localhost", 8080);
    }

    public Pong(final String bindAddress, final int port) {
        this.bindAddress = bindAddress;
        this.port = port;
    }

    public void start() {
        RSocketFactory
                .receive()
                .acceptor((setupPayload, reactiveSocket) ->
                    Mono.just(new AbstractRSocket() {
                        @Override
                        public Mono<Payload> requestResponse(Payload p) {
                            String count = StandardCharsets.UTF_8.decode(p.getData()).toString().split(" ")[1];
                            return Mono.just(new PayloadImpl("Pong: " + count));
                        }
                    })
                )
                .transport(TcpServerTransport.create("localhost", 8080))
                .start()
                .block();
    }
}
