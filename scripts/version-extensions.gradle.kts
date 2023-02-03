import java.util.Properties

fun initVersions() {
  val props = Properties()
  val propsFile = project.file("${project.rootDir}/version.properties")

  props.load(propsFile.inputStream())

  val versionBuild: String by project

  val versionMajor by extra { props.getProperty("versionMajor") }
  val versionMinor by extra { props.getProperty("versionMinor") }
  val versionPatch by extra { props.getProperty("versionPatch") }

  val versionCode = resolveVersionInt(versionMajor, versionMinor, versionPatch, versionBuild)

  val applicationVersionCode by extra { versionCode }

  val applicationVersionNameFull by extra {
    "$versionMajor.$versionMinor.$versionPatch.$versionBuild"
  }

  val applicationVersionNameShort by extra {
    "$versionMajor.$versionMinor.$versionPatch"
  }
}

initVersions()

fun resolveVersionInt(versionMajor: String, versionMinor: String, versionPatch: String, versionBuild: String): Int {
  val majorInt = Integer.parseInt(versionMajor) * 100_000_000
  val minorInt = Integer.parseInt(versionMinor) * 100_000
  val patchInt = Integer.parseInt(versionPatch) * 1_000
  println("Version build: $versionBuild")
  val versionBuildInt = Integer.parseInt(versionBuild)

  println("Resolving versions: majorInt = $majorInt, minorInt = $minorInt, patchInt = $patchInt, versionBuildInt = $versionBuildInt")

  return majorInt + minorInt + patchInt + versionBuildInt
}

//10150021
//10100024