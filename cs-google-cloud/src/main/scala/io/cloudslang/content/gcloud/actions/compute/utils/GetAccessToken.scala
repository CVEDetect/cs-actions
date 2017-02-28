package io.cloudslang.content.gcloud.actions.compute.utils

import java.nio.charset.StandardCharsets
import java.util

import com.hp.oo.sdk.content.annotations.{Action, Output, Param, Response}
import com.hp.oo.sdk.content.plugin.ActionMetadata.{MatchType, ResponseType}
import io.cloudslang.content.constants.{OutputNames, ResponseNames, ReturnCodes}
import io.cloudslang.content.gcloud.utils.Constants.NEW_LINE
import io.cloudslang.content.gcloud.utils.action.DefaultValues.{DEFAULT_PROXY_PASSWORD, DEFAULT_PROXY_PORT, DEFAULT_SCOPES_DELIMITER, DEFAULT_TIMEOUT}
import io.cloudslang.content.gcloud.utils.action.InputNames._
import io.cloudslang.content.gcloud.utils.action.InputUtils.verifyEmpty
import io.cloudslang.content.gcloud.utils.action.InputValidator.{validateNonNegativeInteger, validateProxyPort}
import io.cloudslang.content.gcloud.utils.service.{GoogleAuth, HttpTransportUtils, JsonFactoryUtils}
import io.cloudslang.content.utils.NumberUtilities.toInteger
import io.cloudslang.content.utils.OutputUtilities.{getFailureResultsMap, getSuccessResultsMap}
import org.apache.commons.io.IOUtils
import org.apache.commons.lang3.StringUtils.defaultIfEmpty

/**
  * Created by victor on 28.02.2017.
  */
class GetAccessToken {


  /*
  scopes: see https://developers.google.com/identity/protocols/googlescopes#computev1
   */

  @Action(name = "Get the access token for Google Cloud",
    outputs = Array(
      new Output(OutputNames.RETURN_CODE),
      new Output(OutputNames.RETURN_RESULT),
      new Output(OutputNames.EXCEPTION)
    ),
    responses = Array(
      new Response(text = ResponseNames.SUCCESS, field = OutputNames.RETURN_CODE, value = ReturnCodes.SUCCESS, matchType = MatchType.COMPARE_EQUAL, responseType = ResponseType.RESOLVED),
      new Response(text = ResponseNames.FAILURE, field = OutputNames.RETURN_CODE, value = ReturnCodes.FAILURE, matchType = MatchType.COMPARE_EQUAL, responseType = ResponseType.ERROR, isOnFail = true)
    )
  )
  def execute(@Param(value = JSON_TOKEN, required = true, encrypted = true) jsonToken: String,
              @Param(value = SCOPES, required = true) scopes: String,
              @Param(value = SCOPES_DELIMITER) scopesDelInp: String,
              @Param(value = TIMEOUT) timeoutInp: String,
              @Param(value = PROXY_HOST) proxyHost: String,
              @Param(value = PROXY_PORT) proxyPortInp: String,
              @Param(value = PROXY_USERNAME) proxyUsername: String,
              @Param(value = PROXY_PASSWORD, encrypted = true) proxyPasswordInp: String): util.Map[String, String] = {

    val proxyHostOpt = verifyEmpty(proxyHost)
    val proxyUsernameOpt = verifyEmpty(proxyUsername)
    val proxyPortStr = defaultIfEmpty(proxyPortInp, DEFAULT_PROXY_PORT)
    val proxyPassword = defaultIfEmpty(proxyPasswordInp, DEFAULT_PROXY_PASSWORD)
    val scopesDel = defaultIfEmpty(scopesDelInp, DEFAULT_SCOPES_DELIMITER)
    val timeoutStr = defaultIfEmpty(timeoutInp, DEFAULT_TIMEOUT)

    val validationStream = validateProxyPort(proxyPortStr) ++
      validateNonNegativeInteger(timeoutStr, TIMEOUT)

    if (validationStream.nonEmpty) {
      return getFailureResultsMap(validationStream.mkString(NEW_LINE))
    }

    val proxyPort = toInteger(proxyPortStr)
    val timeout = toInteger(timeoutStr)

    try {
      val httpTransport = HttpTransportUtils.getNetHttpTransport(proxyHostOpt, proxyPort, proxyUsernameOpt, proxyPassword)
      val jsonFactory = JsonFactoryUtils.getDefaultJacksonFactory

      val credential = GoogleAuth.fromJsonWithScopes(IOUtils.toInputStream(jsonToken, StandardCharsets.UTF_8),
        httpTransport, jsonFactory, scopes.split(scopesDel), timeout)
      val accessToken = GoogleAuth.getAccessTokenFromCredentials(credential)

      getSuccessResultsMap(accessToken)
    } catch {
      case e: Throwable => getFailureResultsMap(e)
    }
  }
}
