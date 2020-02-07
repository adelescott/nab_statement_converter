import org.rogach.scallop.{ScallopConf, ScallopOption}

case class CliParser(arguments: Seq[String]) extends ScallopConf(arguments) {
  val nabStatementFilename: ScallopOption[String] = opt[String](
    required = true,
    short = 'n',
    descr = "Nab statement filename.")
  val outputDir: ScallopOption[String] = opt[String](
    short = 'o',
    descr = "Directory to write converted output csv to.",
    default = Some(".")
  )
  verify()
}
