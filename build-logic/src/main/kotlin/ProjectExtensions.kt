import org.gradle.api.Project
import org.gradle.kotlin.dsl.support.uppercaseFirstChar

val Project.prefixedPluginName: String
	get() = name.split("-")
		.joinToString("-") { it.uppercaseFirstChar() }
		.let { "${rootProject.name}-$it" }