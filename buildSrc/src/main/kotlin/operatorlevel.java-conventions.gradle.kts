plugins {
	`java-library`
	id("com.diffplug.spotless")
}

group = rootProject.group
version = rootProject.version
description = rootProject.description

java.toolchain {
	languageVersion.set(JavaLanguageVersion.of(25))
}

tasks {
	compileJava {
		options.encoding = Charsets.UTF_8.name()
	}
	build {
		dependsOn(spotlessApply)
	}
}

spotless {
	java {
		endWithNewline()
		formatAnnotations()
		leadingSpacesToTabs()
		removeUnusedImports()
		trimTrailingWhitespace()
	}
}