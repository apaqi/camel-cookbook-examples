/*
 * Copyright (C) Scott Cranton, Jakub Korab, and Christian Posta
 * https://github.com/CamelCookbook
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.camelcookbook.ws.client;

import org.apache.camel.builder.RouteBuilder;
import org.camelcookbook.ws.payment_service.Payment;

public class ClientRoute extends RouteBuilder {
    private int port1;

    public ClientRoute() {
    }

    public ClientRoute(int port1) {
        this.port1 = port1;
    }

    public void setPort1(int port1) {
        this.port1 = port1;
    }

    @Override
    public void configure() throws Exception {
        final String cxfUri =
            String.format("cxf:http://localhost:%d/paymentService?serviceClass=%s",
                port1, Payment.class.getName());

        from("direct:start")
                .id("wsClient")
            .log("${body}")
            .to(cxfUri + "&defaultOperationName=transferFunds");
    }
}
