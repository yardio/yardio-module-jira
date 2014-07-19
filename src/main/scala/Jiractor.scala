package io.yard.module.jira

import scala.concurrent.Future
import scala.util.Failure
import play.api.libs.concurrent.Execution.Implicits._

import akka.actor.Actor

import io.yard.common.models.{ Message, Command }
import io.yard.module.core.Api

class Jiractor extends Actor with io.yard.common.utils.Answer {
  def receive = {
    case h: Message ⇒ handleMessage(h)
    case c: Command ⇒ handleCommand(c)
    case _          ⇒ ()
  }

  def handleCommand(command: Command) = {
    /*command.args.headOption match {
      case Some("") ⇒ asyncError("/jira command must have at least one argument.")
      case Some("new") ⇒ {
        JiraServices.create(command.args(2), command.args(3), command.channel_name.toUpperCase, command.args(1)).map { r ⇒
          if (r.keys.contains("key")) {
            val key = (r \ "key").toString
            val link = JiraServices.issueUrl(key)
            ok(s"<@${command.user_name}> juste created JIRA <${link}|${key}>")
          } else {
            error(r.toString)
          }
        }
      }
      case Some(key) ⇒ {
        val link = JiraServices.issueUrl(key)
        asyncOk(s"<${link}|${key}>")
      }
      case _ ⇒ asyncError("/jira command must have at least one argument.")
    }*/
  }

  val jiraRegex = "([A-Z]+-[0-9]+)".r

  def findIssueKeys(str: String): List[String] =
    for {
      jiraRegex(issueKey) ← (jiraRegex findAllIn str).toList
    } yield issueKey

  def handleMessage(message: Message) = {
    implicit val config = JiraConfig("")

    val futureMessages =
      findIssueKeys(message.text) map { key ⇒
        JiraServices
          .get(key)
          .map(_.fold(s"Couldn't find an issue for key $key.")(_.asMessage))
          .recover {
            case ex ⇒
              println(s"Error while reading from JIRA: ${ex}") // TODO: use log
              s"Coudn't retrieve issue for key $key."
          }
      }

    Future.sequence(futureMessages)
      .map(_ mkString "\n")
      .map(compiledMessage ⇒ Api.send(message.copy(text = compiledMessage)))
  }
}
