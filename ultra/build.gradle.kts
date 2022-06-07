import com.github.gradle.node.yarn.task.YarnTask

plugins {
    id("com.github.node-gradle.node") version "3.2.1"
}

val svelteCheck by tasks.register("svelteCheck", YarnTask::class) {
    description =
        "Provides CLI diagnostics checks for Unused CSS, Svelte A11y hints, and JavaScript/TypeScript compiler errors"
    dependsOn(tasks.yarn)
    args.set(listOf("run", "check:ultra"))
}

val generate by tasks.register("generate", YarnTask::class) {
    description = "checks written queries and fragments are valid"
    dependsOn(tasks.yarn)
    args.set(listOf("run", "generate:ultra"))
}
