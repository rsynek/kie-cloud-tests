/*
 * Copyright 2018 JBoss by Red Hat.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kie.cloud.integrationtests.testproviders;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.kie.api.KieServices;
import org.kie.api.command.BatchExecutionCommand;
import org.kie.api.command.Command;
import org.kie.api.command.KieCommands;
import org.kie.api.runtime.ExecutionResults;
import org.kie.cloud.api.deployment.KieServerDeployment;
import org.kie.cloud.api.deployment.WorkbenchDeployment;
import org.kie.cloud.common.provider.KieServerClientProvider;
import org.kie.cloud.common.provider.KieServerControllerClientProvider;
import org.kie.cloud.git.GitProvider;
import org.kie.cloud.git.GitProviderService;
import org.kie.cloud.integrationtests.Kjar;
import org.kie.cloud.integrationtests.util.WorkbenchUtils;
import org.kie.cloud.maven.MavenDeployer;
import org.kie.server.api.model.KieContainerResource;
import org.kie.server.api.model.KieContainerStatus;
import org.kie.server.api.model.KieServerInfo;
import org.kie.server.api.model.ReleaseId;
import org.kie.server.api.model.ServiceResponse;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.RuleServicesClient;
import org.kie.server.controller.client.KieServerControllerClient;
import org.kie.server.integrationtests.shared.KieServerAssert;

public class FireRulesTestProvider {

    private static final String LIST_NAME = "list";
    private static final String LIST_OUTPUT_NAME = "output-list";
    private static final String KIE_SESSION = "defaultKieSession";
    private static final String HELLO_RULE = "Hello.";
    private static final String WORLD_RULE = "World.";

    private static KieCommands commandsFactory = KieServices.Factory.get().getCommands();

    static {
        MavenDeployer.buildAndDeployMavenProject(ClassLoader.class.getResource("/kjars-sources/hello-rules-snapshot").getFile());
    }

    public static void testFireRules(KieServerDeployment kieServerDeployment) {
        String containerId = "testFireRules";
        KieServicesClient kieServerClient = KieServerClientProvider.getKieServerClient(kieServerDeployment);

        ServiceResponse<KieContainerResource> createContainer = kieServerClient.createContainer(containerId, new KieContainerResource(containerId, new ReleaseId(Kjar.HELLO_RULES_SNAPSHOT.getGroupId(), Kjar.HELLO_RULES_SNAPSHOT.getName(), Kjar.HELLO_RULES_SNAPSHOT.getVersion())));
        KieServerAssert.assertSuccess(createContainer);

        try {
            fireRules(kieServerDeployment, containerId);
        } finally {
            kieServerClient.disposeContainer(containerId);
        }
    }

    public static void testDeployFromWorkbenchAndFireRules(WorkbenchDeployment workbenchDeployment, KieServerDeployment kieServerDeployment) {
        String containerId = "testDeployFromWorkbenchAndFireRules";
        String containerAlias = "alias-testDeployFromWorkbenchAndFireRules";
        GitProvider gitProvider = new GitProviderService().createGitProvider();
        KieServerControllerClient kieControllerClient = KieServerControllerClientProvider.getKieServerControllerClient(workbenchDeployment);
        KieServicesClient kieServerClient = KieServerClientProvider.getKieServerClient(kieServerDeployment);
        KieServerInfo serverInfo = kieServerClient.getServerInfo().getResult();

        String repositoryName = gitProvider.createGitRepositoryWithPrefix(workbenchDeployment.getNamespace(), ClassLoader.class.getResource("/kjars-sources/hello-rules").getFile());
        try {
            WorkbenchUtils.deployProjectToWorkbench(gitProvider.getRepositoryUrl(repositoryName), workbenchDeployment, Kjar.HELLO_RULES.getName());

            WorkbenchUtils.saveContainerSpec(kieControllerClient, serverInfo.getServerId(), serverInfo.getName(), containerId, containerAlias, Kjar.HELLO_RULES, KieContainerStatus.STARTED);

            KieServerClientProvider.waitForContainerStart(kieServerDeployment, containerId);

            fireRules(kieServerDeployment, containerId);
        } finally {
            gitProvider.deleteGitRepository(repositoryName);
            kieControllerClient.deleteContainerSpec(serverInfo.getServerId(), containerId);
        }
    }

    private static void fireRules(KieServerDeployment kieServerDeployment, String containerId) {
        RuleServicesClient ruleClient = KieServerClientProvider.getRuleClient(kieServerDeployment);

        List<Command<?>> commands = new ArrayList<>();
        BatchExecutionCommand batchExecutionCommand = commandsFactory.newBatchExecution(commands, KIE_SESSION);
        commands.add(commandsFactory.newSetGlobal(LIST_NAME, new ArrayList<>(), LIST_OUTPUT_NAME));
        commands.add(commandsFactory.newFireAllRules());
        commands.add(commandsFactory.newGetGlobal(LIST_NAME, LIST_OUTPUT_NAME));

        ServiceResponse<ExecutionResults> response = ruleClient.executeCommandsWithResults(containerId, batchExecutionCommand);

        KieServerAssert.assertSuccess(response);
        ExecutionResults result = response.getResult();
        assertThat(result).isNotNull();
        List<String> outcome = (List<String>) result.getValue(LIST_OUTPUT_NAME);
        assertThat(outcome).hasSize(2);
        assertThat(outcome.get(0)).startsWith(HELLO_RULE);
        assertThat(outcome.get(1)).startsWith(WORLD_RULE);
    }
}
