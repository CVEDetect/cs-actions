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

package io.cloudslang.content.hashicorp.terraform.utils;

import io.cloudslang.content.constants.OutputNames;

public class Outputs extends OutputNames {
    public static class CommonOutputs {
        public static final String DOCUMENT = "document";

    }

    public static class ListOAuthClientOutputs {
        public static final String OAUTH_TOKEN_ID = "oauthTokenId";
    }

    public static class CreateWorkspaceOutputs {
        public static final String WORKSPACE_ID = "workspaceId";
    }

    public static class CreateOrganizationOutputs {
        public static final String ORGANIZATION_ID = "organizationId";
    }

    public static class CreateRunOutputs {
        public static final String RUN_ID = "runId";

    }

    public static class CreateVariableOutputs {
        public static final String VARIABLE_ID = "variableId";
    }

    public static class CreateWorkspaceVariableOutputs {
        public static final String WORKSPACE_VARIABLE_ID = "workspaceVariableId";
    }


    public static class ListWorkspacesOutputs {
        public static final String WORKSPACE_LIST = "workspaceList";
    }

    public static class ListOrganizationsOutputs {
        public static final String ORGANIZATION_NAME_LIST = "organizationNameList";
    }


    public static class GetCurrentStateVersionOutputs {
        public static final String STATE_VERSION_ID = "stateVersionId";
        public static final String HOSTED_STATE_DOWNLOAD_URL = "hostedStateDownloadUrl";
    }

}
