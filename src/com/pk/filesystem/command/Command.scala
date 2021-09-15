package com.pk.filesystem.command

import com.pk.filesystem.State

trait Command {
  def apply(state: State): State
}

object Command {
  def incompleteCommand(cmd: String): Command = state => state.setMessage(s"$cmd: incomplete command")

  def emptyCommand(): Command = state => state

  def from(input: String): Command = {
    if (input.trim.isEmpty) emptyCommand()
    val tokens = input.split(" ")
    tokens.head match {
      case mkdir@"mkdir" => if (tokens.tail.isEmpty) incompleteCommand(mkdir) else MkDir(tokens(1))
      case touch@"touch" => if (tokens.tail.isEmpty) incompleteCommand(touch) else Touch(tokens(1))
      case "ls" => Ls()
      case "pwd" => Pwd()
      case cd@"cd" => if (tokens.tail.isEmpty) incompleteCommand(cd) else Cd(tokens(1))
      case something => UnknownCommand(something)
    }
  }
}