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

import org.apache.http.HttpHost;
import org.apache.http.client.utils.URIBuilder;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.Assert.assertEquals;

/**
 * User: Adina Tusa
 * Date: 8/19/14
 */
public class FinalLocationConsumerTest {
    private static final String URI = "http://localhost:8080/test";
    private static final String REDIRECT_URI = "http://localhost:8080/redirect";
    private static final String FINAL_LOCATION = "finalLocation";
    private FinalLocationConsumer finalLocationConsumer;

    @Before
    public void setUp() {
        finalLocationConsumer = new FinalLocationConsumer();
    }

    @Test
    public void consume() throws URISyntaxException {
        URI uri = new URIBuilder(URI).build();
        HttpHost httpHost = new HttpHost("16.17.158", 8081);
        List<URI> redirectLocations = new ArrayList<>();
        URI redirectUri = new URIBuilder(REDIRECT_URI).build();
        redirectLocations.add(redirectUri);
        Map<String, String> returnResult = new HashMap<>();
        finalLocationConsumer.setUri(uri)
                .setTargetHost(httpHost)
                .setRedirectLocations(redirectLocations)
                .consume(returnResult);

        assertEquals(REDIRECT_URI, returnResult.get(FINAL_LOCATION));
    }

    @Test
    public void consumeWithNoRedirects() throws URISyntaxException {
        URI uri = new URIBuilder(URI).build();
        Map<String, String> returnResult = new HashMap<>();
        finalLocationConsumer.setUri(uri)
                .setTargetHost(null)
                .setRedirectLocations(null)
                .consume(returnResult);

        assertEquals(URI, returnResult.get(FINAL_LOCATION));
    }

    @Test(expected = IllegalArgumentException.class)
    public void consumeWithNoUri() throws URISyntaxException {
        Map<String, String> returnResult = new HashMap<>();
        finalLocationConsumer.setUri(null)
                .setTargetHost(null)
                .setRedirectLocations(null)
                .consume(returnResult);
    }

    @Test(expected = IllegalArgumentException.class)
    public void consumeWithException() throws URISyntaxException {
        URI uri = new URIBuilder("/test").build();
        HttpHost httpHost = new HttpHost("te[]st1", 8080);
        Map<String, String> returnResult = new HashMap<>();
        finalLocationConsumer.setUri(uri)
                .setTargetHost(httpHost)
                .setRedirectLocations(null)
                .consume(returnResult);


    }
}
