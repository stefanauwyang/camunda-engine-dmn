/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.camunda.bpm.dmn.engine.test;

import static org.camunda.bpm.dmn.engine.test.asserts.DmnEngineTestAssertions.assertThat;

import java.io.InputStream;
import java.util.List;

import org.camunda.bpm.dmn.engine.DmnDecision;
import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.dmn.engine.DmnEngine;
import org.camunda.bpm.dmn.engine.DmnEngineConfiguration;
import org.camunda.bpm.dmn.engine.impl.DefaultDmnEngineConfiguration;
import org.camunda.bpm.dmn.engine.test.asserts.DmnDecisionTableResultAssert;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.commons.utils.IoUtil;
import org.junit.Before;
import org.junit.Rule;

public abstract class DmnEngineTest {

  @Rule
  public DmnEngineTestRule dmnEngineRule = new DmnEngineTestRule(getDmnEngineConfiguration());

  public DmnEngine dmnEngine;
  public DmnDecision decision;
  public VariableMap variables;

  public DmnEngineConfiguration getDmnEngineConfiguration() {
    return new DefaultDmnEngineConfiguration();
  }

  @Before
  public void initDmnEngine() {
    dmnEngine = dmnEngineRule.getDmnEngine();
  }

  @Before
  public void initDecision() {
    decision = dmnEngineRule.getDecision();
  }

  @Before
  public void initVariables() {
    variables = Variables.createVariables();
  }

  // parsing //////////////////////////////////////////////////////////////////

  public List<DmnDecision> parseDecisionsFromFile(String filename) {
    InputStream inputStream = IoUtil.fileAsStream(filename);
    return dmnEngine.parseDecisions(inputStream);
  }

  public DmnDecision parseDecisionFromFile(String decisionKey, String filename) {
    InputStream inputStream = IoUtil.fileAsStream(filename);
    return dmnEngine.parseDecision(decisionKey, inputStream);
  }

  // evaluations //////////////////////////////////////////////////////////////

  public DmnDecisionTableResult evaluateDecisionTable() {
    return dmnEngine.evaluateDecisionTable(decision, variables);
  }

  // assertions ///////////////////////////////////////////////////////////////

  public DmnDecisionTableResultAssert assertThatDecisionTableResult() {
    DmnDecisionTableResult results = evaluateDecisionTable();
    return assertThat(results);
  }

}