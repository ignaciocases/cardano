package org.hnlab.cardano.processor

import sbt._
import sbt.processor.BasicProcessor
import org.lifty.engine._
import org.lifty.util.TemplateHelper._
import org.lifty.processor.LiftHelper._
import net.liftweb.common._

object CONSTANTS {
  val LIFTVERSION = "2.3"
	val CAPPUCCINOVERSION = "0.9"
	val CAPPUCCINOBUILD = "CAPP_BUILD"
	val CAPPUCCINOAPP = "CardanoApp"
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

/**
 * Template for a model class implemented using Mapper. It provides basic functionality
 * that would expect from a User.
 */
object UserTemplate extends DefaultLiftTemplate {

  def name = "user"

  def description = "Template for a model class implemented using Mapper. It provides basic functionality that would expect from a User"

  def arguments = pack :: Nil

  val path = "%s/user".format(GlobalConfiguration.rootResources)

  def files = TemplateFile(
    "%s/user.ssp".format(path),
    "src/main/scala/${modelpack}/User.scala") :: Nil

  injectContentsOfFile("%s/boot_import_injections_user.ssp".format(path)) into ("boot.ssp") at ("imports")
  injectContentsOfFile("%s/boot_sitemap_injections_user.txt".format(path)) into ("boot.ssp") at ("sitemap")
  injectContentsOfFile("%s/boot_bottom_injections_user.txt".format(path)) into ("boot.ssp") at ("bottom")
  injectContentsOfFile("%s/boot_top_injections_user.txt".format(path)) into ("boot.ssp") at ("top")
  injectContentsOfFile("%s/ProjectDefinition_dependencies_injections_user.txt".format(path)) into ("Project.ssp") at ("dependencies")

  object pack extends PackageArgument("modelpack") with Default with Value {
    value = searchForPackageInBoot("src/main/scala/bootstrap/liftweb/Boot.scala", Full(".model"))
  }
}

object CardanoBlankProject extends DefaultLiftTemplate {

  def name = "project-blank"

  def description = "Creates a blank project using Cappuccino " + CONSTANTS.CAPPUCCINOVERSION + " and Lift " + CONSTANTS.LIFTVERSION

  def arguments = pack :: cappuccinoversion :: cappuccinobuild :: liftversion :: cappuccinoapp :: Nil

  def files = {
		val cappSharedPath = "%s/cappuccino/blank".format(GlobalConfiguration.rootResources)
    val blankProjectPath = "%s/project-blank".format(GlobalConfiguration.rootResources)
		
		CardanoHelper.copyFrameworks()
		//CardanoHelper.copyResources()
		
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
	  TemplateFile("%s/favicon.ico".format(cappSharedPath), "src/main/webapp/favicon.ico") ::
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
	object cappuccinobuild extends Argument("cappuccinobuild") with Default with Value { value = Full(CONSTANTS.CAPPUCCINOBUILD) }
	object cappuccinoapp extends Argument("cappuccinoapp") with Default with Value { value = Full(CONSTANTS.CAPPUCCINOAPP) }
  object pack extends PackageArgument("mainpack") with Default with Value { value = defaultMainPackage }
  
}

object CardanoFrothyProject extends DefaultLiftTemplate {

  def name = "project-frothy"

  def description = "Creates an example project based on Frothy using Cappuccino " + CONSTANTS.CAPPUCCINOVERSION + " and Lift " + CONSTANTS.LIFTVERSION

  def arguments = pack :: cappuccinoversion :: cappuccinobuild :: liftversion :: cappuccinoapp :: Nil

  def files = {
		val cappSharedPath = "%s/cappuccino/frothy".format(GlobalConfiguration.rootResources)
    val blankProjectPath = "%s/project-blank".format(GlobalConfiguration.rootResources)
		
		CardanoHelper.copyFrameworks()
		//CardanoHelper.copyResources()

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
	  TemplateFile("%s/favicon.ico".format(cappSharedPath), "src/main/webapp/favicon.ico") ::
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
	object cappuccinobuild extends Argument("cappuccinobuild") with Default with Value { value = Full(CONSTANTS.CAPPUCCINOBUILD) }
	object cappuccinoapp extends Argument("cappuccinoapp") with Default with Value { value = Full(CONSTANTS.CAPPUCCINOAPP) }
  object pack extends PackageArgument("mainpack") with Default with Value { value = defaultMainPackage }
  
}


object CardanoXibProject extends DefaultLiftTemplate {

  def name = "project-xib"

  def description = "Creates a Xib project using Cappuccino " + CONSTANTS.CAPPUCCINOVERSION + " and Lift " + CONSTANTS.LIFTVERSION

  def arguments = pack :: cappuccinoversion :: cappuccinobuild :: liftversion :: cappuccinoapp :: Nil

  def files = {
		val cappSharedPath = "%s/cappuccino/xib".format(GlobalConfiguration.rootResources)
    val blankProjectPath = "%s/project-blank".format(GlobalConfiguration.rootResources)
		
	CardanoHelper.copyFrameworks()

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
	TemplateFile("%s/favicon.ico".format(cappSharedPath), "src/main/webapp/favicon.ico") ::
	TemplateFile("%s/AppController.ssp".format(cappSharedPath), "src/main/webapp/AppController.j") ::
	TemplateFile("%s/capp.ssp".format(cappSharedPath), "src/main/webapp/capp.html") ::
	TemplateFile("%s/capp_debug.ssp".format(cappSharedPath), "src/main/webapp/capp_debug.html") ::
	TemplateFile("%s/capp_debug_template.ssp".format(cappSharedPath), "src/main/webapp/templates-hidden/capp_debug_template.html") ::
	TemplateFile("%s/capp_template.ssp".format(cappSharedPath), "src/main/webapp/templates-hidden/capp_template.html") ::
	TemplateFile("%s/Info.ssp".format(cappSharedPath), "src/main/webapp/Info.plist") ::
	TemplateFile("%s/Jakefile.ssp".format(cappSharedPath), "src/main/webapp/Jakefile") ::
	TemplateFile("%s/main.ssp".format(cappSharedPath), "src/main/webapp/main.j") ::
	TemplateFile("%s/Resources/MainMenu.xib".format(cappSharedPath), "src/main/webapp/Resources/MainMenu.xib") ::
	TemplateFile("%s/Resources/spinner.gif".format(cappSharedPath), "src/main/webapp/Resources/spinner.gif") ::
    Nil
  }

  override def postRenderAction(arguments: List[ArgumentResult]): Unit = {
    createFolderStructure(arguments)(org.lifty.processor.LiftHelper.liftFolderStructure: _*)
  }

	object liftversion extends Argument("liftversion") with Default with Value { value = Full(CONSTANTS.LIFTVERSION) }
	object cappuccinoversion extends Argument("cappuccinoversion") with Default with Value { value = Full(CONSTANTS.CAPPUCCINOVERSION) }
	object cappuccinobuild extends Argument("cappuccinobuild") with Default with Value { value = Full(CONSTANTS.CAPPUCCINOBUILD) }
	object cappuccinoapp extends Argument("cappuccinoapp") with Default with Value { value = Full(CONSTANTS.CAPPUCCINOAPP) }
  object pack extends PackageArgument("mainpack") with Default with Value { value = defaultMainPackage }
  
}

object CardanoProject extends DefaultLiftTemplate {

  override def dependencies = CardanoXibProject :: UserTemplate :: Nil

  def name = "project"

  def description = "Creates a Xib-project using Cappuccino " + CONSTANTS.CAPPUCCINOVERSION + " and Lift " + CONSTANTS.LIFTVERSION + " with user functionality"

  def arguments = mainPackage :: Nil

  override def postRenderAction(arguments: List[ArgumentResult]): Unit = {
    createFolderStructure(arguments)(org.lifty.processor.LiftHelper.liftFolderStructure: _*)
  }

  val path = "%s/basic-cardano-project".format(GlobalConfiguration.rootResources)

  def files = {
    TemplateFile("%s/index-static.html".format(path), "src/main/webapp/static/index.html") ::
      TemplateFile("%s/helloworld.ssp".format(path), "src/main/scala/${mainpack}/snippet/HelloWorld.scala") ::
      TemplateFile("%s/HelloWorldTest.ssp".format(path), "src/test/scala/${mainpack}/snippet/HelloWorldTest.scala") ::
      Nil
  }

  injectContentsOfFile("%s/boot_import_injections.ssp".format(path)) into ("boot.ssp") at ("imports")
  injectContentsOfFile("%s/boot_bottom_injections.ssp".format(path)) into ("boot.ssp") at ("bottom")
  injectContentsOfFile("%s/boot_sitemap_injections.ssp".format(path)) into ("boot.ssp") at ("sitemap")
  injectContentsOfFile("%s/index_content_injections.ssp".format(path)) into ("index.ssp") at ("content")
  injectContentsOfFile("%s/ProjectDefinition_dependencies_injections.ssp".format(path)) into ("Project.ssp") at ("dependencies")

  object mainPackage extends PackageArgument("mainpack") with Default with Value { value = defaultMainPackage }

}


object CPLogger extends ConsoleLogger {

}

object CardanoHelper {
	
	def copyFrameworks() = {
		val originRelease = System.getenv(CONSTANTS.CAPPUCCINOBUILD) + "/Release"
		val destinationRelease = "src/main/webapp/Frameworks"

		val originDebug = System.getenv(CONSTANTS.CAPPUCCINOBUILD) + "/Debug"
		val destinationDebug = "src/main/webapp/Frameworks/Debug"		
		
		copy(originRelease, destinationRelease)
		copy(originDebug, destinationDebug)
	}
	//def copyResources() = copy("Resources")

	def prepareFrameworks() = prepare()

	
	private def copy(resource: String, destination: String): Unit = {
		val frameworkSourcePath: Path = sbt.Path.fromFile(resource)
		val frameworkDestinationPath: Path = sbt.Path.fromFile((destination).format(GlobalConfiguration.rootResources))
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
