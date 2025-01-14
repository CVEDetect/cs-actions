/*
 * (c) Copyright 2022 EntIT Software LLC, a Micro Focus company, L.P.
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


package io.cloudslang.content.xml.factory;

import io.cloudslang.content.xml.entities.ActionType;
import io.cloudslang.content.xml.services.OperationService;
import io.cloudslang.content.xml.services.impl.AppendOperationServiceImpl;
import io.cloudslang.content.xml.services.impl.DeleteOperationServiceImpl;
import io.cloudslang.content.xml.services.impl.InsertOperationServiceImpl;
import io.cloudslang.content.xml.services.impl.MoveOperationServiceImpl;
import io.cloudslang.content.xml.services.impl.RenameOperationServiceImpl;
import io.cloudslang.content.xml.services.impl.SubnodeOperationServiceImpl;
import io.cloudslang.content.xml.services.impl.UpdateOperationServiceImpl;

/**
 * Created by moldovas on 7/8/2016.
 */
public class OperationFactory {
    public static OperationService getOperation(ActionType action) {
        switch (action) {
            case delete:
                return new DeleteOperationServiceImpl();
            case insert:
                return new InsertOperationServiceImpl();
            case append:
                return new AppendOperationServiceImpl();
            case subnode:
                return new SubnodeOperationServiceImpl();
            case move:
                return new MoveOperationServiceImpl();
            case rename:
                return new RenameOperationServiceImpl();
            case update:
                return new UpdateOperationServiceImpl();
            default:
                throw new RuntimeException("Invalid/unsupported action provided as input.");
        }
    }
}
