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
package io.cloudslang.content.winrm.entities;

import java.beans.ConstructorProperties;

public class WinRMInputs {
    private final String host;
    private final String port;
    private final String protocol;
    private final String username;
    private final String password;
    private final String script;
    private final String authType;
    private final String proxyHost;
    private final String proxyPort;
    private final String proxyUsername;
    private final String proxyPassword;
    private final String useSSL;
    private final String trustAllRoots;
    private final String x509HostnameVerifier;
    private final String trustKeystore;
    private final String trustPassword;
    private final String keystore;
    private final String keystorePassword;
    private final int operationTimeout;
    private final String requestNewKerberosToken;
    private final String workingDirectory;


    @ConstructorProperties({"host", "port", "protocol", "username", "password", "script", "authType", "proxyHost",
            "proxyPort", "proxyUsername", "proxyPassword", "useSSL", "trustALLRoots", "x509HostnameVerifier", "trustKeystore",
            "trustPassword", "keyStore", "keyStorePassword", "operationTimeout"})
    private WinRMInputs(String host, String port, String protocol, String username, String password, String script,
                        String authType, String proxyHost, String proxyPort, String proxyUsername, String proxyPassword,
                        String useSSL, String trustAllRoots, String x509HostnameVerifier, String trustKeystore, String trustPassword,
                        String keystore, String keystorePassword, int operationTimeout, String requestNewKerberosToken, String workingDirectory) {
        this.host = host;
        this.port = port;
        this.script = script;
        this.protocol = protocol;
        this.username = username;
        this.password = password;
        this.authType = authType;
        this.proxyHost = proxyHost;
        this.proxyPort = proxyPort;
        this.proxyUsername = proxyUsername;
        this.proxyPassword = proxyPassword;
        this.useSSL = useSSL;
        this.trustAllRoots = trustAllRoots;
        this.x509HostnameVerifier = x509HostnameVerifier;
        this.trustKeystore = trustKeystore;
        this.trustPassword = trustPassword;
        this.keystore = keystore;
        this.keystorePassword = keystorePassword;
        this.operationTimeout = operationTimeout;
        this.requestNewKerberosToken = requestNewKerberosToken;
        this.workingDirectory = workingDirectory;
    }

    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getScript() {
        return script;
    }

    public String getAuthType() {
        return authType;
    }

    public String getProxyHost() {
        return proxyHost;
    }

    public String getProxyPort() {
        return proxyPort;
    }

    public String getProxyUsername() {
        return proxyUsername;
    }

    public String getProxyPassword() {
        return proxyPassword;
    }

    public String getUseSSL() {
        return useSSL;
    }

    public String getTrustAllRoots() {
        return trustAllRoots;
    }

    public String getX509HostnameVerifier() {
        return x509HostnameVerifier;
    }

    public String getTrustKeystore() {
        return trustKeystore;
    }

    public String getTrustPassword() {
        return trustPassword;
    }

    public String getKeystore() {
        return keystore;
    }

    public String getKeystorePassword() {
        return keystorePassword;
    }

    public int getOperationTimeout() {
        return operationTimeout;
    }

    public String getRequestNewKerberosToken(){return requestNewKerberosToken; }

    public String getWorkingDirectory(){return workingDirectory; }

    public static class WinRMBuilder {
        private String host;
        private String port;
        private String protocol;
        private String username;
        private String password;
        private String script;
        private String authType;
        private String proxyHost;
        private String proxyPort;
        private String proxyUsername;
        private String proxyPassword;
        private String useSSL;
        private String trustAllRoots;
        private String x509HostnameVerifier;
        private String trustKeystore;
        private String trustPassword;
        private String keystore;
        private String keystorePassword;
        private String requestNewKerberosToken;
        private int operationTimeout;
        private String workingDirectory;

       public WinRMBuilder() {
        }

        public WinRMInputs build() {
            return new WinRMInputs(host, port, protocol, username, password, script, authType, proxyHost, proxyPort,
                    proxyUsername, proxyPassword, useSSL, trustAllRoots, x509HostnameVerifier, trustKeystore, trustPassword,
                    keystore, keystorePassword, operationTimeout, requestNewKerberosToken, workingDirectory);
        }

        public WinRMBuilder host(String host) {
            this.host = host;
            return this;
        }

        public WinRMBuilder port(String port) {
            this.port = port;
            return this;
        }

        public WinRMBuilder protocol(String protocol) {
            this.protocol = protocol;
            return this;
        }

        public WinRMBuilder username(String username) {
            this.username = username;
            return this;
        }

        public WinRMBuilder password(String password) {
            this.password = password;
            return this;
        }

        public WinRMBuilder script(String script) {
            this.script = script;
            return this;
        }

        public WinRMBuilder authType(String authType) {
            this.authType = authType;
            return this;
        }

        public WinRMBuilder proxyHost(String proxyHost) {
            this.proxyHost = proxyHost;
            return this;
        }

        public WinRMBuilder proxyPort(String proxyPort) {
            this.proxyPort = proxyPort;
            return this;
        }

        public WinRMBuilder proxyUsername(String proxyUsername) {
            this.proxyUsername = proxyUsername;
            return this;
        }

        public WinRMBuilder proxyPassword(String proxyPassword) {
            this.proxyPassword = proxyPassword;
            return this;
        }

        public WinRMBuilder useSSL(String useSSL) {
            this.useSSL = useSSL;
            return this;
        }

        public WinRMBuilder trustAllRoots(String trustAllRoots) {
            this.trustAllRoots = trustAllRoots;
            return this;
        }

        public WinRMBuilder x509HostnameVerifier(String x509HostnameVerifier) {
            this.x509HostnameVerifier = x509HostnameVerifier;
            return this;
        }

        public WinRMBuilder trustKeystore(String trustKeystore) {
            this.trustKeystore = trustKeystore;
            return this;
        }

        public WinRMBuilder trustPassword(String trustPassword) {
            this.trustPassword = trustPassword;
            return this;
        }

        public WinRMBuilder keystore(String keystore) {
            this.keystore = keystore;
            return this;
        }

        public WinRMBuilder keystorePassword(String keystorePassword) {
            this.keystorePassword = keystorePassword;
            return this;
        }

        public WinRMBuilder operationTimeout(int operationTimeout) {
            this.operationTimeout = operationTimeout;
            return this;
        }

        public WinRMBuilder requestNewKerberosToken(String requestNewKerberosToken) {
            this.requestNewKerberosToken = requestNewKerberosToken;
            return this;
        }

        public WinRMBuilder workingDirectory(String workingDirectory){
           this.workingDirectory = workingDirectory;
           return this;
        }
    }
}
