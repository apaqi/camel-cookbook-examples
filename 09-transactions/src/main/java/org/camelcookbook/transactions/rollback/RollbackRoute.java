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

package org.camelcookbook.transactions.rollback;

import org.apache.camel.RollbackExchangeException;
import org.apache.camel.builder.RouteBuilder;

/**
 * Demonstrates the use of the rollback statement to throw an Exception, that will roll back the transaction
 */
public class RollbackRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("direct:transacted")
            .onException(RollbackExchangeException.class)
                .log("Caught rollback :P")
            .end()
            .transacted()
            .log("Processing message: ${body}")
            .setHeader("message", body())
            .to("sql:insert into audit_log (message) values (:#message)")
            .choice()
                .when(simple("${body} contains 'explode'"))
                    .log("Message cannot be processed further - rolling back insert")
                    .rollback("Message contained word 'explode'")
                .otherwise()
                    .log("Message processed successfully")
                    .to("mock:out");
    }
}
