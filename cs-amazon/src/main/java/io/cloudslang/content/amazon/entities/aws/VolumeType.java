/*
 * (c) Copyright 2019 EntIT Software LLC, a Micro Focus company, L.P.
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



package io.cloudslang.content.amazon.entities.aws;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static io.cloudslang.content.amazon.entities.constants.Constants.Miscellaneous.NOT_RELEVANT;

/**
 * Created by Mihai Tusa.
 * 6/15/2016.
 */
public enum VolumeType {
    STANDARD,
    IO1;

    public static String getValue(String input) {
        if (isBlank(input))
            return NOT_RELEVANT;
        else
            return input;
    }
}
