plugins {
    id("operatorlevel.platform-conventions")
    alias(libs.plugins.resourceFactory.paper)
    alias(libs.plugins.run.paper)
}

dependencies {
    compileOnly(libs.platform.paper)

    implementation(libs.bstats.bukkit)
    implementation(libs.dazzleconf) {
        exclude("org.yaml")
    }
    compileOnly(libs.luckperms)
}

tasks {
    runServer {
        // This should be the minimum supported version
        minecraftVersion("1.20.6")
    }
}

paperPluginYaml {
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

    dependencies {
        server {
            register("LuckPerms") {
                required = false
            }
        }
    }
}