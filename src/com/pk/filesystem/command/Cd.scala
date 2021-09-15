package com.pk.filesystem.command

import com.pk.filesystem.State
import com.pk.filesystem.files.Directory

import scala.annotation.tailrec

case class Cd(path: String) extends Command {
  override def apply(state: State): State = {
    @tailrec
    def collapseRelativeTokens(tokens: Seq[String], acc: List[String] = List()): Option[List[String]] = {
      if (tokens.isEmpty) Option.apply(acc)
      else if (tokens.head.isEmpty) collapseRelativeTokens(tokens.tail, acc)
      else if (tokens.head.equals(".")) collapseRelativeTokens(tokens.tail, acc)
      else if (tokens.head.equals("..")) if (acc.isEmpty) Option.empty else collapseRelativeTokens(tokens.tail, acc.init)
      else collapseRelativeTokens(tokens.tail, acc :+ tokens.head)
    }

    val absPath = if (isAbsolutePath(path)) path else state.current.fullPath + Directory.SEPARATOR + path
    collapseRelativeTokens(absPath.split(Directory.SEPARATOR))
      .flatMap(list => state.root.navigateTo(list))
      .map(dir => State(state.root, dir))
      .getOrElse(state.setMessage(s"Path $path not found"))
  }

  private def isAbsolutePath(path: String): Boolean = path.startsWith(Directory.SEPARATOR)
}
