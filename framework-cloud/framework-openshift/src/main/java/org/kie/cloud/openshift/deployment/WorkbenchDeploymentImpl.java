/*
 * Copyright 2017 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/

package org.kie.cloud.openshift.deployment;

import java.net.URI;
import java.net.URL;

import org.kie.cloud.api.deployment.WorkbenchDeployment;
import org.kie.cloud.openshift.resource.Project;

public class WorkbenchDeploymentImpl extends OpenShiftDeployment implements WorkbenchDeployment {

    private URL url;
    private URL secureUrl;
    private URI webSocketUri;
    private String username;
    private String password;

    private String serviceName;

    public WorkbenchDeploymentImpl(Project project) {
        super(project);
    }

    @Override public URL getUrl() {
        if (url == null) {
            url = getHttpRouteUrl(getServiceName());
        }
        return url;
    }

    @Override public URL getSecureUrl() {
        if (secureUrl == null) {
            secureUrl = getHttpsRouteUrl(getServiceName());
        }
        return secureUrl;
    }

    @Override public URI getWebSocketUri() {
        if (webSocketUri == null) {
            webSocketUri = getWebSocketRouteUri(getServiceName());
        }
        return webSocketUri;
    }

    @Override public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getServiceName() {
        if (serviceName == null) {
            serviceName = ServiceUtil.getWorkbenchServiceName(getOpenShiftUtil());
        }
        return serviceName;
    }

    @Override public void waitForScale() {
        super.waitForScale();
        if (getInstances().size() > 0) {
            RouterUtil.waitForRouter(getUrl());
        }
    }
}
