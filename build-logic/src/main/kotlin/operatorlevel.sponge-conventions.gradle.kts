plugins {
	id("operatorlevel.platform-conventions")
	id("org.spongepowered.gradle.plugin")
}

tasks {
	shadowJar {
		sequenceOf(
			"dev.faststats",
			"space.arim.dazzleconf",
			"org.slf4j"
		).forEach { pkg ->
			relocate(pkg, "fi.fabianadrian.operatorlevel.dependency.$pkg")
		}
	}
}