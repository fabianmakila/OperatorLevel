plugins {
    id("operatorlevel.java-conventions")
}

dependencies {
    compileOnly(libs.adventure)
    compileOnly(libs.adventure.text.minimessage)

    implementation(libs.dazzleconf) {
        exclude("org.apiguardian")
        exclude("org.checkerframework")
    }

    compileOnly(libs.luckperms)
    compileOnly(libs.slf4j)
    compileOnly(libs.packetevents.api)
}