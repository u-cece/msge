plugins {
    id("java")
}

group = "pet.cece"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://repo.opencollab.dev/maven-releases/") {
        name = "opencollab"
    }
    maven("https://repo.opencollab.dev/maven-snapshots/") {
        name = "opencollab"
    }
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("org.geysermc.mcprotocollib:protocol:1.21.4-SNAPSHOT")
    implementation("org.slf4j:slf4j-simple:2.0.17")
}

tasks.test {
    useJUnitPlatform()
}