package com.pk.filesystem.command

import com.pk.filesystem.State
import com.pk.filesystem.files.{DirEntry, Directory}

abstract class CreateEntry(name: String) extends Command {
  def isInvalidName(dirName: String): Boolean = dirName.contains(Directory.SEPARATOR) || dirName.startsWith(".")

  def createEntry(name: String, state: State): State = {

    def updateStructure(currentDir: Directory, paths: List[String], entryToAdd: DirEntry): Option[Directory] =
      if (paths.isEmpty) Option.apply(currentDir.addEntry(entryToAdd))
      else currentDir.getSubDir(paths.head).flatMap(sd => currentDir.replaceEntry(updateStructure(sd, paths.tail, entryToAdd)))

    val wd = state.current;
    val newDir: DirEntry = createEntry(state.current.fullPath)
    val allDirectoriesInPath = wd.getAllDirectoriesInPath
    val newRoot: Option[Directory] = updateStructure(state.root, allDirectoriesInPath, newDir)

    newRoot.flatMap(nr => nr.navigateTo(allDirectoriesInPath).map(d => State(nr, d, s"$name created in ${wd.fullPath}")))
     .getOrElse(state.setMessage("Unexpected error!"))
  }

  override def apply(state: State): State = {
    val wd = state.current;
    if (wd.hasEntry(name)) state.setMessage(s"Directory or file named $name already exists in ${wd.fullPath}")
    else if (isInvalidName(name)) state.setMessage(s"name $name is invalid")
    else createEntry(name, state)
  }

  def createEntry(parentPath: String): DirEntry
}
