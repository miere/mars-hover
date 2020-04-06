project("Mars Rover") {

    groupId = "snooper.tests"
    artifactId = "mars-rover"
    version = "1.0.0-SNAPSHOT"

    // Versions
    val kotlinVersion = "1.3.61"
    val logbackVersion = "1.2.3"
    val injectorVersion = "1.3.0"
    val junitVersion = "5.6.1"
    val mockitoKotlinVersion = "2.2.0"

    // Configuration
    val launcherClass = "snooper.mars.rover.ApplicationKt"

    dependencies {
        // Kotlin Dependencies
        compile("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")
        compile("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")

        test("org.junit.jupiter:junit-jupiter:${junitVersion}")
        test("com.nhaarman.mockitokotlin2:mockito-kotlin:${mockitoKotlinVersion}")
    }

    build {
        sourceDirectory = "source"
        testSourceDirectory = "tests/source"
        finalName = "application"

        resources {
            resource {
                isFiltering = false
                directory = "resources"
                includes = listOf("**/*")
                excludes = listOf("**/*.kt", "**/*.java")
            }
        }

        testResources {
            testResource {
                isFiltering = false
                directory = "tests/resources"
                includes = listOf("**/*")
                excludes = listOf("**/*.kt", "**/*.java")
            }
        }

        plugins {

            plugin("org.apache.maven.plugins:maven-surefire-plugin:3.0.0-M4") {
            }
            
            /**
             * Configures Kotlin Plugin to perform usual compilation and annotation
             * processing. It will ensure any library that generates classes (like
             * Injector, AutoValue, Dagger) will be properly executed with Kotlin.
             */
            plugin("org.jetbrains.kotlin:kotlin-maven-plugin:${kotlinVersion}") {
                executions {
                    execution(id = "compile", goals = listOf("compile"))
                    execution(id = "test-compile", goals = listOf("test-compile"))
                }
                configuration {
                    "jvmTarget" to "11"
                }
            }

            /**
             * Generates a Uber Jar that will run Kos as main Launcher.
             */
            plugin("org.apache.maven.plugins:maven-shade-plugin:3.2.0") {
                executions {
                    execution(id = "default-package", phase = "package", goals = listOf("shade")) {
                        configuration {
                            "createDependencyReducedPom" to true
                            "dependencyReducedPomLocation" to "\${project.build.directory}/pom-reduced.xml"
                            "transformers" {
                                "org.apache.maven.plugins.shade.resource.ServicesResourceTransformer" {}
                                "org.apache.maven.plugins.shade.resource.ManifestResourceTransformer" {
                                    "manifestEntries" {
                                        "Main-Class" to launcherClass
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
