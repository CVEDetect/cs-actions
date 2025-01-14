/*
  * (c) Copyright 2022 Micro Focus
  * All rights reserved. This program and the accompanying materials
  * are made available under the terms of the Apache License v2.0 which accompany this distribution.
  *
  * The Apache License is available at
  * http://www.apache.org/licenses/LICENSE-2.0
  *
  * Unless required by applicable law or agreed to in writing, software
  * distributed under the License is distributed on an "AS IS" BASIS,
  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  * See the License for the specific language governing permissions and
  * limitations under the License.
 */




package io.cloudslang.content.httpclient.consume;

import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * User: Adina Tusa
 * Date: 8/20/14
 */
public class StatusConsumerTest {

    private StatusConsumer statusConsumer;

    @Before
    public void setUp() {
        statusConsumer = new StatusConsumer();
    }

    @Test
    public void consume() {
        Map<String, String> returnResult = new HashMap<>();
        StatusLine statusLine = new StatusLine() {
            @Override
            public ProtocolVersion getProtocolVersion() {
                return new ProtocolVersion("HTTP", 1, 1);
            }

            @Override
            public int getStatusCode() {
                return 200;
            }

            @Override
            public String getReasonPhrase() {
                return "OK";
            }
        };
        statusConsumer.setStatusLine(statusLine).consume(returnResult);
    }

    @Test
    public void consumeWithNulls() {
        Map<String, String> returnResult = new HashMap<>();
        statusConsumer.setStatusLine(null).consume(returnResult);
    }
}
