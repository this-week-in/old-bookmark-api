package ttd.editor.api

data class DeleteCommand(val href: String)

interface ReadCommand {
  val `when`: String
}

data class UpdateCommand(val href: String, val tags: Array<String>, val description: String, override val `when`: String) : ReadCommand

data class NextCommand(override val `when`: String) : ReadCommand

