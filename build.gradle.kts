plugins {
    java
    id("net.minecrell.plugin-yml.paper") version "0.6.0"
}

group = "fi.fabianadrian"
version = "2.0.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.1-R0.1-SNAPSHOT")
    compileOnly("net.luckperms:api:5.4")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
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