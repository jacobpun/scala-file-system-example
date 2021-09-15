package com.pk.filesystem.command
import com.pk.filesystem.State

case class Pwd() extends Command {
  override def apply(state: State): State = state.setMessage(state.current.fullPath)
}
