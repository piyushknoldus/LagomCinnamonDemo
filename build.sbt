
organization in ThisBuild := "com.example"
version in ThisBuild := "1.0-SNAPSHOT"

// the Scala version that will be used for cross-compiled libraries
scalaVersion in ThisBuild := "2.11.12"

val macwire = "com.softwaremill.macwire" %% "macros" % "2.2.5" % "provided"
val scalaTest = "org.scalatest" %% "scalatest" % "3.0.1" % Test
val logbackClassic = "ch.qos.logback" % "logback-classic" % "1.2.3"

credentials in ThisBuild += Credentials(baseDirectory.value / "project" / ".lightbend" / "commercial.credentials")

val commonAkkaSettings = Seq(
  libraryDependencies ++= Seq(
    logbackClassic,
    Cinnamon.library.cinnamonCHMetrics3,
   // Cinnamon.library.cinnamonJvmMetricsProducer,
    Cinnamon.library.cinnamonAkka),
    //   Cinnamon.library.cinnamonCHMetrics3ElasticsearchReporter),
  // Cinnamon options
  cinnamon in run := true,
  cinnamon in test := true,
  cinnamonLogLevel := "INFO"
)

val commonLagomImplSettings = commonAkkaSettings ++ Seq(
  libraryDependencies ++= Seq(
    lagomScaladslPersistenceCassandra,
    lagomScaladslTestKit,
    lagomScaladslKafkaBroker,
    macwire,
    Cinnamon.library.cinnamonLagom
  ),
  // Cinnamon options
  cinnamon in run := true,
  cinnamon in test := true,
  cinnamonLogLevel := "INFO"
)

lazy val `hello-lagom` = (project in file("."))
  .aggregate(`hello-lagom-api`, `hello-lagom-impl`)

lazy val `hello-lagom-api` = (project in file("hello-lagom-api"))
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslApi
    )
  )

val normal_lagom_plugins = Seq(Cinnamon, LagomScala)

lazy val `hello-lagom-impl` = (project in file("hello-lagom-impl"))
  .enablePlugins(normal_lagom_plugins: _*)
  .settings(
    commonLagomImplSettings,
    // Add a play secret to javaOptions in run in Test, so we can run Lagom forked
    javaOptions in (Test, run) += "-Dplay.http.secret.key=x",

    libraryDependencies ++= Seq(
      lagomScaladslPersistenceCassandra,
      lagomScaladslKafkaBroker,
      lagomScaladslTestKit,
      macwire,
      scalaTest
    )
  )
  .settings(lagomForkedTestSettings: _*)
  .dependsOn(`hello-lagom-api`)

/*
lagomCassandraEnabled in ThisBuild := false
lagomUnmanagedServices in ThisBuild := Map("cas_native" -> "http://localhost:9042")
lagomKafkaEnabled in ThisBuild := false
lagomKafkaAddress in ThisBuild := "localhost:9092"
*/