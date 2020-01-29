import java.text.SimpleDateFormat
import java.util.Date

import kantan.csv.{RowDecoder, RowEncoder}

/**
 * Class to represent a row of an Nab statement csv.
 */
case class NabStatementRow(
  date: Date,
  amount: BigDecimal,
  description: String
)

object NabStatementRow {
  implicit val nabStatementRowDecoder: RowDecoder[NabStatementRow] = RowDecoder.ordered { (
    date: String,
    amount: BigDecimal,
    _: String, /* Reference */
    _: String, /* ??? */
    _: String, /* TransactionType */
    description: String,
    _: String /* Balance */
  ) => {
    NabStatementRow(
      stringToDate(date),
      amount,
      description
    )
  }}

  implicit val nabStatementRowEncoder: RowEncoder[NabStatementRow] = RowEncoder.ordered {
    nabStatementRow: NabStatementRow =>
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