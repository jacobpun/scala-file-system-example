package com.pk.filesystem.files

abstract class DirEntry(val parentPath: String, val name: String) {
  def fullPath: String = {
    def separatorIfNecessary: String = if (Directory.ROOT_PATH.equals(parentPath)) "" else Directory.SEPARATOR

    parentPath + separatorIfNecessary + name
  }

  def friendlyName: String

  def isDirectory: Boolean

  def isFile: Boolean
}
