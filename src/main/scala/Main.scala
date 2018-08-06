import java.text.SimpleDateFormat

import scala.collection.mutable.ListBuffer
import scala.io.Source

/**
  * Created by rachel on 8/4/18.
  */
object Main extends App with HelperFunctions {

  if (args.length == 0){
    usage
  }
  else {
    val date = args(0)
    val file = args (1)

    val sdf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS")
    val matchDate = sdf.format(sdf.parse(date replace(" ", "T")))

    val logLines = getLogLines(file)

    val occurrences = findOccurrences(matchDate, logLines)

    println("Occurrences of timestamp " + date + " : " + occurrences)

    val timeStampWithHighestConnections = highestNumberConnections(logLines)

    println("Timestamp with highest num connections: " + timeStampWithHighestConnections)
  }

}

trait HelperFunctions {
  def usage = {
    println("Please provide two string arguments: a timestamp, and a file.")
    println("Example: \"2017-10-23 12:00:00.000\" /Users/rachel/Documents/sample.csv")
  }

  def getLogLines(file: String): ListBuffer[LogLine] = {

    val sdf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS")
    var logLines: ListBuffer[LogLine] = new ListBuffer[LogLine]()

    for (line <- Source.fromFile(file).getLines.drop(1)) {
      val pieces = line split ","
      val formattedTimeStamp = sdf.format(sdf.parse(pieces(1)))
      val logLine: LogLine = LogLine(ip = pieces(0), timeStamp = formattedTimeStamp, timeTaken = pieces(2))

      logLines += logLine
    }

    logLines
  }

  def findOccurrences(matchDate: String, logLines: ListBuffer[LogLine]) = {
    var occurrences = 0
    for(line <- logLines){
      if(matchDate == line.timeStamp) occurrences += 1
    }
    occurrences
  }

  def highestNumberConnections(logLines: ListBuffer[LogLine]): String = {
    logLines groupBy(_.timeStamp) maxBy(_._2.size) _1
  }
}

case class LogLine(ip: String, timeStamp: String, timeTaken: String)