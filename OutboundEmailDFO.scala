package Feature.InContact.mediaTypes.email_scenarios

import Feature.BaseExecution
import Model.BaseModel
import RestAPI.DigitalAPI.DigitalAPI
import RestAPI.InContactAPI._
import SetUp.Cluster.ClusterConfig
import SetUp.LoadSimulationSetUp
import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._
import scala.concurrent.duration._

class OutboundEmailDFO(OutboundModel: BaseModel) extends BaseExecution(OutboundModel) {

  /**
    *  testExecution
    * @note workflow to execute
    * @return ChainBuilder
    */
  override def testExecution: ChainBuilder = {
    exec(DigitalAPI.getChannelId(channelName = "digital_email"))
      .during(LoadSimulationSetUp.executionTime minutes) {
        exec(DigitalAPI.sendOutboundEmailDFO)
          .pause(2)
          .exec(DigitalAPI.PutCloseCase)
          .exec(methods.isTokenExpired())
      }
  }

}
