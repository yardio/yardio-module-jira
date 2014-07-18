package io.yard.jira

import akka.actor.Props

import io.yard.core.Api

class JiraPlugin(application: play.api.Application) extends play.api.Plugin {
  override def onStart = {
    Api.registerModule(
      "jira",
      "JIRA",
      Some(JiraController),
      Some(Props[Jiractor])
    )
  }
}
