rootProject.name = "OperatorLevel"

listOf(
	"common",
	"paper",
	"sponge-12",
	"sponge-17"
).forEach { include(it) }

dependencyResolutionManagement {
	repositories {
		mavenCentral()
		maven("https://repo.papermc.io/repository/maven-public/") // Paper
		maven("https://repo.codemc.io/repository/maven-releases/") // PacketEvents
		maven("https://repo.spongepowered.org/repository/") // Sponge
		maven("https://repo.faststats.dev/releases") // Faststats
		maven("https://eldonexus.de/repository/maven-public/") // StrokkCommands
	}
}