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



package io.cloudslang.content.amazon.factory.helpers;

import io.cloudslang.content.amazon.entities.constants.Constants;
import io.cloudslang.content.amazon.entities.inputs.InputsWrapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.cloudslang.content.amazon.entities.constants.Constants.AwsParams.ATTRIBUTE;
import static io.cloudslang.content.amazon.entities.constants.Constants.AwsParams.DESCRIPTION;
import static io.cloudslang.content.amazon.entities.constants.Constants.AwsParams.EXECUTABLE_BY;
import static io.cloudslang.content.amazon.entities.constants.Constants.AwsParams.FILTER_NAME;
import static io.cloudslang.content.amazon.entities.constants.Constants.AwsParams.FILTER_VALUE;
import static io.cloudslang.content.amazon.entities.constants.Constants.AwsParams.IMAGE_ID;
import static io.cloudslang.content.amazon.entities.constants.Constants.AwsParams.INSTANCE_ID;
import static io.cloudslang.content.amazon.entities.constants.Constants.AwsParams.NAME;
import static io.cloudslang.content.amazon.entities.constants.Constants.AwsParams.NO_REBOOT;
import static io.cloudslang.content.amazon.entities.constants.Constants.AwsParams.OPERATION_TYPE;
import static io.cloudslang.content.amazon.entities.constants.Constants.AwsParams.OWNER;
import static io.cloudslang.content.amazon.entities.constants.Constants.AwsParams.USER_GROUP;
import static io.cloudslang.content.amazon.entities.constants.Constants.AwsParams.USER_ID;
import static io.cloudslang.content.amazon.entities.constants.Constants.Miscellaneous.DOT;
import static io.cloudslang.content.amazon.entities.constants.Constants.Values.ONE;
import static io.cloudslang.content.amazon.entities.constants.Constants.Values.START_INDEX;
import static io.cloudslang.content.amazon.entities.constants.Inputs.ImageInputs.OWNERS;
import static io.cloudslang.content.amazon.entities.constants.Inputs.VpcInputs.VPC_IDS;
import static io.cloudslang.content.amazon.utils.InputsUtil.*;
import static java.lang.String.valueOf;
import static org.apache.commons.lang3.ArrayUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * Created by Tirla Alin.
 * Date: 30/9/2016.
 */
public class ImageUtils {
    private static final String ARCHITECTURE = "architecture";
    private static final String BLOCK_DEVICE_MAPPING_DELETE_ON_TERMINATION = "block-device-mapping.delete-on-termination";
    private static final String BLOCK_DEVICE_MAPPING_DEVICE_NAME = "block-device-mapping.device-name";
    private static final String BLOCK_DEVICE_MAPPING_SNAPSHOT_ID = "block-device-mapping.snapshot-id";
    private static final String BLOCK_DEVICE_MAPPING_VOLUME_SIZE = "block-device-mapping.volume-size";
    private static final String BLOCK_DEVICE_MAPPING_VOLUME_TYPE = "block-device-mapping.volume-type";
    private static final String BOTH_PERMISSION_INPUTS_EMPTY = "The [userIdsString] and [userGroupsString] inputs" +
            " cannot be both empty in order to add/remove permission launch on specified image.";
    private static final String DESCRIPTION_FILTER = "description";
    private static final String HYPERVISOR = "hypervisor";
    private static final String IMAGE_ID_FILTER = "image-id";
    private static final String IMAGE_TYPE = "image-type";
    private static final String IS_PUBLIC = "is-public";
    private static final String KERNEL_ID = "kernel-id";
    private static final String MANIFEST_LOCATION = "manifest-location";
    private static final String NAME_FILTER = "name";
    private static final String OWNER_ALIAS = "owner-alias";
    private static final String OWNER_ID = "owner-id";
    private static final String PLATFORM = "platform";
    private static final String PRODUCT_CODE = "product-code";
    private static final String PRODUCT_CODE_TYPE = "product-code.type";
    private static final String RAMDISK_ID = "ramdisk-id";
    private static final String ROOT_DEVICE_NAME = "root-device-name";
    private static final String ROOT_DEVICE_TYPE = "root-device-type";
    private static final String STATE = "state";
    private static final String STATE_REASON_CODE = "state-reason-code";
    private static final String STATE_REASON_MESSAGE = "state-reason-message";
    private static final String TAG_KEY = "tag-key";
    private static final String TAG_VALUE = "tag-value";

    private static final String VIRTUALIZATION_TYPE = "virtualization-type";

    public Map<String, String> getDeregisterImageQueryParamsMap(InputsWrapper wrapper) {
        Map<String, String> queryParamsMap = new HashMap<>();
        setCommonQueryParamsMap(queryParamsMap, wrapper.getCommonInputs().getAction(), wrapper.getCommonInputs().getVersion());
        queryParamsMap.put(IMAGE_ID, wrapper.getCustomInputs().getImageId());

        return queryParamsMap;
    }

    public Map<String, String> getCreateImageQueryParamsMap(InputsWrapper wrapper) {
        Map<String, String> queryParamsMap = new HashMap<>();
        setCommonQueryParamsMap(queryParamsMap, wrapper.getCommonInputs().getAction(), wrapper.getCommonInputs().getVersion());

        queryParamsMap.put(INSTANCE_ID, wrapper.getCustomInputs().getInstanceId());
        queryParamsMap.put(NAME, wrapper.getImageInputs().getImageName());

        setOptionalMapEntry(queryParamsMap, DESCRIPTION, wrapper.getImageInputs().getDescription(),
                isNotBlank(wrapper.getImageInputs().getDescription()));
        setOptionalMapEntry(queryParamsMap, NO_REBOOT, valueOf(wrapper.getImageInputs().isImageNoReboot()),
                isNotBlank(valueOf(wrapper.getImageInputs().isImageNoReboot())));

        return queryParamsMap;
    }

    public Map<String, String> getDescribeImageAttributeQueryParamsMap(InputsWrapper wrapper) {
        Map<String, String> queryParamsMap = new HashMap<>();
        setCommonQueryParamsMap(queryParamsMap, wrapper.getCommonInputs().getAction(), wrapper.getCommonInputs().getVersion());
        queryParamsMap.put(IMAGE_ID, wrapper.getCustomInputs().getImageId());
        queryParamsMap.put(ATTRIBUTE, wrapper.getCustomInputs().getAttribute());

        return queryParamsMap;
    }

    public Map<String, String> getModifyImageAttributeQueryParamsMap(InputsWrapper wrapper) {
        Map<String, String> queryParamsMap = new HashMap<>();
        List<String> userIds = getStringsList(wrapper.getImageInputs().getUserIdsString(), wrapper.getCommonInputs().getDelimiter());
        List<String> userGroupIds = getStringsList(wrapper.getImageInputs().getUserGroupsString(), wrapper.getCommonInputs().getDelimiter());

        if (userIds == null && userGroupIds == null) {
            throw new RuntimeException(BOTH_PERMISSION_INPUTS_EMPTY);
        }

        setCommonQueryParamsMap(queryParamsMap, wrapper.getCommonInputs().getAction(), wrapper.getCommonInputs().getVersion());
        queryParamsMap.put(ATTRIBUTE, wrapper.getCustomInputs().getAttribute());
        queryParamsMap.put(OPERATION_TYPE, wrapper.getCustomInputs().getOperationType());
        queryParamsMap.put(IMAGE_ID, wrapper.getCustomInputs().getImageId());
        putCollectionInQueryMap(queryParamsMap, USER_ID, userIds);
        putCollectionInQueryMap(queryParamsMap, USER_GROUP, userGroupIds);

        return queryParamsMap;
    }

    public Map<String, String> getDescribeImagesQueryParamsMap(InputsWrapper wrapper) {
        Map<String, String> queryParamsMap = new HashMap<>();
        setCommonQueryParamsMap(queryParamsMap, wrapper.getCommonInputs().getAction(), wrapper.getCommonInputs().getVersion());
        setOptionalMapEntry(queryParamsMap, EXECUTABLE_BY + DOT + ONE, valueOf(wrapper.getCustomInputs().getIdentityId()),
                isNotBlank(valueOf(wrapper.getCustomInputs().getIdentityId())));

        String[] ownersArray = getArrayWithoutDuplicateEntries(wrapper.getImageInputs().getOwners(),
                OWNERS, wrapper.getCommonInputs().getDelimiter());
        if(wrapper.getImageInputs().getOwners()!="Not relevant")
        {setSpecificQueryParamsMap(queryParamsMap, ownersArray, Constants.AwsParams.OWNERS);}
        else
        {
            queryParamsMap.remove("Owner.1");
        }
        String[] executableByArray = getArrayWithoutDuplicateEntries(wrapper.getImageInputs().getExecutableBy(),
                EXECUTABLE_BY, wrapper.getCommonInputs().getDelimiter());
        if(wrapper.getImageInputs().getExecutableBy()!="Not relevant")
        {setSpecificQueryParamsMap(queryParamsMap, executableByArray, EXECUTABLE_BY);}
        else
        {
            queryParamsMap.remove("ExecutableBy.1");
        }
        int currentIndex = ONE;
        if(wrapper.getCustomInputs().getPlatform()!="Not relevant")
        {currentIndex = appendOptionalFilters(queryParamsMap, PLATFORM, currentIndex, wrapper.getCustomInputs().getPlatform());}
        if(wrapper.getCustomInputs().getRootDeviceType()!="Not relevant")
        {currentIndex = appendOptionalFilters(queryParamsMap, ROOT_DEVICE_TYPE, currentIndex, wrapper.getCustomInputs().getRootDeviceType());}
        if(wrapper.getImageInputs().getType()!="Not relevant")
        {currentIndex = appendOptionalFilters(queryParamsMap, IMAGE_TYPE, currentIndex, wrapper.getImageInputs().getType());}
        if(wrapper.getImageInputs().getState()!="Not relevant")
        {appendOptionalFilters(queryParamsMap, STATE, currentIndex, wrapper.getImageInputs().getState());}
        return queryParamsMap;
    }

    private int appendOptionalFilters(Map<String, String> queryParamsMap, String filterName, int filterIndex, String filterValue) {
        if (isNotBlank(filterValue)) {
            queryParamsMap.put(String.format(FILTER_NAME, filterIndex), filterName);
            queryParamsMap.put(String.format(FILTER_VALUE, filterIndex), filterValue);

            return filterIndex + ONE;
        }

        return filterIndex;
    }

    private void setSpecificQueryParamsMap(Map<String, String> queryParamsMap, String[] inputArray, String specificString) {
        if (isNotEmpty(inputArray)) {
            for (int index = START_INDEX; index < inputArray.length; index++) {
                queryParamsMap.put(getQueryParamsSpecificString(specificString, index), inputArray[index]);
            }
        }
    }

    public Map<String, String> getResetImageAttributeQueryParamsMap(InputsWrapper wrapper) {
        Map<String, String> queryParamsMap = new HashMap<>();
        setCommonQueryParamsMap(queryParamsMap, wrapper.getCommonInputs().getAction(), wrapper.getCommonInputs().getVersion());
        queryParamsMap.put(ATTRIBUTE, wrapper.getCustomInputs().getAttribute());
        queryParamsMap.put(IMAGE_ID, wrapper.getCustomInputs().getImageId());

        return queryParamsMap;
    }
}
