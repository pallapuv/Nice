package Feature.InContact.mediaTypes.email_scenarios

import Feature.BaseExecution
import Model.BaseModel
import RestAPI.DigitalAPI.DigitalAPI
import SetUp.LoadSimulationSetUp
import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder


class StatusAgents(OutboundModel: BaseModel) extends BaseExecution(OutboundModel) {


  /**
    *  testExecution
    * @note workflow to execute
    * @return ChainBuilder
    */
  override def testExecution: ChainBuilder = {
      during(LoadSimulationSetUp.executionTime minutes) {
          exec(methods.isTokenExpired())
      }
  }

}
