package com.pk.filesystem

import com.pk.filesystem.files.Directory

class State(val root: Directory, val current: Directory, val output: String) {
  def show(): Unit = {
    println(output)
    print(State.SHELL_TOKEN)
  }
  def setMessage(m: String): State = State(this.root, this.current, m)
}

object State {
  val SHELL_TOKEN = "$ "

  def apply(root: Directory, current: Directory, output: String = ""): State = new State(root, current, output)
}
