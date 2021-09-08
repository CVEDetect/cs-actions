package io.cloudslang.content.rft.remote_copy;

public class RemoteCopyInputs {

    private final String sourceHost;
    private final String sourcePort;
    private final String sourceUsername;
    private final String sourcePassword;
    private final String sourcePrivateKeyFile;
    private final String sourcePath;
    private final String sourceProtocol;
    private final String sourceCharacterSet;
    private final String destinationHost;
    private final String destinationPort;
    private final String destinationUsername;
    private final String destinationPassword;
    private final String destinationPrivateKeyFile;
    private final String destinationPath;
    private final String destinationProtocol;
    private final String destinationCharacterSet;
    private final String proxyHost;
    private final String proxyPort;
    private final String proxyUsername;
    private final String proxyPassword;
    private final String connectionTimeout;
    private final String executionTimeout;

    private RemoteCopyInputs(String sourceHost, String sourcePort, String sourceUsername, String sourcePassword, String sourcePrivateKeyFile,
                             String sourcePath, String sourceProtocol, String sourceCharacterSet, String destinationHost,
                             String destinationPort, String destinationUsername, String destinationPassword, String destinationPrivateKeyFile,
                             String destinationPath, String destinationProtocol, String destinationCharacterSet,
                             String proxyHost, String proxyPort, String proxyUsername, String proxyPassword,
                             String connectionTimeout, String executionTimeout) {
        this.sourceHost = sourceHost;
        this.sourcePort = sourcePort;
        this.sourceUsername = sourceUsername;
        this.sourcePassword = sourcePassword;
        this.sourcePrivateKeyFile = sourcePrivateKeyFile;
        this.sourcePath = sourcePath;
        this.sourceProtocol = sourceProtocol;
        this.sourceCharacterSet = sourceCharacterSet;
        this.destinationHost = destinationHost;
        this.destinationPort = destinationPort;
        this.destinationUsername = destinationUsername;
        this.destinationPassword = destinationPassword;
        this.destinationPrivateKeyFile = destinationPrivateKeyFile;
        this.destinationPath = destinationPath;
        this.destinationProtocol = destinationProtocol;
        this.destinationCharacterSet = destinationCharacterSet;
        this.proxyHost = proxyHost;
        this.proxyPort = proxyPort;
        this.proxyUsername = proxyUsername;
        this.proxyPassword = proxyPassword;
        this.connectionTimeout = connectionTimeout;
        this.executionTimeout = executionTimeout;
    }

    public String getSourceHost() {
        return sourceHost;
    }

    public String getSourcePort() {
        return sourcePort;
    }

    public String getSourceUsername() {
        return sourceUsername;
    }

    public String getSourcePassword() {
        return sourcePassword;
    }

    public String getSourcePrivateKeyFile() {
        return sourcePrivateKeyFile;
    }

    public String getSourcePath() {
        return sourcePath;
    }

    public String getSourceProtocol() {
        return sourceProtocol;
    }

    public String getSourceCharacterSet() {
        return sourceCharacterSet;
    }

    public String getDestinationHost() {
        return destinationHost;
    }

    public String getDestinationPort() {
        return destinationPort;
    }

    public String getDestinationUsername() {
        return destinationUsername;
    }

    public String getDestinationPassword() {
        return destinationPassword;
    }

    public String getDestinationPrivateKeyFile() {
        return destinationPrivateKeyFile;
    }

    public String getDestinationPath() {
        return destinationPath;
    }

    public String getDestinationProtocol() {
        return destinationProtocol;
    }

    public String getDestinationCharacterSet() {
        return destinationCharacterSet;
    }

    public String getProxyHost() {
        return proxyHost;
    }

    public String getProxyPort() { return proxyPort; }

    public String getProxyUsername() {
        return proxyUsername;
    }

    public String getProxyPassword() {
        return proxyPassword;
    }

    public String getConnectionTimeout() {
        return connectionTimeout;
    }

    public String getExecutionTimeout() {
        return executionTimeout;
    }

    public static class RemoteCopyBuilder {
        private String sourceHost;
        private String sourcePort;
        private String sourceUsername;
        private String sourcePassword;
        private String sourcePrivateKeyFile;
        private String sourcePath;
        private String sourceProtocol;
        private String sourceCharacterSet;
        private String destinationHost;
        private String destinationPort;
        private String destinationUsername;
        private String destinationPassword;
        private String destinationPrivateKeyFile;
        private String destinationPath;
        private String destinationProtocol;
        private String destinationCharacterSet;
        private String proxyHost;
        private String proxyPort;
        private String proxyUsername;
        private String proxyPassword;
        private String connectionTimeout;
        private String executionTimeout;

        public RemoteCopyBuilder() {
        }

        public RemoteCopyInputs build() {
            return new RemoteCopyInputs(sourceHost, sourcePort, sourceUsername, sourcePassword, sourcePrivateKeyFile,
                    sourcePath, sourceProtocol, sourceCharacterSet, destinationHost, destinationPort, destinationUsername,
                    destinationPassword, destinationPrivateKeyFile, destinationPath, destinationProtocol,
                    destinationCharacterSet, proxyHost, proxyPort, proxyUsername, proxyPassword,
                    connectionTimeout,executionTimeout);
        }

        public RemoteCopyBuilder sourceHost(String sourceHost) {
            this.sourceHost = sourceHost;
            return this;
        }

        public RemoteCopyBuilder sourcePort(String sourcePort) {
            this.sourcePort = sourcePort;
            return this;
        }

        public RemoteCopyBuilder sourceUsername(String sourceUsername) {
            this.sourceUsername = sourceUsername;
            return this;
        }

        public RemoteCopyBuilder sourcePassword(String sourcePassword) {
            this.sourcePassword = sourcePassword;
            return this;
        }

        public RemoteCopyBuilder sourcePrivateKeyFile(String sourcePrivateKeyFile) {
            this.sourcePrivateKeyFile = sourcePrivateKeyFile;
            return this;
        }

        public RemoteCopyBuilder sourcePath(String sourcePath) {
            this.sourcePath = sourcePath;
            return this;
        }

        public RemoteCopyBuilder sourceProtocol(String sourceProtocol) {
            this.sourceProtocol = sourceProtocol;
            return this;
        }

        public RemoteCopyBuilder sourceCharacterSet(String sourceCharacterSet) {
            this.sourceCharacterSet = sourceCharacterSet;
            return this;
        }

        public RemoteCopyBuilder destinationHost(String destinationHost) {
            this.destinationHost = destinationHost;
            return this;
        }

        public RemoteCopyBuilder destinationPort(String destinationPort) {
            this.destinationPort = destinationPort;
            return this;
        }

        public RemoteCopyBuilder destinationUsername(String destinationUsername) {
            this.destinationUsername = destinationUsername;
            return this;
        }

        public RemoteCopyBuilder destinationPassword(String destinationPassword) {
            this.destinationPassword = destinationPassword;
            return this;
        }

        public RemoteCopyBuilder destinationPrivateKeyFile(String destinationPrivateKeyFile) {
            this.destinationPrivateKeyFile = destinationPrivateKeyFile;
            return this;
        }

        public RemoteCopyBuilder destinationPath(String destinationPath) {
            this.destinationPath = destinationPath;
            return this;
        }

        public RemoteCopyBuilder destinationProtocol(String destinationProtocol) {
            this.destinationProtocol = destinationProtocol;
            return this;
        }

        public RemoteCopyBuilder destinationCharacterSet(String destinationCharacterSet) {
            this.destinationCharacterSet = destinationCharacterSet;
            return this;
        }

        public RemoteCopyBuilder proxyHost(String proxyHost) {
            this.proxyHost = proxyHost;
            return this;
        }

        public RemoteCopyBuilder proxyPort(String proxyPort) {
            this.proxyPort = proxyPort;
            return this;
        }

        public RemoteCopyBuilder proxyUsername(String proxyUsername) {
            this.proxyUsername = proxyUsername;
            return this;
        }

        public RemoteCopyBuilder proxyPassword(String proxyPassword) {
            this.proxyPassword = proxyPassword;
            return this;
        }

        public RemoteCopyBuilder connectionTimeout(String connectionTimeout) {
            this.connectionTimeout = connectionTimeout;
            return this;
        }

        public RemoteCopyBuilder executionTimeout(String executionTimeout) {
            this.executionTimeout = executionTimeout;
            return this;
        }
    }
}

