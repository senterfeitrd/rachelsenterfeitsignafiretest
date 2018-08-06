/**
  * Created by rachel on 8/5/18.
  */

import org.scalatest.FlatSpec

import scala.io.Source

class MainSpec extends FlatSpec with HelperFunctions{

  val logLines = getLogLines("src/test/resources/logtest.csv")
  val date = "2017-10-23T12:00:00.000"

  "Loglines" should " not be null" in {
    assert(logLines.nonEmpty)
    assert(logLines.size == 18)
  }

  "Occurrences of a timestamp" should "be the right number" in {
    val occurrences = findOccurrences(date, logLines)
    assert(occurrences == 6)
  }

  "Timestamp with highest number of connections" should "be the right number" in {
    val mostConnections = highestNumberConnections(logLines)
    assert(mostConnections == "2017-10-23T12:00:00.000")
  }

}
