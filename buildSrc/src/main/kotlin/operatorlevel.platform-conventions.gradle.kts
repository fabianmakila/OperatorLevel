plugins {
	id("operatorlevel.java-conventions")
	id("com.gradleup.shadow")
}

dependencies {
	implementation(project(":common"))
}

tasks {
	build {
		dependsOn(shadowJar)
	}
	shadowJar {
		archiveBaseName.set("${rootProject.name}-${project.name.replaceFirstChar(Char::titlecase)}")
		archiveClassifier.set("")
		destinationDirectory.set(file("${rootProject.rootDir}/dist"))

		minimize()

		sequenceOf(
			"space.arim.dazzleconf"
		).forEach { pkg ->
			relocate(pkg, "fi.fabianadrian.operatorlevel.dependency.$pkg")
		}
	}
}