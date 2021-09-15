package com.pk.filesystem.command
import com.pk.filesystem.files.{DirEntry, Directory}

case class MkDir(dirName: String) extends CreateEntry(dirName) {
  override def createEntry(parentPath: String): DirEntry = Directory.empty(parentPath, dirName)
}
