package Feature.InContact.mediaTypes.email_scenarios

import Feature.BaseExecution
import Model.BaseModel
import RestAPI.DigitalAPI.DigitalAPI
import RestAPI.InContactAPI.EmailApi
import SetUp.LoadSimulationSetUp
import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder



class InboundEmailDFO(OutboundModel: BaseModel) extends BaseExecution(OutboundModel) {


  /**
    *  testExecution
    * @note workflow to execute
    * @return ChainBuilder
    */
  override def testExecution: ChainBuilder = {
    exec(DigitalAPI.getChannelId(channelName = "digital_email"))
      .during(LoadSimulationSetUp.executionTime minutes) {
          exec(DigitalAPI.sendInboundEmailDFOAPI())
          .pause(5)
          .exec(DigitalAPI.PutCloseCase)
          .exec(methods.isTokenExpired())
      }
  }

}
