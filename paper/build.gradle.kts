import xyz.jpenilla.resourcefactory.paper.PaperPluginYaml

plugins {
    id("operatorlevel.platform-conventions")
    alias(libs.plugins.resourceFactory.paper)
    alias(libs.plugins.run.paper)
}

dependencies {
    compileOnly(libs.platform.paper)
    compileOnly(libs.packetevents.spigot)

    implementation(libs.bstats.bukkit)
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
        register("operatorlevel.gamemode.adventure")
        register("operatorlevel.gamemode.survival")
        register("operatorlevel.gamemode.creative")
        register("operatorlevel.gamemode.spectator")
        register("operatorlevel.command.reload")
    }

    dependencies {
        server {
            register("LuckPerms") {
                load = PaperPluginYaml.Load.BEFORE
                required = false
            }
            register("packetevents") {
                load = PaperPluginYaml.Load.BEFORE
                required = true
            }
        }
    }
}