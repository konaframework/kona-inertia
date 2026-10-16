rootProject.name = "kona-inertia"

include("kona-inertia")
include("inertia-sample-kotlin")
include("inertia-sample-java")

project(":kona-inertia").projectDir = file("inertia")
project(":inertia-sample-kotlin").projectDir = file("samples/inertia-kotlin")
project(":inertia-sample-java").projectDir = file("samples/inertia-java")
