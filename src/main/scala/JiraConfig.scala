package io.yard.module.jira

import play.api.libs.json._
import play.api.libs.functional.syntax._

import io.yard.common.models.{ModuleConfiguration, Organization}
import io.yard.module.core.Api

case class JiraColors(
  created: String = "",
  updated: String = "",
  deleted: String = "",
  commented: String = ""
)

object JiraColors {
  implicit val jiraColorsFormat = Json.format[JiraColors]
}

case class JiraWorklog(
  enabled: Boolean = false
)

object JiraWorklog {
  implicit val jiraWorklogFormat = Json.format[JiraWorklog]
}

case class JiraBot(
  name: String = "JIRA",
  icon: String = ""
)

object JiraBot {
  implicit val jiraBotFormat = Json.format[JiraBot]
}

case class JiraConfig(
  url: String,
  authBasic: Option[String] = None,
  blackList: Seq[String] = Seq.empty,
  bot: JiraBot = JiraBot(),
  colors: JiraColors = JiraColors(),
  worklog: JiraWorklog = JiraWorklog()
) extends ModuleConfiguration

object JiraConfig {
  implicit val jiraConfigFormat = Json.format[JiraConfig]

  def from(org: Organization) = Api.connector.read[JiraConfig](JiraModule.value, Some(org))
  def from(orgName: String) = Api.connector.read[JiraConfig](JiraModule.value, Some(Api.organizations.from(orgName)))
}


 /*extends io.yard.common.utils.Config {
  object jira {
    def url = getString("yardio.jira.url")
    def authBasic = getString("yardio.jira.auth.basic")
    def blackList = getStringSeq("yardio.jira.blacklist")

    object bot {
      def name = config.getString("yardio.jira.bot.name")
      def icon = config.getString("yardio.jira.bot.icon")
    }

    object colors {
      object issue {
        def created = config.getString("yardio.jira.colors.issue.created")
        def updated = config.getString("yardio.jira.colors.issue.updated")
        def deleted = config.getString("yardio.jira.colors.issue.deleted")
      }

      def comment = config.getString("yardio.jira.colors.comment")
    }

    object worklog {
      def enabled = config.getBoolean("yardio.jira.worklog.enabled") getOrElse true
    }
  }
}*/
