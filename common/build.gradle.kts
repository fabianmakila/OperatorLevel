plugins {
    id("operatorlevel.java-conventions")
}

dependencies {
    compileOnly(libs.adventure)
    compileOnly(libs.dazzleconf)
    compileOnly(libs.luckperms)
    compileOnly(libs.slf4j)
    compileOnly(libs.packetevents.api)
}