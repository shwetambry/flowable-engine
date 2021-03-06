/* Licensed under the Apache License, Version 2.0 (the "License");
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
package org.flowable.test.spring.boot;

import flowable.Application;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.idm.api.IdmIdentityService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class BasicApplicationTest {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private IdmIdentityService idmIdentityService;

    @Test
    public void launchProcessDefinition() {
        assertThat(idmIdentityService).as("Idm identity service").isNotNull();
        assertThat(repositoryService.createProcessDefinitionQuery().count())
            .as("All process definitions")
            .isEqualTo(2);
        List<ProcessDefinition> processDefinitionList = repositoryService.createProcessDefinitionQuery()
            .processDefinitionKey("waiter")
            .list();
        assertThat(processDefinitionList)
            .extracting(ProcessDefinition::getKey)
            .as("First process definition with definition key")
            .containsExactly("waiter");
    }
}
