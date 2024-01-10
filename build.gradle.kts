plugins {
    java
    id("net.minecrell.plugin-yml.paper") version "0.6.0"
}

group = "fi.fabianadrian"
version = "1.0.0"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.20.4-R0.1-SNAPSHOT")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

paper {
    main = "fi.fabianadrian.operatorlevel.OperatorLevel"

    foliaSupported = true
    apiVersion = "1.19"
    author = "FabianAdrian"

    permissions {
        register("operatorlevel.1")
        register("operatorlevel.2")
        register("operatorlevel.3")
        register("operatorlevel.4")
    }
}