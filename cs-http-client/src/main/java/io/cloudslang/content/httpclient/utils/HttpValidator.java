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
package io.cloudslang.content.httpclient.utils;

import io.cloudslang.content.httpclient.entities.HttpClientInputs;

import java.util.ArrayList;
import java.util.List;

import static io.cloudslang.content.constants.BooleanValues.FALSE;
import static io.cloudslang.content.httpclient.entities.Constants.EXCEPTION_INVALID_BOOLEAN;
import static io.cloudslang.content.httpclient.entities.HttpClientInputs.KEEP_ALIVE;
import static io.cloudslang.content.utils.BooleanUtilities.isValid;
import static org.apache.commons.lang3.StringUtils.defaultIfEmpty;
import static org.apache.commons.lang3.StringUtils.isEmpty;

public class HttpValidator {

    public static List<String> validateInputs(HttpClientInputs clientInputs) {

            List<String> exceptions = new ArrayList<>();

            if (isEmpty(clientInputs.getKeepAlive()))
                defaultIfEmpty(clientInputs.getKeepAlive(), FALSE);
            else if (!isValid(clientInputs.getKeepAlive()))
                exceptions.add(String.format(EXCEPTION_INVALID_BOOLEAN, clientInputs.getKeepAlive(), KEEP_ALIVE));

            return exceptions;
    }
}