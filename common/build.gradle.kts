plugins {
    id("operatorlevel.java-conventions")
}

dependencies {
    compileOnly(libs.slf4j)
    compileOnly(libs.luckperms)
    compileOnly(libs.dazzleconf)
}