package RestAPI.InContactAPI

import RestAPI.BasicApi
import Utilities.Emuns.StatusType

trait InContactApi extends BasicApi {

  /**
    * Gets json path event
    */
  protected def getJsonPathEvents(eventKey: String, eventValue: String): String = {
    "$.events..".concat(eventKey)
  }

  /**
    * Gets json path from the given event
    */
  protected def getJsonPathByEvent(event: String): String = {
    event match {
      case StatusType.GetAgentLegId => "$.events[?(@.Status==\"Dialing\")].AgentLegId"
      case StatusType.softPhoneDNIS => "$.events[?(@.eventName==\"WebRTC\")].softPhoneDNIS"
      case StatusType.OutReason => "$.events[?(@.NextStates)].NextStates[?(@.State)].OutReason"
      case _ => "$.events[?(@.NextStates)].NextStates[?(@.State)].State"
    }
  }


  /**
    * Gets json path from the given event
    */
  protected def getJsonPathByContactType(event: String): String = {
    event match {
      case StatusType.GetContactChatId => "$.events[?(@.Status == \"Incoming\" && @.Skill == \"${SKILL_ID}\" && @.IsActive== \"True\")].ContactID"
      case StatusType.GetContactIncomingChatId => "$.events[?(@.Status == \"Incoming\" && @.Skill == \"${SKILL_ID}\" && @.IsActive== \"True\")].ContactID"
      case StatusType.GetContactActiveChatId => "$.events[?(@.Skill == \"${SKILL_ID}\" && @.IsActive== \"True\")].ContactID"
      case StatusType.GetContactEmailId => "$.events[?(@.Status == \"Active\" && @.Skill == \"${SKILL_ID}\")].ContactId"
      case StatusType.GetContactIncomingEmailId => "$.events[?(@.Status == \"Incoming\" && @.Skill == \"${SKILL_ID}\")].ContactId"
      case StatusType.GetContactWorkItemId => "$.events[?(@.Status == \"Incoming\" && @.SkillId == \"${SKILL_ID}\")].ContactID"
      case StatusType.GetContactOutboundPhoneId => "$.events[?(@.Status == \"Dialing\"  || @.Status == \"Active\" && @.Skill == \"${SKILL_ID}\")].ContactID"
      case StatusType.GetContactOutboundPBBPhoneId => "$.events[?(@.Status == \"Dialing\"  || @.Status == \"Active\")].ContactID"
      case _ => "$.events[?(@.Status == \"".concat(event).concat("\")].ContactID")
    }
  }

  /**
    * Gets Request status from the give event
    */
  protected def getStatusRequest(event: String): String = {
    event match {
      case StatusType.StartRefusedSession => StatusType.success
      case StatusType.TurnAvailableRefusedSession => StatusType.success
      case StatusType.StartSession => StatusType.success
      case StatusType.TurnAvailable => StatusType.success
      case StatusType.EndSession => StatusType.successAndRedirect
      case StatusType.EndCall => StatusType.successAndRedirect
      case StatusType.Busy => StatusType.successAndRedirect
      case StatusType.TurnUnavailable => StatusType.successAndRedirect
    }
  }
}
