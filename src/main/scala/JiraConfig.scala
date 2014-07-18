package io.yard.jira

trait JiraConfig extends io.yard.core.utils.Config {
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
}
