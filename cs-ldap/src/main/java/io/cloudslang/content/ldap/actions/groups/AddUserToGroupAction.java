/*
 * (c) Copyright 2020 Micro Focus
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

package io.cloudslang.content.ldap.actions.groups;

import com.hp.oo.sdk.content.annotations.Action;
import com.hp.oo.sdk.content.annotations.Output;
import com.hp.oo.sdk.content.annotations.Param;
import com.hp.oo.sdk.content.annotations.Response;
import com.hp.oo.sdk.content.plugin.ActionMetadata.MatchType;
import com.hp.oo.sdk.content.plugin.ActionMetadata.ResponseType;
import io.cloudslang.content.constants.ResponseNames;
import io.cloudslang.content.constants.ReturnCodes;
import io.cloudslang.content.ldap.constants.InputNames;
import io.cloudslang.content.ldap.constants.OutputNames;
import io.cloudslang.content.ldap.entities.AddRemoveUserInput;
import io.cloudslang.content.ldap.services.groups.AddUserToGroupService;
import io.cloudslang.content.ldap.utils.ResultUtils;

import java.util.Map;

import static io.cloudslang.content.ldap.constants.Descriptions.AddUserToGroup.*;
import static io.cloudslang.content.ldap.constants.Descriptions.Common.*;
import static io.cloudslang.content.ldap.constants.Descriptions.CreateComputerAccount.RETURN_RESULT_DESC;

public class AddUserToGroupAction {

    /**
     * This operation adds a user to a group in Active Directory.
     *
     * @param host                   The IP or host name of the domain controller. The port number can be mentioned as well,
     *                               along with the host (hostNameOrIP:PortNumber).
     *                               Examples: test.example.com,  test.example.com:636, <IPv4Address>, <IPv6Address>,
     *                               [<IPv6Address>]:<PortNumber> etc.
     *                               Value format: The format of an IPv4 address is: [0-225].[0-225].[0-225].[0-225]. The format of an
     *                               IPv6 address is ####:####:####:####:####:####:####:####/### (with a prefix), where each #### is
     *                               a hexadecimal value between 0 to FFFF and the prefix /### is a decimal value between 0 to 128.
     *                               The prefix length is optional.
     * @param groupDistinguishedName The DN (distinguished name) of the group.
     *                               Example: CN=GroupName,OU=OUTest1,DC=battleground,DC=ad
     * @param userDistinguishedName  The DN (distinguished name) of the user to add.
     *                               Example: CN=UserName,OU=OUTest1,DC=battleground,DC=ad
     * @param username               The user to connect to Active Directory as.
     * @param password               The password of the user to connect to Active Directory.
     * @param protocol               The protocol to use when connecting to the Active Directory server.
     *                               Valid values: HTTP and HTTPS.
     * @param proxyHost              The proxy server used to access the web site.
     * @param proxyPort              The proxy server port.
     *                               Default value: 8080.
     * @param proxyUsername          The username used when connecting to the proxy.
     * @param proxyPassword          The proxy server password associated with the "proxyUsername" input value.
     * @param tlsVersion             The version of TLS to use. The value of this input will be ignored if "protocol"
     *                               is set to "HTTP".
     *                               Valid values: SSLv3, TLSv1, TLSv1.1, TLSv1.2.
     *                               Default value: TLSv1.2.
     * @param allowedCiphers         A list of ciphers to use. The value of this input will be ignored if 'tlsVersion' does
     *                               not contain 'TLSv1.2'.
     *                               Default value: TLS_DHE_RSA_WITH_AES_256_GCM_SHA384,
     *                               TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384, TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
     *                               TLS_DHE_RSA_WITH_AES_256_CBC_SHA256, TLS_DHE_RSA_WITH_AES_128_CBC_SHA256,
     *                               TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA384, TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA256,
     *                               TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA256, TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
     *                               TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA384, TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384,
     *                               TLS_RSA_WITH_AES_256_GCM_SHA384, TLS_RSA_WITH_AES_256_CBC_SHA256,
     *                               TLS_RSA_WITH_AES_128_CBC_SHA256.
     * @param trustAllRoots          Specifies whether to enable weak security over SSL. A SSL certificate is trusted
     *                               even if no trusted certification authority issued it.
     *                               Valid values: true, false.
     *                               Default value: true.
     * @param x509HostnameVerifier   Specifies the way the server hostname must match a domain name in the subject's Common
     *                               Name (CN) or subjectAltName field of the X.509 certificate. Set this to "allow_all" to
     *                               skip any checking, but you become vulnerable to attacks.
     *                               For the value "browser_compatible" the hostname verifier works the same way as Curl
     *                               and Firefox. The hostname must match either the first CN, or any of the subject-alts.
     *                               A wildcard can occur in the CN, and in any of the subject-alts. The only difference
     *                               between "browser_compatible" and "strict" is that a wildcard (such as "*.foo.com")
     *                               with "browser_compatible" matches all subdomains, including "a.b.foo.com". From the
     *                               security perspective, to provide protection against possible Man-In-The-Middle attacks,
     *                               we strongly recommend to use "strict" option.
     *                               Default value: strict.
     *                               Valid values: strict, browser_compatible, allow_all.
     * @param trustKeystore          The location of the TrustStore file.
     *                               Example: %JAVA_HOME%/jre/lib/security/cacerts
     * @param trustPassword          The password associated with the TrustStore file.
     * @param timeout                Time in milliseconds to wait for the command to complete.
     *                               Default value: 60000.
     * @return - a map containing the output of the operation. Keys present in the map are:
     * returnResult - A message with the added user's DN and the group in which it was added DN, in case of success or the
     * error in case of failure.
     * returnCode - The return code of the operation. 0 if the operation succeeded, -1 if the operation fails.
     * exception - The exception message if the operation fails.
     */

    @Action(name = "Add User To Group", description = ADD_USER_TO_GROUP_DESC,
            outputs = {
                    @Output(value = OutputNames.RETURN_RESULT, description = RETURN_RESULT_DESC),
                    @Output(value = OutputNames.RETURN_CODE, description = RETURN_CODE_DESC),
                    @Output(value = OutputNames.EXCEPTION, description = EXCEPTION_DESC)
            },
            responses = {
                    @Response(text = ResponseNames.SUCCESS, field = OutputNames.RETURN_CODE, value = ReturnCodes.SUCCESS,
                            matchType = MatchType.COMPARE_EQUAL, responseType = ResponseType.RESOLVED, description = SUCCESS_DESC),
                    @Response(text = ResponseNames.FAILURE, field = OutputNames.RETURN_CODE,
                            value = ReturnCodes.FAILURE, matchType = MatchType.COMPARE_EQUAL, responseType = ResponseType.ERROR, description = FAILURE_DESC)
            })
    public Map<String, String> execute(
            @Param(value = InputNames.HOST, required = true, description = HOST_DESC) String host,
            @Param(value = InputNames.GROUP_DISTINGUISHED_NAME, required = true, description = GROUP_DISTINGUISHED_NAME_DESC) String groupDistinguishedName,
            @Param(value = InputNames.USER_DISTINGUISHED_NAME, required = true, description = USER_DISTINGUISHED_NAME_DESC) String userDistinguishedName,
            @Param(value = InputNames.USERNAME, description = USERNAME_DESC) String username,
            @Param(value = InputNames.PASSWORD, encrypted = true, description = PASSWORD_DESC) String password,
            @Param(value = InputNames.PROTOCOL, description = PROTOCOL_DESC) String protocol,
            @Param(value = InputNames.PROXY_HOST, description = PROXY_HOST_DESC) String proxyHost,
            @Param(value = InputNames.PROXY_PORT, description = PROXY_PORT_DESC) String proxyPort,
            @Param(value = InputNames.PROXY_USERNAME, description = PROXY_USERNAME_DESC) String proxyUsername,
            @Param(value = InputNames.PROXY_PASSWORD, description = PROXY_PASSWORD_DESC) String proxyPassword,
            @Param(value = InputNames.TLS_VERSION, description = TLS_VERSION_DESC) String tlsVersion,
            @Param(value = InputNames.ALLOWED_CIPHERS, description = ALLOWED_CIPHERS_DESC) String allowedCiphers,
            @Param(value = InputNames.X_509_HOSTNAME_VERIFIER, description = X_509_DESC) String x509HostnameVerifier,
            @Param(value = InputNames.TRUST_ALL_ROOTS, description = TRUST_ALL_ROOTS_DESC) String trustAllRoots,
            @Param(value = InputNames.TRUST_KEYSTORE, description = TRUST_KEYSTORE_DESC) String trustKeystore,
            @Param(value = InputNames.TRUST_PASSWORD, encrypted = true, description = TRUST_PASSWORD_DESC) String trustPassword,
            @Param(value = InputNames.EXECUTION_TIMEOUT, description = EXECUTION_TIMEOUT_DESC) String timeout) {
        AddRemoveUserInput.Builder inputBuilder = new AddRemoveUserInput.Builder()
                .host(host)
                .groupDistinguishedName(groupDistinguishedName)
                .userDistinguishedName(userDistinguishedName)
                .username(username)
                .password(password)
                .protocol(protocol)
                .proxyHost(proxyHost)
                .proxyPort(proxyPort)
                .proxyUsername(proxyUsername)
                .proxyPassword(proxyPassword)
                .tlsVersion(tlsVersion)
                .allowedCiphers(allowedCiphers)
                .x509HostnameVerifier(x509HostnameVerifier)
                .trustAllRoots(trustAllRoots)
                .trustKeystore(trustKeystore)
                .trustPassword(trustPassword)
                .timeout(timeout);
        try {
            return new AddUserToGroupService().execute(inputBuilder.build());
        } catch (Exception e) {
            return ResultUtils.fromException(e);
        }
    }
}
