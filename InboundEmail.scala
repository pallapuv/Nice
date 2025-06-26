package Feature.InContact.mediaTypes.email_scenarios

import Feature.BaseExecution
import Model.BaseModel
import RestAPI.InContactAPI._
import SetUp.Cluster.ClusterConfig
import SetUp.LoadSimulationSetUp
import Utilities.Emuns.StatusType
import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder

import scala.util.Random

class InboundEmail(inboundEmailModel: BaseModel) extends BaseExecution(inboundEmailModel)  {

  /**
    *  testExecution
    * @note workflow to execute
    * @return ChainBuilder
    */
  override def testExecution: ChainBuilder = {
    exec(methods.retryMax(AdminApi.getSkillId(skillName = ClusterConfig.inboundEmailSkill)))
      .exec(methods.retryMax(AdminApi.getPointsOfContacts()))
      .during(duration = LoadSimulationSetUp.executionTime minutes) {
        exec(EmailApi.sendInboundEmail())
          .exec(methods.getContactId(StatusType.GetContactIncomingEmailId))
          .exec(AgentApi.acceptContact("V15.0"))
          .pause(LoadSimulationSetUp.skillDuration minutes)
          .exec(AgentApi.endContact())
          .exec(methods.isTokenExpired())
      }
  }
}

