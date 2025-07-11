package Feature.InContact.mediaTypes.email_scenarios

import Feature.BaseExecution
import Model.BaseModel
import RestAPI.DigitalAPI.DigitalAPI
import SetUp.LoadSimulationSetUp
import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder

class OutboundEmailDFOInline(OutboundModel: BaseModel) extends BaseExecution(OutboundModel) {

  /**
    *  testExecution
    * @note workflow to execute
    * @return ChainBuilder
    */
  override def testExecution: ChainBuilder = {
    exec(DigitalAPI.getChannelId(channelName = "digital_email"))
      .during(LoadSimulationSetUp.executionTime minutes) {
        exec(DigitalAPI.uploadAttachmentTemporaryInline)
          .exec(DigitalAPI.sendOutboundEmailDFOInline)
          .pause(2)
          .exec(DigitalAPI.PutCloseCase)
          .exec(methods.isTokenExpired())
      }
  }

}
