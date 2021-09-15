package com.pk.filesystem

import com.pk.filesystem.command.Command
import com.pk.filesystem.files.Directory

import java.util.Scanner

object FileSystem extends App {
  val scanner = new Scanner(System.in)
  val root = Directory.ROOT
  var state: State = State(root, root)
  while (true) {
    state.show()
    val input = scanner.nextLine();
    state = Command.from(input).apply(state)
  }
}
