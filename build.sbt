import AssemblyKeys._
import DockerKeys._
import sbtdocker.mutable.Dockerfile

name := "sample_app"

version := "1.0"


Common.settings

assemblySettings

/////// Docker 

dockerSettings

// add your sbt-docker settings here
docker <<= (docker dependsOn assembly)

dockerfile in docker := {
  val artifact = (outputPath in assembly).value
  val artifactTargetPath = s"/app/${artifact.name}"
  new Dockerfile {
    from("java")
    add(artifact, artifactTargetPath)
    entryPoint("java", "-jar", artifactTargetPath)
    expose(8080)
  }
}