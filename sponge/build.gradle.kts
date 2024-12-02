import org.spongepowered.gradle.plugin.config.PluginLoaders
import org.spongepowered.plugin.metadata.model.PluginDependency

plugins {
	id("operatorlevel.platform-conventions")
	alias(libs.plugins.sponge)
}

dependencies {
	implementation(libs.slf4j)
	implementation(libs.bstats.sponge)
	implementation(libs.snakeyaml)
}

tasks {
	shadowJar {
		sequenceOf(
			"org.slf4j",
			"org.yaml"
		).forEach { pkg ->
			relocate(pkg, "fi.fabianadrian.operatorlevel.dependency.$pkg")
		}
	}
}

sponge {
	apiVersion("10.0.0")
	license("GPLv3")
	loader {
		name(PluginLoaders.JAVA_PLAIN)
		version("1.0")
	}
	plugin(rootProject.name.lowercase()) {
		displayName(rootProject.name)
		entrypoint("fi.fabianadrian.operatorlevel.sponge.OperatorLevelSponge")
		description(rootProject.description)
		dependency("spongeapi") {
			loadOrder(PluginDependency.LoadOrder.AFTER)
			optional(false)
		}
		dependency("packetevents") {
			loadOrder(PluginDependency.LoadOrder.AFTER)
			optional(false)
			version("2.6.0")
		}
		dependency("luckperms") {
			loadOrder(PluginDependency.LoadOrder.AFTER)
			optional(true)
			version("5.4")
		}
	}
}