import _root_.io.gatling.core.scenario.Simulation
import ch.qos.logback.classic.{Level, LoggerContext}
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import org.slf4j.LoggerFactory

import scala.concurrent.duration._

/**
 * Performance test for the Frontpageconfig entity.
 */
class FrontpageconfigGatlingTest extends Simulation {

    val context: LoggerContext = LoggerFactory.getILoggerFactory.asInstanceOf[LoggerContext]
    // Log all HTTP requests
    //context.getLogger("io.gatling.http").setLevel(Level.valueOf("TRACE"))
    // Log failed HTTP requests
    //context.getLogger("io.gatling.http").setLevel(Level.valueOf("DEBUG"))

    val baseURL = Option(System.getProperty("baseURL")) getOrElse """http://localhost:8080"""

    val httpConf = http
        .baseUrl(baseURL)
        .inferHtmlResources()
        .acceptHeader("*/*")
        .acceptEncodingHeader("gzip, deflate")
        .acceptLanguageHeader("fr,fr-fr;q=0.8,en-us;q=0.5,en;q=0.3")
        .connectionHeader("keep-alive")
        .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.10; rv:33.0) Gecko/20100101 Firefox/33.0")
        .silentResources // Silence all resources like css or css so they don't clutter the results

    val headers_http = Map(
        "Accept" -> """application/json"""
    )

    val headers_http_authentication = Map(
        "Content-Type" -> """application/json""",
        "Accept" -> """application/json"""
    )

    val headers_http_authenticated = Map(
        "Accept" -> """application/json""",
        "Authorization" -> "${access_token}"
    )

    val scn = scenario("Test the Frontpageconfig entity")
        .exec(http("First unauthenticated request")
        .get("/api/account")
        .headers(headers_http)
        .check(status.is(401))
        ).exitHereIfFailed
        .pause(10)
        .exec(http("Authentication")
        .post("/api/authenticate")
        .headers(headers_http_authentication)
        .body(StringBody("""{"username":"admin", "password":"admin"}""")).asJson
        .check(header("Authorization").saveAs("access_token"))).exitHereIfFailed
        .pause(2)
        .exec(http("Authenticated request")
        .get("/api/account")
        .headers(headers_http_authenticated)
        .check(status.is(200)))
        .pause(10)
        .repeat(2) {
            exec(http("Get all frontpageconfigs")
            .get("/api/frontpageconfigs")
            .headers(headers_http_authenticated)
            .check(status.is(200)))
            .pause(10 seconds, 20 seconds)
            .exec(http("Create new frontpageconfig")
            .post("/api/frontpageconfigs")
            .headers(headers_http_authenticated)
            .body(StringBody("""{
                "creationDate":"2020-01-01T00:00:00.000Z"
                , "topNews1":"0"
                , "topNews2":"0"
                , "topNews3":"0"
                , "topNews4":"0"
                , "topNews5":"0"
                , "latestNews1":"0"
                , "latestNews2":"0"
                , "latestNews3":"0"
                , "latestNews4":"0"
                , "latestNews5":"0"
                , "breakingNews1":"0"
                , "recentPosts1":"0"
                , "recentPosts2":"0"
                , "recentPosts3":"0"
                , "recentPosts4":"0"
                , "featuredArticles1":"0"
                , "featuredArticles2":"0"
                , "featuredArticles3":"0"
                , "featuredArticles4":"0"
                , "featuredArticles5":"0"
                , "featuredArticles6":"0"
                , "featuredArticles7":"0"
                , "featuredArticles8":"0"
                , "featuredArticles9":"0"
                , "featuredArticles10":"0"
                , "popularNews1":"0"
                , "popularNews2":"0"
                , "popularNews3":"0"
                , "popularNews4":"0"
                , "popularNews5":"0"
                , "popularNews6":"0"
                , "popularNews7":"0"
                , "popularNews8":"0"
                , "weeklyNews1":"0"
                , "weeklyNews2":"0"
                , "weeklyNews3":"0"
                , "weeklyNews4":"0"
                , "newsFeeds1":"0"
                , "newsFeeds2":"0"
                , "newsFeeds3":"0"
                , "newsFeeds4":"0"
                , "newsFeeds5":"0"
                , "newsFeeds6":"0"
                , "usefulLinks1":"0"
                , "usefulLinks2":"0"
                , "usefulLinks3":"0"
                , "usefulLinks4":"0"
                , "usefulLinks5":"0"
                , "usefulLinks6":"0"
                , "recentVideos1":"0"
                , "recentVideos2":"0"
                , "recentVideos3":"0"
                , "recentVideos4":"0"
                , "recentVideos5":"0"
                , "recentVideos6":"0"
                }""")).asJson
            .check(status.is(201))
            .check(headerRegex("Location", "(.*)").saveAs("new_frontpageconfig_url"))).exitHereIfFailed
            .pause(10)
            .repeat(5) {
                exec(http("Get created frontpageconfig")
                .get("${new_frontpageconfig_url}")
                .headers(headers_http_authenticated))
                .pause(10)
            }
            .exec(http("Delete created frontpageconfig")
            .delete("${new_frontpageconfig_url}")
            .headers(headers_http_authenticated))
            .pause(10)
        }

    val users = scenario("Users").exec(scn)

    setUp(
        users.inject(rampUsers(Integer.getInteger("users", 100)) during (Integer.getInteger("ramp", 1) minutes))
    ).protocols(httpConf)
}
