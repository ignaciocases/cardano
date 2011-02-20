package org.hnlab.cardano.processor

import sbt._
import sbt.processor.BasicProcessor
import org.lifty.engine._
import org.lifty.util.TemplateHelper._
import org.lifty.processor.LiftHelper._
import net.liftweb.common._

object CONSTANTS {
  val LIFTVERSION = "2.3-SNAPSHOT"
	val CAPPUCCINOVERSION = "0.9"
}

trait DefaultLiftTemplate extends Template with Create with Delete {
  lazy val defaultMainPackage = searchForMainPackage match {
    case Full(packageName) => Full(packageName)
    case Empty => Empty
    case Failure(msg, _, _) => Failure(msg)
  }
}

object SnippetTemplate extends DefaultLiftTemplate {

  def name = "snippet"

  def description = "Creates a Lift snippet and correspondant Cappuccino classes"

  def arguments = {
    object packageArgument extends PackageArgument("snippetpack") with Default with Value {
      value = searchForPackageInBoot("src/main/scala/bootstrap/liftweb/Boot.scala", Full(".snippet"))
    }
    Argument("snippetName") :: packageArgument :: Nil
  }

  def files = {
    val templatePath = "%s/snippet.ssp".format(GlobalConfiguration.rootResources)
    val snippetPath = "src/main/scala/${snippetpack}/${snippetName}.scala"
    TemplateFile(templatePath, snippetPath) :: Nil
  }
}

object CardanoBlankProject extends DefaultLiftTemplate {

  def name = "project-blank"

  def description = "Creates a blank project using Cappuccino " + CONSTANTS.CAPPUCCINOVERSION + " and Lift " + CONSTANTS.LIFTVERSION

  def arguments = pack :: cappuccinoversion :: liftversion :: Argument("applicationName") :: Nil

  def files = {
		val cappSharedPath = "%s/cappuccino/blank".format(GlobalConfiguration.rootResources)
    val blankProjectPath = "%s/project-blank".format(GlobalConfiguration.rootResources)
		
		CardanoHelper.copyFrameworks()
		CardanoHelper.copyResources()
		
		//CardanoHelper.prepareFrameworks()

    TemplateFile("%s/Project.ssp".format(blankProjectPath), "project/build/Project.scala") ::
      TemplateFile("%s/LiftConsole.scala".format(blankProjectPath), "src/test/scala/LiftConsole.scala") ::
      TemplateFile("%s/RunWebApp.scala".format(blankProjectPath), "src/test/scala/RunWebApp.scala") ::
      TemplateFile("%s/default.props".format(blankProjectPath), "src/main/resources/props/default.props") ::
      TemplateFile("%s/default.html".format(blankProjectPath), "src/main/webapp/templates-hidden/default.html") ::
      TemplateFile("%s/404.html".format(blankProjectPath), "src/main/webapp/404.html") ::
      TemplateFile("%s/wizard-all.html".format(blankProjectPath), "src/main/webapp/templates-hidden/wizard-all.html") ::
      TemplateFile("%s/web.xml".format(blankProjectPath), "src/main/webapp/WEB-INF/web.xml") ::
      TemplateFile("%s/index.ssp".format(blankProjectPath), "src/main/webapp/index.html") ::
      TemplateFile("%s/boot.ssp".format(blankProjectPath), "src/main/scala/bootstrap/liftweb/Boot.scala") ::
      TemplateFile("%s/AppTest.ssp".format(blankProjectPath), "src/test/scala/${mainpack}/AppTest.scala") ::
			TemplateFile("%s/capp_template.ssp".format(cappSharedPath), "src/main/webapp/templates-hidden/capp_template.html") ::
			TemplateFile("%s/capp.html".format(cappSharedPath), "src/main/webapp/capp.html") ::
			TemplateFile("%s/AppController.j".format(cappSharedPath), "src/main/webapp/AppController.j") ::
			TemplateFile("%s/main.j".format(cappSharedPath), "src/main/webapp/main.j") ::
			TemplateFile("%s/Info.plist".format(cappSharedPath), "src/main/webapp/Info.plist") ::
      Nil
  }

  override def postRenderAction(arguments: List[ArgumentResult]): Unit = {
    createFolderStructure(arguments)(org.lifty.processor.LiftHelper.liftFolderStructure: _*)
  }

	object liftversion extends Argument("liftversion") with Default with Value { value = Full(CONSTANTS.LIFTVERSION) }
	object cappuccinoversion extends Argument("cappuccinoversion") with Default with Value { value = Full(CONSTANTS.CAPPUCCINOVERSION) }
  object pack extends PackageArgument("mainpack") with Default with Value { value = defaultMainPackage }
  
}


object CardanoFrothyProject extends DefaultLiftTemplate {

  def name = "project-frothy"

  def description = "Creates an example project based on Frothy using Cappuccino " + CONSTANTS.CAPPUCCINOVERSION + " and Lift " + CONSTANTS.LIFTVERSION

  def arguments = pack :: cappuccinoversion :: liftversion :: Argument("applicationName") :: Nil

  def files = {
		val cappSharedPath = "%s/cappuccino/frothy".format(GlobalConfiguration.rootResources)
    val blankProjectPath = "%s/project-blank".format(GlobalConfiguration.rootResources)
		
		CardanoHelper.copyFrameworks()
		CardanoHelper.copyResources()

    TemplateFile("%s/Project.ssp".format(blankProjectPath), "project/build/Project.scala") ::
      TemplateFile("%s/LiftConsole.scala".format(blankProjectPath), "src/test/scala/LiftConsole.scala") ::
      TemplateFile("%s/RunWebApp.scala".format(blankProjectPath), "src/test/scala/RunWebApp.scala") ::
      TemplateFile("%s/default.props".format(blankProjectPath), "src/main/resources/props/default.props") ::
      TemplateFile("%s/default.html".format(blankProjectPath), "src/main/webapp/templates-hidden/default.html") ::
      TemplateFile("%s/404.html".format(blankProjectPath), "src/main/webapp/404.html") ::
      TemplateFile("%s/wizard-all.html".format(blankProjectPath), "src/main/webapp/templates-hidden/wizard-all.html") ::
      TemplateFile("%s/web.xml".format(blankProjectPath), "src/main/webapp/WEB-INF/web.xml") ::
      TemplateFile("%s/index.ssp".format(blankProjectPath), "src/main/webapp/index.html") ::
      TemplateFile("%s/boot.ssp".format(blankProjectPath), "src/main/scala/bootstrap/liftweb/Boot.scala") ::
      TemplateFile("%s/AppTest.ssp".format(blankProjectPath), "src/test/scala/${mainpack}/AppTest.scala") ::
			TemplateFile("%s/capp_template.ssp".format(cappSharedPath), "src/main/webapp/templates-hidden/capp_template.html") ::
			TemplateFile("%s/capp.html".format(cappSharedPath), "src/main/webapp/capp.html") ::
			TemplateFile("%s/CPAjaxCall.ssp".format(cappSharedPath), "src/main/scala/${mainpack}/snippet/CPAjaxCall.scala") ::
			TemplateFile("%s/CPClock.ssp".format(cappSharedPath), "src/main/scala/${mainpack}/comet/CPClock.scala") ::
			TemplateFile("%s/AppController.j".format(cappSharedPath), "src/main/webapp/AppController.j") ::
			TemplateFile("%s/main.j".format(cappSharedPath), "src/main/webapp/main.j") ::
			TemplateFile("%s/Info.plist".format(cappSharedPath), "src/main/webapp/Info.plist") ::
      Nil
  }

  override def postRenderAction(arguments: List[ArgumentResult]): Unit = {
    createFolderStructure(arguments)(org.lifty.processor.LiftHelper.liftFolderStructure: _*)
  }

	object liftversion extends Argument("liftversion") with Default with Value { value = Full(CONSTANTS.LIFTVERSION) }
	object cappuccinoversion extends Argument("cappuccinoversion") with Default with Value { value = Full(CONSTANTS.CAPPUCCINOVERSION) }
  object pack extends PackageArgument("mainpack") with Default with Value { value = defaultMainPackage }
  
}




object CPLogger extends ConsoleLogger {

}

object CardanoHelper {
	def copyFrameworks() = copy("Frameworks")
	def copyResources() = copy("Resources")

	def prepareFrameworks() = prepare()

	private def copy(resource: String): Unit = {
		val frameworkSourcePath: Path = sbt.Path.fromFile("/Development/hn/client/Quadra/" + resource)
		val frameworkDestinationPath: Path = sbt.Path.fromFile(("src/main/webapp/" + resource).format(GlobalConfiguration.rootResources))
		try {
			sbt.FileUtilities.copyDirectory(frameworkSourcePath, frameworkDestinationPath, CPLogger)
		} catch {
			case e:java.lang.IllegalArgumentException =>
		}
		
	}
	
	private def prepare(): Unit = {
		val frameworkLibPath: Path = sbt.Path.fromFile("src/lib_managed/scala_2.8.1/compile/cappuccino_2.8.1-0.9.jar")
		val frameworkDestinationPath: Path = sbt.Path.fromFile(("src/main/webapp/").format(GlobalConfiguration.rootResources))
		try {
			sbt.FileUtilities.unzip(frameworkLibPath, frameworkDestinationPath, CPLogger)
		} catch {
			case e: java.lang.IllegalArgumentException =>
		}
	}

}
