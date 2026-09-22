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
		archiveBaseName.set(project.prefixedPluginName)
		archiveClassifier.set("")
		destinationDirectory.set(rootProject.layout.buildDirectory.dir("libs"))
	}
}