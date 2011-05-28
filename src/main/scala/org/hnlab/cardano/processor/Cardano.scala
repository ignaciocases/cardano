package org.hnlab.cardano.processor

import sbt._
import org.lifty.engine._

/**
 * This is the Cardano processor.
 * It mimics lifty, and so many code
 * has been borrowed from Mads Hartmann project 
 */

class Cardano extends SBTTemplateProcessor {
  def templates = CardanoBlankProject :: CardanoFrothyProject :: CardanoXibProject:: Nil
}
