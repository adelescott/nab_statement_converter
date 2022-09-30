import java.text.SimpleDateFormat
import java.util.Date

import kantan.csv.{RowDecoder, RowEncoder}

/** Class to represent a row of an Nab statement csv.
  */
case class NabStatementRow(
    date: Date,
    amount: BigDecimal,
    description: String
)

object NabStatementRow {

  // Unfortunately the header row is incorrect in the NAB files, othewise this would be a
  // better implementation.
  //
  // implicit val nabStatementRowDecoder: HeaderDecoder[NabStatementRow] =
  //   HeaderDecoder.decoder(
  //     "Date",
  //     "Amount",
  //     "Transaction Details"
  //   )((date: String, amount: BigDecimal, description: String) =>
  //     NabStatementRow(
  //       stringToDate(date),
  //       amount,
  //       description
  //     )
  //   )

  implicit val nabStatementRowDecoder: RowDecoder[NabStatementRow] =
    RowDecoder.ordered {
      (
          date: String,
          amount: BigDecimal,
          _: String, /* Account number */
          _: String, /* Transaction Type */
          description: String, /* Transaction Details */
          _: String, /* Balance */
          _: String, /* Category */
          _: String /* Merchant Name */
      ) =>
        NabStatementRow(
          stringToDate(date),
          amount,
          description
        )
    }

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
