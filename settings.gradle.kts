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
		maven("https://repo.papermc.io/repository/maven-public/")
		maven("https://repo.codemc.io/repository/maven-releases/")
		maven("https://repo.spongepowered.org/repository/")
	}
}