package com.pk.filesystem.command

import com.pk.filesystem.State

case class Ls() extends Command {
  override def apply(state: State): State = state.setMessage(state.current.contents.map(_.friendlyName).mkString("\r\n"))
}
