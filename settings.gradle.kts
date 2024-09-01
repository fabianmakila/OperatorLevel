rootProject.name = "OperatorLevel"

include("paper")

dependencyResolutionManagement {
	repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
	repositories {
		mavenCentral()
		maven("https://papermc.io/repo/repository/maven-public/")
	}
}
include("common")
