/*
 * (c) Copyright 2021 Micro Focus
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
package io.cloudslang.content.rft.actions.sftp;

import com.hp.oo.sdk.content.annotations.Action;
import com.hp.oo.sdk.content.annotations.Output;
import com.hp.oo.sdk.content.annotations.Param;
import com.hp.oo.sdk.content.annotations.Response;
import com.hp.oo.sdk.content.plugin.GlobalSessionObject;
import io.cloudslang.content.constants.ReturnCodes;
import io.cloudslang.content.rft.entities.sftp.SFTPCommonInputs;
import io.cloudslang.content.rft.entities.sftp.SFTPConnection;
import io.cloudslang.content.rft.entities.sftp.SFTPRenameInputs;
import io.cloudslang.content.rft.services.SFTPService;
import io.cloudslang.content.rft.utils.SFTPOperation;
import io.cloudslang.content.utils.StringUtilities;

import java.util.List;
import java.util.Map;

import static com.hp.oo.sdk.content.plugin.ActionMetadata.MatchType.COMPARE_EQUAL;
import static com.hp.oo.sdk.content.plugin.ActionMetadata.ResponseType.ERROR;
import static com.hp.oo.sdk.content.plugin.ActionMetadata.ResponseType.RESOLVED;
import static io.cloudslang.content.constants.OutputNames.*;
import static io.cloudslang.content.constants.ResponseNames.FAILURE;
import static io.cloudslang.content.constants.ResponseNames.SUCCESS;
import static io.cloudslang.content.rft.utils.Constants.*;
import static io.cloudslang.content.rft.utils.Descriptions.CommonInputsDescriptions.RETURN_RESULT_DESC;
import static io.cloudslang.content.rft.utils.Descriptions.CommonInputsDescriptions.*;
import static io.cloudslang.content.rft.utils.Descriptions.SFTPDescriptions.*;
import static io.cloudslang.content.rft.utils.Descriptions.SFTPRenameDescriptions.*;
import static io.cloudslang.content.rft.utils.Inputs.CommonInputs.*;
import static io.cloudslang.content.rft.utils.Inputs.SFTPInputs.*;
import static io.cloudslang.content.rft.utils.InputsValidation.verifyInputsSFTPRename;
import static io.cloudslang.content.utils.OutputUtilities.getFailureResultsMap;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.defaultIfEmpty;

public class SFTPRename {
    @Action(name = SFTP_RENAME,
            description = SFTP_RENAME_DESCRIPTION,
            outputs = {
                    @Output(value = RETURN_RESULT, description = RETURN_RESULT_DESC),
                    @Output(value = RETURN_CODE, description = RETURN_CODE_DESC),
                    @Output(value = EXCEPTION, description = EXCEPTION_DESC)
            },
            responses = {
                    @Response(text = SUCCESS, field = RETURN_CODE, value = ReturnCodes.SUCCESS, matchType = COMPARE_EQUAL, responseType = RESOLVED, description = RENAME_SUCCESS_DESC),
                    @Response(text = FAILURE, field = RETURN_CODE, value = ReturnCodes.FAILURE, matchType = COMPARE_EQUAL, responseType = ERROR, description = RENAME_FAILURE_DESC)
            })
    public Map<String, String> execute(@Param(value = HOST, description = HOST_NAME, required = true) String host,
                                       @Param(value = PORT, description = PORT_DESC) String port,
                                       @Param(value = USERNAME, description = USERNAME_DESC, required = true) String username,
                                       @Param(value = PASSWORD, description = PASSWORD_DESC, required = true, encrypted = true) String password,
                                       @Param(value = PROXY_HOST, description = PROXY_HOST_DESC) String proxyHost,
                                       @Param(value = PROXY_PORT, description = PROXY_PORT_DESC) String proxyPort,
                                       @Param(value = PROXY_USERNAME, description = PROXY_USERNAME_DESC) String proxyUsername,
                                       @Param(value = PROXY_PASSWORD, description = PROXY_PASSWORD_DESC, encrypted = true) String proxyPassword,
                                       @Param(value = PRIVATE_KEY, description = PRIVATE_KEY_DESC) String privateKey,
                                       @Param(value = REMOTE_PATH, description = RENAME_REMOTE_PATH_DESC, required = true) String remotePath,
                                       @Param(value = REMOTE_FILE, description = RENAME_REMOTE_FILE_DESC) String remoteFile,
                                       @Param(value = NEW_REMOTE_PATH, description = RENAME_NEW_REMOTE_PATH_DESC, required = true) String newRemotePath,
                                       @Param(value = NEW_REMOTE_FILE, description = NEW_REMOTE_FILE_DESC) String newRemoteFile,
                                       @Param(value = SSH_SESSIONS_DEFAULT_ID, description = GLOBAL_SESSION_DESC) GlobalSessionObject<Map<String, SFTPConnection>> globalSessionObject,
                                       @Param(value = CHARACTER_SET, description = CHARACTER_SET_DESC) String characterSet,
                                       @Param(value = CLOSE_SESSION, description = CLOSE_SESSION_DESC) String closeSession,
                                       @Param(value = CONNECTION_TIMEOUT, description = CONNECTION_TIMEOUT_DESC) String connectionTimeout,
                                       @Param(value = EXECUTION_TIMEOUT, description = EXECUTION_TIMEOUT_DESC) String executionTimeout) {

        host = defaultIfEmpty(host, EMPTY);
        port = defaultIfEmpty(port, String.valueOf(DEFAULT_PORT));
        username = defaultIfEmpty(username, EMPTY);
        password = defaultIfEmpty(password, EMPTY);
        proxyPort = defaultIfEmpty(proxyPort, String.valueOf(DEFAULT_PROXY_PORT));
        privateKey = defaultIfEmpty(privateKey, EMPTY);
        remotePath = defaultIfEmpty(remotePath, EMPTY);
        newRemotePath = defaultIfEmpty(newRemotePath, EMPTY);
        characterSet = defaultIfEmpty(characterSet, CHARACTER_SET_UTF8);
        closeSession = defaultIfEmpty(closeSession, BOOLEAN_TRUE);
        connectionTimeout = defaultIfEmpty(connectionTimeout, CONNECTION_TIMEOUT);
        executionTimeout = defaultIfEmpty(executionTimeout, EXECUTION_TIMEOUT);

        final List<String> exceptionMessages = verifyInputsSFTPRename(remotePath, newRemotePath, host, port,
                username, password, proxyPort, characterSet, closeSession, connectionTimeout, executionTimeout);
        if (!exceptionMessages.isEmpty()) {
            return getFailureResultsMap(StringUtilities.join(exceptionMessages, NEW_LINE));
        }

        SFTPRenameInputs sftpRenameInputs = SFTPRenameInputs.builder()
                .remotePath(remotePath)
                .remoteFile(remoteFile)
                .newRemotePath(newRemotePath)
                .newRemoteFile(newRemoteFile)
                .sftpCommonInputs(SFTPCommonInputs.builder()
                        .host(host)
                        .port(port)
                        .username(username)
                        .password(password)
                        .proxyHost(proxyHost)
                        .proxyPort(proxyPort)
                        .proxyUsername(proxyUsername)
                        .proxyPassword(proxyPassword)
                        .connectionTimeout(connectionTimeout)
                        .executionTimeout(executionTimeout)
                        .privateKey(privateKey)
                        .characterSet(characterSet)
                        .closeSession(closeSession)
                        .globalSessionObject(globalSessionObject)
                        .build())
                .build();

        return new SFTPService().execute(sftpRenameInputs, SFTPOperation.RENAME);
    }
}
