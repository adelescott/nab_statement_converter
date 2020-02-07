import org.rogach.scallop.{ScallopConf, ScallopOption}

case class CliParser(arguments: Seq[String]) extends ScallopConf(arguments) {
  val nabStatementFilename: ScallopOption[String] = opt[String](
    required = true,
    short = 'n',
    descr = "Nab statement filename.")
  val outputFilename: ScallopOption[String] = opt[String](
    short = 'o',
    descr = "Converted output csv filename.",
    default = Some("converted_nab_statement.csv")
  )
  verify()
}
