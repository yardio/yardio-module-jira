package io.yard.module.jira

import akka.actor.Props
import akka.actor.ActorSystem
import play.api.libs.concurrent.Akka

import io.yard.common.models.Module

object JiraModule {

  lazy val value: Module = {
    import play.api.Play.current
    val name = "jira"
    val actor = Akka.system.actorOf(Props[Jiractor], name)
    Module(
      name,
      description = "The JIRA module.",
      controller = Some(JiraController),
      actor = Option(actor)
    )
  }

}
