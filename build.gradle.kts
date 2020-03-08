import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

plugins {
    application
    id("org.springframework.boot") version "2.2.1.RELEASE"
    id("io.spring.dependency-management") version "1.0.8.RELEASE"
    kotlin("jvm") version "1.3.50"
    kotlin("plugin.spring") version "1.3.50"
    kotlin("plugin.jpa") version "1.3.50"

}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8
var arrow_version = "0.9.0"
repositories {
    mavenCentral()
}
dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-rest")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-tomcat")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.flywaydb:flyway-core")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation ("io.springfox:springfox-swagger2:3.0.0-SNAPSHOT")
    implementation ("io.springfox:springfox-swagger-ui:3.0.0-SNAPSHOT")
    implementation ("io.springfox:springfox-data-rest:3.0.0-SNAPSHOT")
    // Arrow Start
    // compile ("org.springframework.plugin:spring-plugin-core:1.2.0.RELEASE")
    compile ("io.arrow-kt:arrow-core-data:$arrow_version")
    compile ("io.arrow-kt:arrow-core-extensions:$arrow_version")
    compile ("io.arrow-kt:arrow-syntax:$arrow_version")
    compile ("io.arrow-kt:arrow-typeclasses:$arrow_version")
    compile ("io.arrow-kt:arrow-extras-data:$arrow_version")
    compile ("io.arrow-kt:arrow-extras-extensions:$arrow_version")
    compile    ("io.arrow-kt:arrow-meta:$arrow_version")
    compile ("joda-time:joda-time:2.10.5")
    implementation("org.apache.commons:commons-lang3:3.9")


    // Firebase Admin
    implementation ("com.google.firebase:firebase-admin:6.8.1")
    // JWT
    implementation ("io.jsonwebtoken:jjwt-api:0.10.7")
    runtime ("io.jsonwebtoken:jjwt-impl:0.10.7")
    runtime ("io.jsonwebtoken:jjwt-jackson:0.10.7")
    // Arrow End
    runtimeOnly("org.postgresql:postgresql")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}
repositories {
    mavenCentral()
    jcenter()
    jcenter {
        setUrl("http://oss.jfrog.org/artifactory/oss-snapshot-local/")
    }
}