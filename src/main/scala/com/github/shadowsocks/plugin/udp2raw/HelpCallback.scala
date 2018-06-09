package com.github.shadowsocks.plugin.udp2raw

import com.github.shadowsocks.plugin.PluginOptions

import scala.io.Source

/**
  * @author Mygod
  */
class HelpCallback extends com.github.shadowsocks.plugin.HelpCallback {
  override def produceHelpMessage(options: PluginOptions): CharSequence = {
    val process = new ProcessBuilder(getApplicationInfo.nativeLibraryDir + "/libudp2raw.so", "--help")
      .redirectErrorStream(true)
      .start()
    process.waitFor()
    Source.fromInputStream(process.getInputStream())
      .getLines()
      .dropWhile(line => line != "GLOBAL OPTIONS:")
      .drop(1)
      .takeWhile(line => line.length() > 3)
      .filter(line =>
        !line.startsWith("   -l ") &&
        !line.startsWith("   -r ") &&
        !line.startsWith("   -c ") &&
        !line.startsWith("   --help,"))
      .mkString("\n")
      .replaceAll(" {2,}", "\n")
      .replaceAll("--", "")
      .replaceAll(" value", "=value;")
      .substring(1) // remove 1st \n
  }
}
