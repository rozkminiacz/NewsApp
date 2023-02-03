import java.util.*

fun initBuildProperties() {
    val buildTime by extra {
        Date().toString()
    }
}

initBuildProperties()