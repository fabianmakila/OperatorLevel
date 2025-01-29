rootProject.name = "OperatorLevel"

listOf(
	"common",
	"paper",
	"sponge"
).forEach { include(it) }

dependencyResolutionManagement {
	repositories {
		mavenCentral()
		maven("https://repo.papermc.io/repository/maven-public/")
		maven("https://repo.codemc.io/repository/maven-releases/")
	}
}