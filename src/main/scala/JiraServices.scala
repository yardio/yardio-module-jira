package io.yard.module.jira

import scala.concurrent.Future
import play.api.libs.concurrent.Execution.Implicits._

import play.api.libs.json._
import play.api.libs.functional.syntax._
import play.api.libs.ws.WS
import play.api.Play.current

import io.yard.common.utils.Numbers

object JiraServices {
  lazy val apiUrl = "/rest/api/2"
  lazy val apiUrlIssues = apiUrl + "/issue"

  def issueUrl(key: String)(implicit config: JiraConfig) = config.url + "/browse/" + key

  private def authToken(implicit config: JiraConfig) =
    "Basic " + (config.authBasic getOrElse "")

  private def url(key: String)(implicit config: JiraConfig) =
    WS.url(s"${config.url}/rest/api/2/issue/$key")

  private def request(key: String)(implicit config: JiraConfig) =
    if (config.authBasic.isDefined)
      url(key).withHeaders("Authorization" → authToken)
    else
      url(key)

  def get(key: String)(implicit config: JiraConfig): Future[Option[JiraIssue]] =
    request(key).get.map { _.json.asOpt[JiraIssue] }

  /*def create(summary: String, description: String, project: String, issueType: String): Future[JsObject] = {
    val projectLabel = if (Numbers.isAllDigits(project)) { "id" } else { "key" }
    val issueTypeLabel = if (Numbers.isAllDigits(issueType)) { "id" } else { "name" }

    val newIssue = Json.obj(
      "fields" -> Json.obj(
        "project" -> Json.obj(projectLabel -> project),
        "summary" -> summary,
        "description" -> description,
        "issuetype" -> Json.obj(issueTypeLabel -> issueType)
      )
    )

    apiIssues.post(newIssue).map(_.json.as[JsObject])
  }*/
}
