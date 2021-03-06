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

package org.kie.cloud.openshift.constants;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

import org.kie.cloud.api.constants.Constants;

public class OpenShiftConstants implements Constants {

    public static final String OPENSHIFT_URL = "openshift.master.url";
    public static final String OPENSHIFT_USER = "openshift.username";
    public static final String OPENSHIFT_PASSWORD = "openshift.password";

    /**
     * Project name prefix - to simplify identification of projects created in OpenShift.
     */
    public static final String NAMESPACE_PREFIX = "openshift.namespace.prefix";
    /**
     * Property name to configure Openshift router timeout.
     */
    public static final String HAPROXY_ROUTER_TIMEOUT = "haproxy.router.openshift.io/timeout";
    /**
     * Used Kie application name. Needed for identification of services within the project.
     */
    public static final String KIE_APP_NAME = "kie.app.name";

    /**
     * URL pointing to OpenShift resource file containing keystore for HTTPS communication.
     */
    public static final String KIE_APP_SECRET = "kie.app.secret";

    /**
     * URL pointing to OpenShift resource file containing image streams with all available images.
     */
    public static final String KIE_IMAGE_STREAMS = "kie.image.streams";

    /**
     * URL pointing to OpenShift template file containing Workbench and Kie server.
     */
    public static final String KIE_APP_TEMPLATE_WORKBENCH_KIE_SERVER = "kie.app.template.workbench.kie-server";

    /**
     * URL pointing to OpenShift template file containing Workbench and Kie server.
     */
    public static final String KIE_APP_TEMPLATE_WORKBENCH_KIE_SERVER_PERSISTENT = "kie.app.template.workbench.kie-server.persistent";

    /**
     * URL pointing to OpenShift template file containing Kie server and external database.
     */
    public static final String KIE_APP_TEMPLATE_KIE_SERVER_DATABASE_EXTERNAL = "kie.app.template.kie-server.database.external";

    /**
     * URL pointing to OpenShift template file containing just Kie server.
     */
    public static final String KIE_APP_TEMPLATE_KIE_SERVER = "kie.app.template.kie-server";
    /**
     * URL pointing to OpenShift template file containing just Kie server S2I.
     */
    public static final String KIE_APP_TEMPLATE_KIE_SERVER_HTTPS_S2I = "kie.app.template.kie-server-https-s2i";

    /**
     * URL pointing to OpenShift template file containing Kie server and PostgreSQL database.
     */
    public static final String KIE_APP_TEMPLATE_KIE_SERVER_POSTGRESQL = "kie.app.template.kie-server.postgresql";

    /**
     * URL pointing to OpenShift template file containing Kie server and MySQL database.
     */
    public static final String KIE_APP_TEMPLATE_KIE_SERVER_MYSQL = "kie.app.template.kie-server.mysql";

    /**
     * URL pointing to OpenShift template file containing Workbench monitoring console and Smart router.
     */
    public static final String KIE_APP_TEMPLATE_CONSOLE_SMARTROUTER = "kie.app.template.workbench-monitoring.smartrouter";

    /**
     * URL pointing to OpenShift template file containing Workbench monitoring console Smart router, two Kie servers and two databases.
     */
    public static final String CONSOLE_SMARTROUTER_TWO_KIE_SERVERS_TWO_DATABASES = "kie.app.template.workbench-monitoring.smartrouter.two-kieservers.two-databases";

    /**
     * URL pointing to OpenShift template file containing clustered Workbench monitoring console Smart router, two Kie servers and two databases.
     */
    public static final String CLUSTERED_CONSOLE_SMARTROUTER_TWO_KIE_SERVERS_TWO_DATABASES = "kie.app.template.clustered-workbench-monitoring.smartrouter.two-kieservers.two-databases";

    /**
     * URL pointing to OpenShift template file containing clustered Workbench, Kie server and database.
     */
    public static final String CLUSTERED_WORKBENCH_KIE_SERVER_DATABASE_PERSISTENT = "kie.app.template.clustered-workbench.kieserver.database";

    /**
     * URL pointing to OpenShift template file containing Workbench monitoring console.
     */
    public static final String KIE_APP_TEMPLATE_CONSOLE = "kie.app.template.workbench-monitoring";

    /**
     * URL pointing to OpenShift template file containing Smart router.
     */
    public static final String KIE_APP_TEMPLATE_SMARTROUTER = "kie.app.template.smartrouter";

    /**
     * URL pointing to OpenShift template file containing Controller.
     */
    public static final String KIE_APP_TEMPLATE_CONTROLLER = "kie.app.template.controller";
    /**
     * URL pointing to OpenShift template file containing Workbench.
     */
    public static final String KIE_APP_TEMPLATE_WORKBENCH = "kie.app.template.workbench";

    public static final String KIE_APP_TEMPLATE_PROD_IMMUTABLE_MONITOR = "kie.app.template.prod.immutable.monitor";

    public static final String SSO_APP_TEMPLATE ="sso.app.template";
    public static final String SSO_APP_SECRETS = "sso.app.secrets";
    public static final String SSO_IMAGE_STREAMS = "sso.image.streams";

    /**
     * File path pointing to folder containing JDBC driver scripts.
     */
    public static final String KIE_JDBC_DRIVER_SCRIPTS = "kie.jdbc.driver.scripts";

    /**
     * URL pointing to JDBC driver binary.
     */
    public static final String KIE_JDBC_DRIVER_BINARY_URL = "kie.jdbc.driver.binary.url";

    public static String getOpenShiftUrl() {
        return System.getProperty(OPENSHIFT_URL);
    }

    public static String getOpenShiftUserName() {
        return System.getProperty(OPENSHIFT_USER);
    }

    public static String getOpenShiftPassword() {
        return System.getProperty(OPENSHIFT_PASSWORD);
    }

    public static Optional<String> getNamespacePrefix() {
        return Optional.ofNullable(System.getProperty(NAMESPACE_PREFIX));
    }

    public static String getKieAppSecret() {
        return System.getProperty(KIE_APP_SECRET);
    }

    public static String getKieImageStreams() {
        return System.getProperty(KIE_IMAGE_STREAMS);
    }

    public static String getSSOImageStreams() {
        return System.getProperty(SSO_IMAGE_STREAMS);
    }

    public static String getKieApplicationName() {
        return System.getProperty(KIE_APP_NAME);
    }

    public static File getKieJdbcDriverScriptsFolder() {
        String kieJdbcDriverScriptsFolder = System.getProperty(KIE_JDBC_DRIVER_SCRIPTS);
        return new File(kieJdbcDriverScriptsFolder);
    }

    public static URL getKieJdbcDriverBinaryUrl() {
        String kieJdbcDriverBinaryUrl = System.getProperty(KIE_JDBC_DRIVER_BINARY_URL);
        try {
            return new URL(kieJdbcDriverBinaryUrl);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Malformed URL of Kie server JDBC driver binary.", e);
        }
    }

    /**
     * @return Name of the secret containing keystore file for HTTPS communication.
     */
    public static String getKieApplicationSecretName() {
        return "kie-app-secret";
    }

    @Override
    public void initConfigProperties() {
        // init XTF configuration for OpenShift
        System.setProperty("xtf.config.master.url", getOpenShiftUrl());
        System.setProperty("xtf.config.master.username", getOpenShiftUserName());
        System.setProperty("xtf.config.master.password", getOpenShiftPassword());
        System.setProperty("xtf.config.master.admin.username", getOpenShiftUserName());
        System.setProperty("xtf.config.master.admin.password", getOpenShiftPassword());
        // TODO delete this when raising XTF version
        System.setProperty("xtf.config.openshift.version", "3.9.41");
    }
}
