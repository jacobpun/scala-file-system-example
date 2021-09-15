package com.pk.filesystem.command

import com.pk.filesystem.State

case class UnknownCommand(cmd: String) extends Command {
  override def apply(state: State): State = state.setMessage(s"Command $cmd not found")
}
