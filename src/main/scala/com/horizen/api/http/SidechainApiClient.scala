package com.horizen.api.http

import com.horizen.{SidechainSettings, SidechainSettingsReader}
import scorex.core.api.client.ApiClient

import scala.io.StdIn


object SidechainApiClient extends App {
  private val settingsFilename = args.headOption.getOrElse("src/main/resources/settings.conf")
  private val sidechainSettings = SidechainSettingsReader.read(settingsFilename, None)
  private val sidechainApiClient = new ApiClient(sidechainSettings.scorexSettings.restApi)

  println("Welcome to the Sidechain node command-line client...")
  Iterator.continually(StdIn.readLine()).takeWhile(!_.equals("quit")).foreach { command =>
    println(s"[$command RESULT] " + sidechainApiClient.executeCommand(command + " "))
  }

}
