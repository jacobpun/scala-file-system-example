package com.pk.filesystem.files

case class File(override val parentPath: String, override val name: String, contents: String) extends DirEntry(parentPath, name) {
  override def friendlyName: String = s"$name [File]"

  override def isDirectory: Boolean = false

  override def isFile: Boolean = true
}

object File {
  def empty(parentPath: String, name: String) = new File(parentPath, name, "")
}