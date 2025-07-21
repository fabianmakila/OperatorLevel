import org.spongepowered.gradle.plugin.config.PluginLoaders
import org.spongepowered.plugin.metadata.model.PluginDependency

plugins {
	id("operatorlevel.platform-conventions")
	alias(libs.plugins.sponge)
}

dependencies {
	compileOnly(libs.packetevents.sponge)
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
	injectRepositories(false)
	apiVersion("12.0.0")
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
			version(libs.packetevents.api.get().version)
		}
		dependency("luckperms") {
			loadOrder(PluginDependency.LoadOrder.AFTER)
			optional(true)
			version(libs.luckperms.get().version)
		}
	}
}