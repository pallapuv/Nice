package Feature.InContact.mediaTypes.email_scenarios

import Feature.BaseExecution
import Model.BaseModel
import RestAPI.DigitalAPI.DigitalAPI
import SetUp.LoadSimulationSetUp
import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder


class InboundWithReplyEmailDFO(OutboundModel: BaseModel) extends BaseExecution(OutboundModel) {


  /**
    *  testExecution
    * @note workflow to execute
    * @return ChainBuilder
    */
  override def testExecution: ChainBuilder = {
    exec(DigitalAPI.getChannelId(channelName = "digital_email"))
      .during(LoadSimulationSetUp.executionTime minutes) {
          exec(DigitalAPI.sendInboundEmailDFOAPI())
          .pause(2)
          .exec(DigitalAPI.getMessagesDFOThreadID())
          .exec(DigitalAPI.sendReplyOutboundEmailDFO)
          .pause(2)
          .exec(DigitalAPI.PutCloseCase)
          .exec(methods.isTokenExpired())
      }
  }

}
