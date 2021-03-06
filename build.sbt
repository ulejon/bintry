organization := "me.lessis"

name := "bintry"

version := "0.3.0"

description := "your packages, delivered fresh"

crossScalaVersions := Seq("2.10.4", "2.11.1")

scalaVersion := crossScalaVersions.value.last

//scalacOptions in ThisBuild ++= Seq(Opts.compile.deprecation) ++
//  Seq("-Ywarn-unused-import", "-Ywarn-unused", "-Xlint", "-feature").filter(
//    Function.const(scalaVersion.value.startsWith("2.11")))

libraryDependencies ++= Seq("net.databinder.dispatch" %% "dispatch-json4s-native" % "0.11.2")

initialCommands := "import scala.concurrent.ExecutionContext.Implicits.global;"

seq(bintraySettings:_*)

bintray.Keys.packageLabels in bintray.Keys.bintray := Seq("bintray", "dispatch", "http")

publishArtifact in Test := false

licenses := Seq("MIT" ->
                url(s"https://github.com/softprops/${name.value}/blob/${version.value}/LICENSE"))

homepage := some(url(s"https://github.com/softprops/${name.value}/#readme"))

pomExtra := (
  <scm>
    <url>git@github.com:softprops/bintry.git</url>
    <connection>scm:git:git@github.com:softprops/bintry.git</connection>
  </scm>
  <developers>
    <developer>
      <id>softprops</id>
      <name>Doug Tangren</name>
      <url>https://github.com/softprops</url>
    </developer>
  </developers>)

lsSettings

externalResolvers in LsKeys.lsync := (resolvers in bintray.Keys.bintray).value

(LsKeys.tags in LsKeys.lsync) := (bintray.Keys.packageLabels in bintray.Keys.bintray).value
