/*
 * (c) Copyright 2020 Micro Focus, L.P.
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
package io.cloudslang.content.google.actions.databases.sql.instances

import java.util


import com.hp.oo.sdk.content.annotations.{Action, Output, Param, Response}
import com.hp.oo.sdk.content.plugin.ActionMetadata.{MatchType, ResponseType}
import io.cloudslang.content.constants.OutputNames._
import io.cloudslang.content.constants.{ResponseNames, ReturnCodes}
import io.cloudslang.content.google.services.databases.sql.instances.SQLDatabaseInstanceService
import io.cloudslang.content.google.utils.Constants.NEW_LINE
import io.cloudslang.content.google.utils.action.DefaultValues._
import io.cloudslang.content.google.utils.action.Descriptions.Common._
import io.cloudslang.content.google.utils.action.Descriptions.CreateSQLDataBaseInstance._

import io.cloudslang.content.google.utils.action.InputNames.CreateSQLDatabaseInstanceInputs._
import io.cloudslang.content.google.utils.action.InputNames._
import io.cloudslang.content.google.utils.action.InputUtils.verifyEmpty
import io.cloudslang.content.google.utils.action.InputValidator.{validateBoolean, validateProxyPort}
import io.cloudslang.content.google.utils.action.OutputUtils.toPretty
import io.cloudslang.content.google.utils.action.InputNames.CreateSQLDatabaseInstanceInputs.MACHINE_TYPE
import io.cloudslang.content.google.utils.action.Outputs.SQLDatabaseInstance._
import io.cloudslang.content.google.utils.service.{GoogleAuth, HttpTransportUtils, JsonFactoryUtils}
import io.cloudslang.content.utils.BooleanUtilities.toBoolean
import io.cloudslang.content.utils.NumberUtilities.toInteger
import io.cloudslang.content.utils.OutputUtilities.{getFailureResultsMap, getSuccessResultsMap}
import org.apache.commons.lang3.StringUtils.{EMPTY, defaultIfEmpty}
import io.cloudslang.content.google.utils.action.InputNames.CreateSQLDatabaseInstanceInputs.ZONE

import scala.collection.JavaConversions._

class GetSQLInstance {
  @Action(name = "Get SQL Instance",
    description = "Retrieves a resource containing information about a database inside a Google Cloud SQL instance.",
    outputs = Array(
      new Output(value = RETURN_CODE, description = RETURN_CODE_DESC),
      new Output(value = RETURN_RESULT, description = RETURN_RESULT_DESC),
      new Output(value = EXCEPTION, description = EXCEPTION_DESC),
      new Output(value = INSTANCE_ID, description = INSTANCE_ID_DESC),
      new Output(value = CONNECTION_NAME, description = CONNECTION_NAME_DESC),
      new Output(value = DATABASE_VERSION, description = DATABASE_VERSION_DESC),
      new Output(value = ZONE, description = ZONE_DESC),
      new Output(value = PUBLIC_IP_ADDRESS, description = PUBLIC_IP_ADDRESS_DESC),
      new Output(value = INSTANCE_ID, description = INSTANCE_ID_DESC),
      new Output(value = REGION, description = REGION_DESC),
      new Output(value = SELF_LINK, description = SELF_LINK_DESC),
      new Output(value = AVAILABILITY_TYPE, description = AVAILABILITY_TYPE_DESC),
      new Output(value = STORAGE_TYPE, description = STORAGE_TYPE_DESC),
      new Output(value = STORAGE_CAPACITY, description = STORAGE_CAPACITY_DESC),
      new Output(value = STATE, description = STATE_DESC),
      new Output(value = MACHINE_TYPE, description = MACHINE_TYPE_DESC)
    )
    ,
    responses = Array(
      new Response(text = ResponseNames.SUCCESS, field = RETURN_CODE, value = ReturnCodes.SUCCESS,
        matchType = MatchType.COMPARE_EQUAL, responseType = ResponseType.RESOLVED),
      new Response(text = ResponseNames.FAILURE, field = RETURN_CODE, value = ReturnCodes.FAILURE,
        matchType = MatchType.COMPARE_EQUAL, responseType = ResponseType.ERROR, isOnFail = true)
    )
  )
  def execute(@Param(value = PROJECT_ID, required = true, description = PROJECT_ID_DESC) projectId: String,
              @Param(value = ACCESS_TOKEN, required = true, encrypted = true, description = ACCESS_TOKEN_DESC) accessToken: String,
              @Param(value = INSTANCE_ID, required = true, description = INSTANCE_ID_DESC) instanceId: String,
              @Param(value = PROXY_HOST) proxyHost: String,
              @Param(value = PROXY_PORT) proxyPort: String,
              @Param(value = PROXY_USERNAME) proxyUsername: String,
              @Param(value = PROXY_PASSWORD, encrypted = true) proxyPassword: String,
              @Param(value = PRETTY_PRINT) prettyPrintInp: String): util.Map[String, String] = {


    val proxyHostStr = verifyEmpty(proxyHost)
    val proxyUsernameOpt = verifyEmpty(proxyUsername)
    val proxyPortInt = defaultIfEmpty(proxyPort, DEFAULT_PROXY_PORT)
    val proxyPasswordStr = defaultIfEmpty(proxyPassword, EMPTY)
    val prettyPrintStr = defaultIfEmpty(prettyPrintInp, DEFAULT_PRETTY_PRINT)


    val validationStream = validateProxyPort(proxyPortInt) ++
      validateBoolean(prettyPrintStr, PRETTY_PRINT)

    if (validationStream.nonEmpty) {
      return getFailureResultsMap(validationStream.mkString(NEW_LINE))
    }

    val proxyPortVal = toInteger(proxyPortInt)
    val prettyPrint = toBoolean(prettyPrintStr)

    try {
      val httpTransport = HttpTransportUtils.getNetHttpTransport(proxyHostStr, proxyPortVal, proxyUsernameOpt,
        proxyPasswordStr)
      val jsonFactory = JsonFactoryUtils.getDefaultJacksonFactory
      val credential = GoogleAuth.fromAccessToken(accessToken)

      val sqlInstance = SQLDatabaseInstanceService.get(httpTransport, jsonFactory, credential, projectId,
        instanceId)
      val sqlInstanceSettings = sqlInstance.getSettings

      getSuccessResultsMap(toPretty(prettyPrint, sqlInstance)) +
        (CONNECTION_NAME -> sqlInstance.getConnectionName) +
        (DATABASE_VERSION -> sqlInstance.getDatabaseVersion) +
        (ZONE -> sqlInstance.getGceZone) +
        (PUBLIC_IP_ADDRESS -> sqlInstance.getIpAddresses.get(0).getIpAddress) +
        (INSTANCE_ID -> instanceId) +
        (REGION -> sqlInstance.getRegion) +
        (SELF_LINK -> sqlInstance.getSelfLink) +
        (AVAILABILITY_TYPE -> sqlInstanceSettings.getAvailabilityType) +
        (STORAGE_TYPE -> sqlInstanceSettings.getDataDiskType) +
        (STORAGE_CAPACITY -> sqlInstanceSettings.getDataDiskSizeGb.toString) +
        (STATE -> sqlInstance.getState) +
        (MACHINE_TYPE -> sqlInstanceSettings.getTier)
    } catch {
      case e: Throwable => getFailureResultsMap(e)
    }
  }
}

