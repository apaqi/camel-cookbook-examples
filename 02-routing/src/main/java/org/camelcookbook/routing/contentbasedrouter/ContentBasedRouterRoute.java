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

package org.camelcookbook.routing.contentbasedrouter;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

public class ContentBasedRouterRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("direct:start")
            .choice()
                .when().simple("${body} contains 'Camel'")
                    .to("mock:camel")
                    .process(new Processor() {
                        @Override
                        public void process(Exchange exchange) throws Exception {
                            System.out.println("走到了when分支");
                        }
                    })
                    .log("Camel ${body}")
                .otherwise()
                    .to("mock:other")
                    .process((Exchange exchange)->{
                        System.out.println("走到了otherwise分支");
                    })
                    .log("Other ${body}")
            .end()
            .log("Message ${body}");
    }


}
