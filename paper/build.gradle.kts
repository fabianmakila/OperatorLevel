plugins {
    id("operatorlevel.java-conventions")
    id("net.minecrell.plugin-yml.paper") version "0.6.0"
    id("com.gradleup.shadow") version "8.3.0"
}

dependencies {
    implementation(project(":common"))

    compileOnly(libs.platform.paper)
    compileOnly(libs.luckperms)
    implementation(libs.dazzleconf) {
        exclude("org.yaml")
    }
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

paper {
    main = "fi.fabianadrian.operatorlevel.paper.OperatorLevelPaper"

    name = rootProject.name
    foliaSupported = true
    apiVersion = "1.20.6"
    author = "FabianAdrian"

    permissions {
        register("operatorlevel.level.1")
        register("operatorlevel.level.2")
        register("operatorlevel.level.3")
        register("operatorlevel.level.4")
        register("operatorlevel.command.reload")
    }

    serverDependencies {
        register("LuckPerms") {
            required = false
        }
    }
}