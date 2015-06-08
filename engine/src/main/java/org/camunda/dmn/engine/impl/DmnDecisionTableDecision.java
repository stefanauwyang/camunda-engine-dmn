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

package org.camunda.dmn.engine.impl;

import java.util.ArrayList;
import java.util.List;

import org.camunda.bpm.model.dmn.instance.Decision;
import org.camunda.bpm.model.dmn.instance.DecisionTable;
import org.camunda.bpm.model.dmn.instance.Rule;
import org.camunda.dmn.engine.DmnDecision;
import org.camunda.dmn.engine.DmnDecisionContext;
import org.camunda.dmn.engine.DmnResult;
import org.camunda.dmn.engine.DmnRule;

public class DmnDecisionTableDecision implements DmnDecision {

  protected String id;
  protected List<DmnRule> rules = new ArrayList<DmnRule>();

  public DmnDecisionTableDecision(Decision decision) {
    id = decision.getId();
    DecisionTable decisionTable = (DecisionTable) decision.getExpression();
    for (Rule rule : decisionTable.getRules()) {
      rules.add(new DmnRuleImpl(rule));
    }
  }

  public String getId() {
    return id;
  }

  public DmnResult evaluate(DmnDecisionContext context) {
    DmnResult result = new DmnResultImpl();
    for (DmnRule rule : rules) {
      if (rule.isApplicable(context)) {
        result.addOutput(new DmnOutputImpl());
      }
    }
    return result;
  }

}