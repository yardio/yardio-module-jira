package io.yard.module.jira

import io.yard.module.core.Api

class JiraPlugin(application: play.api.Application) extends play.api.Plugin {
  override def onStart = {
    Api.registerModule(JiraModule.value)
  }
}
