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
package io.cloudslang.content.ldap.constants;

public final class ExceptionMsgs {
    public static final String UNRECOGNIZED_SSL_MESSAGE = "Unrecognized SSL message";
    public static final String UNRECOGNIZED_SSL_MESSAGE_PLAINTEXT_CONNECTION = "Unrecognized SSL message, plaintext " +
            "connection?";
    public static final String HOST_NOT_SPECIFIED = "The required host input is not specified!";
    public static final String OU_NOT_SPECIFIED = "The required OU input is not specified!";
    public static final String USER_DN_NOT_SPECIFIED = "The required userDN input is not specified!";
    public static final String CN_NOT_SPECIFIED = "The required computerCommonName input is not specified!";
    public static final String USERNAME_NOT_SPECIFIED = "The username input is not specified!";
    public static final String USER_PASSWORD_NOT_SPECIFIED = "The required userPassword input is not specified!";
    public static final String ROOTDN_NOT_SPECIFIED = "The required rootDN input is not specified!";
    public static final String COMPDN_NOT_SPECIFIED = "The required computerDN input is not specified!";
    public static final String NEWOUDN_NOT_SPECIFIED = "The required newOUDN input is not specified!";

    public static final String TIMEOUT_MUST_BE_POSITIVE = "timeout value must be a positive number";
    public static final String INVALID_PORT_NUMBER = "Invalid port number";
    public static final String PORT_NOT_SPECIFIED = "The required 'port' input is not specified";
}
