package Feature.InContact.mediaTypes.email_scenarios

import Feature.BaseExecution
import Model.BaseModel
import RestAPI.InContactAPI._
import SetUp.Cluster.ClusterConfig
import SetUp.LoadSimulationSetUp

import scala.concurrent.duration._
import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder

class OutboundEmail(OutboundModel: BaseModel) extends BaseExecution(OutboundModel) {

  /**
    *  testExecution
    * @note workflow to execute
    * @return ChainBuilder
    */
  override def testExecution: ChainBuilder = {
    exec(AdminApi.getSkillId(skillName = ClusterConfig.outboundEmailSkill))
      .exec(AgentApi.createOutboundEmail())
      .during(LoadSimulationSetUp.executionTime minutes) {
        pause(5 seconds)
          .exec(AgentApi.sendOutboundEmail())
          .exec(methods.acceptIfContactExist())
          .pause(LoadSimulationSetUp.skillDuration minutes)
          .exec(AgentApi.endContact("v15.0"))
          .exec(methods.isTokenExpired())
      }
  }
}
