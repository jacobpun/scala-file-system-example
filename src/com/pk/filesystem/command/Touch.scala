package com.pk.filesystem.command
import com.pk.filesystem.files.{DirEntry, File}

case class Touch(fileName: String) extends CreateEntry(fileName) {
  override def createEntry(parentPath: String): DirEntry = File.empty(parentPath, fileName)
}
