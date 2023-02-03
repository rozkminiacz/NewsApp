import java.io.ByteArrayOutputStream

fun initGitVariables() {
    fun getTag(): String {
        return ByteArrayOutputStream().use {
            exec {
                commandLine("git", "describe", "--tags", "--match", "v[0-9]*", "--dirty", "--always")
                standardOutput = it
            }
            it.toString(Charsets.UTF_8.name()).trim()
        }
    }

    fun getVersionBuild(): String {
        val tag = getTag()

        val secondTagSegment = tag.split("-").getOrElse(1) { "0" }

        return if (secondTagSegment == "dirty") "0" else secondTagSegment
    }

    fun getBranchName(): String {
        return ByteArrayOutputStream().use {
            exec {
                commandLine("git", "rev-parse", "--abbrev-ref", "HEAD")
                standardOutput = it
            }
            it.toString(Charsets.UTF_8.name()).trim()
        }
    }

    fun getGitSha(): String {
        return ByteArrayOutputStream().use {
            exec {
                commandLine("git", "rev-parse", "--short", "HEAD")
                standardOutput = it
            }
            it.toString(Charsets.UTF_8.name()).trim()
        }
    }

    fun getLastCommit(): String {
        return ByteArrayOutputStream().use {
            exec {
                commandLine("git", "log", "-1", "--pretty=%B")
                standardOutput = it
            }
            it.toString().trimEnd()
        }
    }

    val branchName by extra { getBranchName() }
    val gitSha by extra { getGitSha() }
    val versionBuild by extra { getVersionBuild() }
    val lastCommit by extra { getLastCommit() }
}

initGitVariables()