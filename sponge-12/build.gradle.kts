import org.spongepowered.gradle.plugin.config.PluginLoaders
import org.spongepowered.plugin.metadata.model.PluginDependency

plugins {
	id("operatorlevel.sponge-conventions")
}

dependencies {
	compileOnly(libs.packetevents.sponge)
	implementation(libs.slf4j)
	implementation(libs.faststats.sponge)
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
		entrypoint("fi.fabianadrian.operatorlevel.sponge12.OperatorLevelSponge")
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