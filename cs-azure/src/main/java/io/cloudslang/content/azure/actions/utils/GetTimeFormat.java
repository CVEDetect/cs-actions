/*
 * (c) Copyright 2022 Micro Focus, L.P.
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
package io.cloudslang.content.azure.actions.utils;

import com.hp.oo.sdk.content.annotations.Action;
import com.hp.oo.sdk.content.annotations.Output;
import com.hp.oo.sdk.content.annotations.Param;
import com.hp.oo.sdk.content.annotations.Response;
import io.cloudslang.content.azure.services.GetTimeFormatImpl;
import io.cloudslang.content.constants.ReturnCodes;
import io.cloudslang.content.utils.StringUtilities;

import java.util.List;
import java.util.Map;

import static com.hp.oo.sdk.content.plugin.ActionMetadata.MatchType.COMPARE_EQUAL;
import static com.hp.oo.sdk.content.plugin.ActionMetadata.ResponseType.ERROR;
import static com.hp.oo.sdk.content.plugin.ActionMetadata.ResponseType.RESOLVED;
import static io.cloudslang.content.azure.utils.Constants.GetTimeFormatConstants.*;
import static io.cloudslang.content.azure.utils.Constants.NEW_LINE;
import static io.cloudslang.content.azure.utils.Constants.SchedulerTimeConstants.TIME_ZONE;
import static io.cloudslang.content.azure.utils.Descriptions.Common.*;
import static io.cloudslang.content.azure.utils.Descriptions.GetTimeFormat.*;
import static io.cloudslang.content.azure.utils.Descriptions.SchedulerTime.TIME_ZONE_DESC;
import static io.cloudslang.content.azure.utils.InputsValidation.verifyTimeFormatInputs;
import static io.cloudslang.content.constants.OutputNames.EXCEPTION;
import static io.cloudslang.content.constants.OutputNames.RETURN_CODE;
import static io.cloudslang.content.constants.ResponseNames.FAILURE;
import static io.cloudslang.content.constants.ResponseNames.SUCCESS;
import static io.cloudslang.content.utils.OutputUtilities.getFailureResultsMap;

public class GetTimeFormat {
    @Action(name = GET_TIME_FORMAT_OPERATION_NAME, description = GET_TIME_FORMAT_OPERATION_DESC,
            outputs = {

                    @Output(value = DATE_FORMAT, description = DATE_FORMAT_DESC),
                    @Output(value = EXCEPTION, description = EXCEPTION_DESC)},

            responses = {
                    @Response(text = SUCCESS, field = RETURN_CODE, value = ReturnCodes.SUCCESS, matchType = COMPARE_EQUAL, responseType = RESOLVED, description = SUCCESS_DESC),
                    @Response(text = FAILURE, field = RETURN_CODE, value = ReturnCodes.FAILURE, matchType = COMPARE_EQUAL, responseType = ERROR, description = FAILURE_DESC)
            })
    public Map<String, String> execute(
            @Param(value = EPOCH_TIME, required = true, description = EPOCH_TIME_DESC) String epochTime,
            @Param(value = TIME_ZONE, required = true, description = TIME_ZONE_DESC) String timeZone
    ) {

        try {

            List<String> exceptionMessage = verifyTimeFormatInputs(epochTime, timeZone);
            if (!exceptionMessage.isEmpty()) {
                return getFailureResultsMap(StringUtilities.join(exceptionMessage, NEW_LINE));
            }
            return GetTimeFormatImpl.getTimeFormat(epochTime, timeZone);
        } catch (Exception exception) {
            return getFailureResultsMap(exception);
        }

    }
}
