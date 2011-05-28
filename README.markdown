Introduction
============
Cardano tries to make easy the initial steps of development of rich internet applications using [Cappuccino](http://cappuccino.org)/Objective-J in the client side and [Lift](http://liftweb.net)/[Scala](http://scala-lang.org/) in the server. It generates source code based on several templates.

Cardano is a [SBT](http://code.google.com/p/simple-build-tool/) processor built on the [Lifty-engine](http://lifty.github.com/), heavily based on [lifty](http://lifty.github.com) by Mads Hartmann Jensen.

Getting Started
===============

Quick Install
-------------

As this project is in its very early beginnings there is no jar in online repositories yet, so please clone this project in your directory of choice:

`mkdir -p /Development/lift/integration`

`cd /Development/lift/integration`

`git clone git://github.com/ignaciocases/cardano.git`

Type `sbt` and type
`publish-local`

This compiles the project and places the resulting jar in your local ivy repository.

Remove the existent processor if you have installed cardano previously:

`*remove cardano`

Then, install the processor:
`*cardano is org.hnlab cardano 0.2.9`

Using Cardano
-------------
Start a new project using `sbt`. To obtain help type inside `sbt`
`cardano help`

Try to generate a new Cappuccino xib with sample integration doing
`cardano create project-xib`

Type the info, and after that enter

`reload`
`update`

You can start your webapp doing
`jetty-run`

and
`~prepare-webapp`

Templates
---------

To see which templates has Cardano currently available type the following:
`cardano tem`
-`project-blank`
-`project-frothy` - David Pollak's Frothy project.
-`project-xib`

Requirements
------------


Acknowledgements
----------------

Thanks to Jerôme Denanot, Mads Hartmann Jensen, and David Pollak for their nice contributions.