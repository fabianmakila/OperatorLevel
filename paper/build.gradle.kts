plugins {
    id("operatorlevel.java-conventions")
    id("net.minecrell.plugin-yml.paper") version "0.6.0"
    id("com.gradleup.shadow") version "8.3.0"
}

dependencies {
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
    main = "fi.fabianadrian.operatorlevel.OperatorLevelPaper"

    foliaSupported = true
    apiVersion = "1.19"
    author = "FabianAdrian"

    permissions {
        register("operatorlevel.1")
        register("operatorlevel.2")
        register("operatorlevel.3")
        register("operatorlevel.4")
    }

    serverDependencies {
        register("LuckPerms") {
            required = false
        }
    }
}