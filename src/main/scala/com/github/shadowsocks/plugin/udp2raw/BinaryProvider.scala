package com.github.shadowsocks.plugin.udp2raw

import java.io.{File, FileNotFoundException}

import android.net.Uri
import android.os.ParcelFileDescriptor
import com.github.shadowsocks.plugin.{NativePluginProvider, PathProvider}

/**
  * @author Mygod
  */
final class BinaryProvider extends NativePluginProvider {
  override protected def populateFiles(provider: PathProvider): Unit = provider.addPath("udp2raw", "755")

  override def getExecutable: String = getContext.getApplicationInfo.nativeLibraryDir + "/libudp2raw.so"

  override def openFile(uri: Uri): ParcelFileDescriptor = uri.getPath match {
    case "/udp2raw" => ParcelFileDescriptor.open(new File(getExecutable), ParcelFileDescriptor.MODE_READ_ONLY)
    case _ => throw new FileNotFoundException()
  }
}
