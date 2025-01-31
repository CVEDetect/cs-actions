/*
 * (c) Copyright 2021 EntIT Software LLC, a Micro Focus company, L.P.
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




package io.cloudslang.content.ssh.utils;

import com.jcraft.jsch.ProxyHTTP;
import io.cloudslang.content.ssh.entities.ProxyConnectionDetails;
import io.cloudslang.content.utils.StringUtilities;

public class ProxyUtils {

    public static ProxyHTTP getHTTPProxy(String proxyHost, String proxyPort, String proxyUsername, String proxyPassword) {
        if (!StringUtilities.isEmpty(proxyHost)) {
            int portForProxy = getPortValue(proxyPort, Constants.DEFAULT_PROXY_PORT);
            return createHTTPProxy(proxyHost, portForProxy, proxyUsername, proxyPassword);
        } else {
            return null;
        }
    }

    public static ProxyHTTP createHTTPProxy(String proxyHost, int proxyPort, String proxyUsername, String proxyPassword){
        ProxyConnectionDetails proxyConnectionDetails = new ProxyConnectionDetails(proxyHost, proxyPort, proxyUsername, proxyPassword);
        ProxyHTTP proxyHTTP = new ProxyHTTP(proxyConnectionDetails.getProxyHost(), proxyConnectionDetails.getProxyPort());
        String username = (StringUtilities.isEmpty(proxyUsername) ? null : proxyUsername);
        String password = (StringUtilities.isEmpty(proxyPassword) ? null : proxyPassword);
        proxyHTTP.setUserPasswd(username, password);
        return proxyHTTP;
    }

    public static int getPortValue(String port, int defaultValue){
        if(StringUtilities.isEmpty(port)) {
            return defaultValue;
        }
        else {
            return StringUtils.validatePortNumber(port, Constants.PROXY_PORT);
        }
    }
}
