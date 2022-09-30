import java.io.File

import kantan.csv._
import kantan.csv.ops._
import Utils._

object Main extends App {

  def readCsv[A: HeaderDecoder](filename: String): List[ReadResult[A]] = {
    val file = new File(filename)
    file.asCsvReader[A](rfc.withoutHeader).toList
  }

  val cliParser = CliParser(args.toIndexedSeq)

  val nabStatementRowsReadResults = readCsv[NabStatementRow](cliParser.nabStatementFilename())
  val nabStatementRowsReadResult = coalesceEithers(nabStatementRowsReadResults)

  nabStatementRowsReadResult match {
    case Right(nabStatementRows) =>
      val outputFile = new File(cliParser.outputDir() + "/converted_nab_statement.csv")
      try {
        outputFile.writeCsv[NabStatementRow](
          nabStatementRows,
          rfc.withHeader("Date", "Reference", "Payee", "Description", "Amount")
        )
      } catch {
        case err: Exception => println(s"Error(s) converting Nab statement: \n ${err.toString}\nNo output file written.")
      }
    case Left(error) => println(s"Error(s) converting Nab statement: + ${error.toString}\nNo output file written.")
  }
}
