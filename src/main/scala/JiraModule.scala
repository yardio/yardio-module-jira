package io.yard.module.jira

import akka.actor.Props

import io.yard.common.models.Module

object JiraModule {
  lazy val value = Module("jira", "JIRA", Some(JiraController), Some(Props[Jiractor]))
}
