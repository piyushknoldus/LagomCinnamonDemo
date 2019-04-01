// Comment to get more information during initialization
logLevel := Level.Warn

resolvers += Resolver.sonatypeRepo("public")

credentials += Credentials(baseDirectory.value / ".lightbend" / "commercial.credentials")
resolvers += Resolver.url("lightbend-commercial", url("https://repo.lightbend.com/commercial-releases"))(Resolver.ivyStylePatterns)
// IDE integration plugins
addSbtPlugin("com.typesafe.sbteclipse" % "sbteclipse-plugin" % "5.2.4")

addSbtPlugin("com.lightbend.rp" % "sbt-reactive-app" % "1.5.0")

// Use the Play sbt plugin for Play projects
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.6.18")

// The Lagom plugin
addSbtPlugin("com.lightbend.lagom" % "lagom-sbt-plugin" % "1.4.11")

// Lightbend Cinnamon
addSbtPlugin("com.lightbend.cinnamon" % "sbt-cinnamon" % "2.10.17")