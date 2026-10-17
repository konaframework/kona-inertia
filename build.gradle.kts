import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.KotlinJvm
import com.vanniktech.maven.publish.MavenPublishBaseExtension

plugins {
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kover) apply false
    alias(libs.plugins.maven.publish) apply false
}

allprojects {
    group = "org.konaframework"
    version = providers.gradleProperty("inertia.version").get()

    repositories {
        mavenLocal {
            content { includeGroup("org.konaframework") }
        }
        mavenCentral()
    }
}

subprojects {
    plugins.withId("org.jetbrains.kotlin.jvm") {
        extensions.configure<JavaPluginExtension> {
            toolchain {
                languageVersion.set(JavaLanguageVersion.of(21))
            }
        }

        tasks.withType<Test> {
            useJUnitPlatform()
            testLogging {
                events("failed")
                exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
                showStackTraces = true
            }
        }
    }

    if (name == "kona-inertia") {
        pluginManager.withPlugin("org.jetbrains.kotlin.jvm") {
            configureKonaInertiaPublishing()
        }
    }
}

fun Project.configureKonaInertiaPublishing() {
    pluginManager.apply("com.vanniktech.maven.publish")
    extensions.configure<MavenPublishBaseExtension> {
        coordinates(group.toString(), "kona-inertia", version.toString())
        configure(
            KotlinJvm(
                javadocJar = JavadocJar.Empty(),
                sourcesJar = true,
            ),
        )
        pom {
            name.set("kona-inertia")
            description.set("Inertia adapter for Kona")
            url.set("https://inertia.konaframework.org")
            inceptionYear.set("2025")
            licenses {
                license {
                    name.set("The Apache License, Version 2.0")
                    url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                    distribution.set("repo")
                }
            }
            developers {
                developer {
                    id.set("konaframework")
                    name.set("Kona Framework contributors")
                    url.set("https://konaframework.org")
                }
            }
            scm {
                url.set("https://github.com/konaframework/kona-inertia")
                connection.set("scm:git:git://github.com/konaframework/kona-inertia.git")
                developerConnection.set("scm:git:ssh://git@github.com/konaframework/kona-inertia.git")
            }
        }
        publishToMavenCentral(automaticRelease = false)
        val hasInMemoryKey =
            findProperty("signingInMemoryKey") != null ||
                System.getenv("ORG_GRADLE_PROJECT_signingInMemoryKey") != null
        val hasKeyId = findProperty("signing.keyId") != null
        if (hasInMemoryKey || hasKeyId) {
            signAllPublications()
        }
    }
}
