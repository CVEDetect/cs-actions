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



package io.cloudslang.content.json.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.InvalidJsonException;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.internal.JsonContext;
import com.jayway.jsonpath.spi.json.GsonJsonProvider;
import com.jayway.jsonpath.spi.json.JsonProvider;
import io.cloudslang.content.json.exceptions.RemoveEmptyElementException;
import io.cloudslang.content.json.utils.ExceptionMsgs;
import io.cloudslang.content.json.utils.JsonUtils;
import io.cloudslang.content.json.utils.StringUtils;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONStyle;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

import static io.cloudslang.content.json.utils.Constants.InputNames.EMPTY_STRING;

/**
 * Created by Folea Ilie Cristian on 2/3/2016.
 */
public class JsonService {

    @NotNull
    public static JsonNode evaluateJsonPathQuery(@Nullable final String jsonObject, @Nullable final String jsonPath) {
        final JsonContext jsonContext = JsonUtils.getValidJsonContext(jsonObject);
        final JsonPath path = JsonUtils.getValidJsonPath(jsonPath);
        return jsonContext.read(path);
    }


    public String removeEmptyElementsJson(String json) throws RemoveEmptyElementException {
        String normalizedJson = json.trim();

        char wrappingQuote = retrieveWrappingQuoteTypeOfJsonMemberNames(normalizedJson);

        Map<String, Object> jsonMap;

        try {
            parseJsonForInconsistencies(normalizedJson);
            Object jsonElement = new JSONParser(JSONParser.MODE_PERMISSIVE).parse(normalizedJson);
            if(jsonElement instanceof JSONObject) {
                jsonMap = JsonPath.read(normalizedJson, "$");
                removeEmptyElementsFromMap(jsonMap);
                return generateResultingJsonString(wrappingQuote, jsonMap);
            } else if(jsonElement instanceof JSONArray) {
                JSONArray jsonArray = (JSONArray) jsonElement;
                Map<String, Object> tmpMap = new LinkedHashMap<>();
                tmpMap.put("", jsonArray);
                JSONObject jsonObject = new JSONObject(tmpMap);

                jsonMap = JsonPath.read(jsonObject.toJSONString(), "$");
                removeEmptyElementsFromMap(jsonMap);
                return jsonMap.isEmpty() ?
                        generateResultingJsonString(wrappingQuote, new JSONArray()) :
                        generateResultingJsonString(wrappingQuote, (JSONArray)jsonMap.get(EMPTY_STRING));
            } else {
                throw new RemoveEmptyElementException(ExceptionMsgs.JSON_OBJECT_SHOULD_BE_OBJECT_OR_ARRAY);
            }
        } catch (InvalidJsonException | JsonSyntaxException | ParseException ije) {
            throw new RemoveEmptyElementException(ije);
        }
    }


    private String generateResultingJsonString(char wrappingQuote, Map<String, Object> jsonMap) {
        JSONObject jsonObject = new JSONObject(jsonMap);
        String newJson = jsonObject.toJSONString(JSONStyle.LT_COMPRESS);

        if ((!jsonObject.isEmpty()) && (newJson.charAt(1) != wrappingQuote)) {
            return replaceUnescapedOccurrencesOfCharacterInText(newJson, newJson.charAt(1), wrappingQuote);
        }

        return newJson;
    }

    private String generateResultingJsonString(char wrappingQuote, JSONArray jsonArray) {
        return jsonArray.toJSONString(JSONStyle.LT_COMPRESS);
    }


    private void parseJsonForInconsistencies(String normalizedJson) {
        JsonProvider provider = new GsonJsonProvider();
        Configuration configuration = Configuration.builder().jsonProvider(provider).build();
        JsonPath.parse(normalizedJson, configuration);       //throws an exception at runtime if the json is malformed
    }


    /**
     * Returns the quote character used for specifying json member names and String values of json members
     *
     * @param jsonString the source json from which to extract the wrapping quote
     * @return either one of the characters ' (single quote)or " (double quote)
     */
    private char retrieveWrappingQuoteTypeOfJsonMemberNames(String jsonString) {
        char quote = '\"';   //  the default quote character used to specify json member names and string value according to the json specification
        for (char c : jsonString.toCharArray()) {
            if (c == '\'' || c == '\"') {
                quote = c;
                break;
            }
        }
        return quote;
    }


    private void removeEmptyElementsFromMap(Map<String, Object> json) {
        Set<Map.Entry<String, Object>> jsonElements = json.entrySet();
        Iterator<Map.Entry<String, Object>> jsonElementsIterator = jsonElements.iterator();
        while (jsonElementsIterator.hasNext()) {
            Map.Entry<String, Object> jsonElement = jsonElementsIterator.next();
            Object jsonElementValue = jsonElement.getValue();
            if (StringUtils.isEmpty(jsonElementValue)) {
                jsonElementsIterator.remove();
            } else if (jsonElementValue instanceof JSONArray) {
                if (((JSONArray) jsonElementValue).isEmpty()) {
                    jsonElementsIterator.remove();
                } else {
                    removeEmptyElementFromJsonArray((JSONArray) jsonElementValue);
                }
            } else if (jsonElementValue instanceof LinkedHashMap) {
                if (((LinkedHashMap) jsonElementValue).isEmpty()) {
                    jsonElementsIterator.remove();
                } else {
                    removeEmptyElementsFromMap((Map<String, Object>) jsonElementValue);
                }
            }
        }
    }


    private void removeEmptyElementFromJsonArray(JSONArray jsonArray) {

        Iterator jsonArrayIterator = jsonArray.iterator();
        while (jsonArrayIterator.hasNext()) {
            Object jsonArrayElement = jsonArrayIterator.next();
            if (StringUtils.isEmpty(jsonArrayElement)) {
                jsonArrayIterator.remove();
            } else if (jsonArrayElement instanceof JSONArray) {
                if (((JSONArray) jsonArrayElement).isEmpty()) {
                    jsonArrayIterator.remove();
                } else {
                    removeEmptyElementFromJsonArray((JSONArray) jsonArrayElement);
                }
            } else if (jsonArrayElement instanceof LinkedHashMap) {
                if (((LinkedHashMap) jsonArrayElement).isEmpty()) {
                    jsonArrayIterator.remove();
                } else {
                    removeEmptyElementsFromMap((LinkedHashMap) jsonArrayElement);
                }
            }
        }
    }


    @NotNull
    private String replaceUnescapedOccurrencesOfCharacterInText(String text, char toReplace, char newChar) {
        char[] charArrayText = text.toCharArray();
        for (int i = 0; i < charArrayText.length; i++) {
            if (shouldCharacterBeReplaced(charArrayText, toReplace, i)) {
                charArrayText[i] = newChar;
            }
        }

        return String.valueOf(charArrayText);
    }


    @Contract(pure = true)
    private boolean shouldCharacterBeReplaced(char[] characters, char characterToReplace, int characterPosition) {
        return characters[characterPosition] == characterToReplace &&
                (characterPosition == 0 || characters[characterPosition - 1] != '\\');
    }
}
