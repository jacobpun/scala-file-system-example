package com.pk.filesystem.files

import scala.util.Try


class Directory(override val parentPath: String, override val name: String, val contents: List[DirEntry]) extends DirEntry(parentPath, name) {
  def getAllDirectoriesInPath: List[String] = fullPath.split(Directory.SEPARATOR).toList.filter(_.nonEmpty)

  def hasEntry(entryName: String): Boolean = contents.exists(_.name.equals(entryName))

  def getSubDir(name: String): Option[Directory] =
    contents.find(e => e.name.equals(name) && e.isDirectory).map(dir => dir.asInstanceOf[Directory])

  def navigateTo(paths: List[String]): Option[Directory] =
    if (paths.isEmpty) Option.apply(this)
    else getSubDir(paths.head).flatMap(dir => dir.navigateTo(paths.tail))

  def addEntry(entry: DirEntry): Directory = Directory.withContents(parentPath, name, contents :+ entry)

  def replaceEntry(entry: Option[DirEntry]): Option[Directory] = entry.map(e => Directory.withContents(parentPath, name, contents.filter(!_.name.equals(e.name)) :+ e))

  override def friendlyName: String = s"$name [Directory]"

  override def isDirectory: Boolean = true

  override def isFile: Boolean = false
}

object Directory {
  val SEPARATOR = "/"
  val ROOT_PATH = "/"

  def ROOT: Directory = empty("", "")

  def empty(patentPath: String, name: String): Directory = withContents(patentPath, name, List())

  def withContents(parentPath: String, name: String, contents: List[DirEntry]) = new Directory(parentPath, name, contents)
}