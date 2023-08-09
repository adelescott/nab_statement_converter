import java.text.SimpleDateFormat
import java.util.Date

import kantan.csv.{HeaderDecoder, RowEncoder}

/** Class to represent a row of an Nab statement csv.
  */
case class NabStatementRow(
    date: Date,
    amount: BigDecimal,
    description: String
)

object NabStatementRow {

  implicit val nabStatementRowDecoder: HeaderDecoder[NabStatementRow] =
    HeaderDecoder.decoder(
      "Date",
      "Amount",
      "Transaction Details"
    )((date: String, amount: BigDecimal, description: String) =>
      NabStatementRow(
        stringToDate(date),
        amount,
        description
      )
    )

  implicit val nabStatementRowEncoder: RowEncoder[NabStatementRow] =
    RowEncoder.ordered { nabStatementRow: NabStatementRow =>
      (
        dateToString(nabStatementRow.date),
        "",
        "",
        nabStatementRow.description,
        nabStatementRow.amount
      )
    }

  def stringToDate(str: String): Date = {
    val format = new SimpleDateFormat("dd MMM yy")
    format.parse(str)
  }

  def dateToString(date: Date): String = {
    val format = new SimpleDateFormat("dd/MM/yyyy")
    format.format(date)
  }
}
